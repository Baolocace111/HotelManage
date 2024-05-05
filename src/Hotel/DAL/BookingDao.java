/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hotel.DAL;

import Hotel.DAL.pool.ConnectionPool;
import Hotel.DTO.Booking;
import Hotel.DTO.rooms.BookingRoom;
import Hotel.DTO.rooms.Room;
import Hotel.DTO.services.RoomService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yue
 */
public class BookingDao {

    public Booking getBookingInfo(int bookingId, int roomId) throws Exception {
        Connection conn = ConnectionPool.getconnection();
        String sql = "SELECT * "
                + "FROM booking INNER JOIN booking_room ON booking.bid = booking_room.brid "
                + "WHERE booking.bid = ? AND rid = ? AND deleted = 0";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, bookingId);
        ps.setInt(2, roomId);
        ResultSet rs = ps.executeQuery();
        Booking booking = new Booking();
        if (rs.next()) {
            booking = new Booking(rs.getInt("booking.bid"),
                    rs.getInt("cid"),
                    rs.getTimestamp("btime"),
                    rs.getTimestamp("check_in"),
                    rs.getTimestamp("check_out"),
                    null, 0, 0);
            booking.getBookingRooms().add(new BookingRoom(
                    rs.getInt("brid"),
                    rs.getInt("rid"),
                    rs.getInt("rprice"),
                    rs.getTimestamp("real_checkin"),
                    rs.getTimestamp("real_checkout"),
                    rs.getInt("surcharge")
            ));
        }
        ConnectionPool.releaseConnection(conn);
        return booking;
    }

    public ArrayList<Booking> getPagingBooking(int page) throws SQLException {
        Connection conn = ConnectionPool.getconnection();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM booking WHERE deleted = 0 ORDER BY bid DESC LIMIT 50 OFFSET ?");
        return getBookings(page, conn, ps);
    }

    public ArrayList<Booking> getCheckedInBookingByPage(int page) throws SQLException {
        Connection conn = ConnectionPool.getconnection();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM booking WHERE bstatus = 2 AND deleted = 0"
                + " ORDER BY bid DESC LIMIT 50 OFFSET ?");
        return getBookings(page, conn, ps);
    }

    private ArrayList<Booking> getBookings(int page, Connection conn, PreparedStatement ps) throws SQLException {
        ps.setInt(1, page * 50);
        ResultSet rs = ps.executeQuery();
        ArrayList<Booking> bookings = new ArrayList<>();
        while (rs.next()) {
            Booking booking = new Booking();
            booking.setId(rs.getInt("bid"));
            booking.setCustomerId(rs.getInt("cid"));
            booking.setBookingTime(rs.getTimestamp("btime"));
            booking.setCheckInTime(rs.getTimestamp("check_in"));
            booking.setCheckOutTime(rs.getTimestamp("check_out"));
            booking.setDeposit(rs.getInt("deposit"));
            booking.setStatus(rs.getInt("bstatus"));
            booking.setPaytime(rs.getTimestamp("paytime"));
            booking.setTotal(rs.getLong("total"));
            bookings.add(booking);
        }
        if (bookings.isEmpty()) {
            return bookings;
        }
        getBookingRooms(bookings);
        getBookingService(bookings);
        ConnectionPool.releaseConnection(conn);

        return bookings;
    }

    public void getBookingRooms(ArrayList<Booking> bookings) throws SQLException {
        Connection conn = ConnectionPool.getconnection();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM booking_room WHERE brid BETWEEN ? AND ? ORDER BY brid DESC");
        ps.setInt(1, bookings.get(bookings.size() - 1).getId());
        ps.setInt(2, bookings.get(0).getId());
        ResultSet rs = ps.executeQuery();
        ArrayList<BookingRoom> bookingRooms = new ArrayList<>();
        int count = 0;
        while (rs.next()) {
            if (rs.getInt("brid") != bookings.get(count).getId()) {
                count++;
            }
            BookingRoom room = new BookingRoom(
                    rs.getInt("brid"),
                    rs.getInt("rid"),
                    rs.getInt("rprice"),
                    rs.getTimestamp("real_checkin"),
                    rs.getTimestamp("real_checkout"),
                    rs.getInt("surcharge")
            );
            bookings.get(count).getBookingRooms().add(room);
        }
        ConnectionPool.releaseConnection(conn);
    }

    public void getBookingService(ArrayList<Booking> bookings) throws SQLException {
        Connection conn = ConnectionPool.getconnection();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM booking_service WHERE bid BETWEEN ? AND ? ORDER BY bid DESC, bstime");
        ps.setInt(1, bookings.get(bookings.size() - 1).getId());
        ps.setInt(2, bookings.get(0).getId());
        ResultSet rs = ps.executeQuery();
        ArrayList<RoomService> roomServices = new ArrayList<>();
        int count = 0;
        while (rs.next()) {
            if (rs.getInt("bid") != bookings.get(count).getId()) {
                count++;
                rs.previous();
            } else {
                RoomService roomService = new RoomService(
                        rs.getInt("bsid"),
                        rs.getInt("sid"),
                        rs.getInt("bid"),
                        rs.getInt("rid"),
                        rs.getTimestamp("bstime"),
                        rs.getInt("bsprice"),
                        rs.getInt("quantity")
                );
                bookings.get(count).getBookingServices().add(roomService);
            }
        }
        ConnectionPool.releaseConnection(conn);
    }

    public ArrayList<Booking> getIncommingBooking(Room room) throws Exception {
        Connection conn = ConnectionPool.getconnection();
        String sql = "SELECT * "
                + "FROM booking INNER JOIN booking_room ON booking.bid = booking_room.brid "
                + "WHERE rid = ? AND bstatus < 2 AND booking.deleted = 0 "
                + "ORDER BY check_in, check_out "
                + "LIMIT 10";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, room.getId());
        ResultSet rs = ps.executeQuery();
        ArrayList<Booking> bookings = new ArrayList<>();
        while (rs.next()) {
            Booking booking = new Booking();
            booking.setId(rs.getInt("bid"));
            booking.setCheckInTime(rs.getTimestamp("check_in"));
            booking.setCheckOutTime(rs.getTimestamp("check_out"));

            bookings.add(booking);
        }
        ConnectionPool.releaseConnection(conn);

        return bookings;
    }

    public void changeRoom(int bookingRoomId, int newRoom, boolean pay) throws SQLException {
        Connection conn = ConnectionPool.getconnection();
        CallableStatement cstmt = conn.prepareCall("{call spChangeRoom(?, ?, ?)}");
        cstmt.setInt(1, bookingRoomId);
        cstmt.setInt(2, newRoom);
        cstmt.setBoolean(3, pay);
        cstmt.execute();
        ConnectionPool.releaseConnection(conn);
    }

    public int addBooking(Booking booking) throws SQLException {
        int id = 0;
        Connection conn = ConnectionPool.getconnection();
        try {
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement("INSERT INTO booking"
                    + "(cid, check_in, check_out, deposit, bstatus) VALUES (?, ?, ?, ? ,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, booking.getCustomerId());
            ps.setTimestamp(2, booking.getCheckInTime());
            ps.setTimestamp(3, booking.getCheckOutTime());
            ps.setInt(4, booking.getDeposit());
            ps.setInt(5, booking.getStatus());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            booking.setId(rs.getInt(1));
            rs.close();
            ps.close();

            ps = conn.prepareStatement("INSERT INTO booking_room"
                    + "(brid, rid, rprice) VALUES (?, ?, ?)");
            for (BookingRoom room : booking.getBookingRooms()) {
                ps.setInt(1, booking.getId());
                ps.setInt(2, room.getRoomId());
                ps.setInt(3, room.getRoomPrice());
                ps.addBatch();
            }
            ps.executeBatch();

            conn.commit();
            id = booking.getId();
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            conn.setAutoCommit(true);
            ConnectionPool.releaseConnection(conn);
            return id;
        }
    }

    public void depositBooking(int bookingId) throws SQLException {
        try (Connection conn = ConnectionPool.getconnection()) {
            int totalAmount = getTotalAmountForBooking(conn, bookingId); // Lấy tổng tiền của booking
            int depositAmount = totalAmount / 2; // Tính toán số tiền cần đặt cọc (50% của tổng tiền)
            try (PreparedStatement ps = conn.prepareStatement("UPDATE booking SET deposit = ? WHERE bid = ?")) {
                ps.setInt(1, depositAmount);
                ps.setInt(2, bookingId);
                ps.executeUpdate();
            }
        }
    }

    public void checkInBooking(int bookingId, int surchargePercent) throws SQLException {
        Connection conn = null;
        try {
            conn = ConnectionPool.getconnection();
            conn.setAutoCommit(false);

            // Cập nhật trạng thái check_in trong bảng booking
            try (PreparedStatement psUpdateBooking = conn.prepareStatement("UPDATE booking SET bstatus = ? WHERE bid = ?")) {
                psUpdateBooking.setInt(1, 1); // Giả sử 1 là mã trạng thái cho CHECKED_IN
                psUpdateBooking.setInt(2, bookingId);
                psUpdateBooking.executeUpdate();
            }

            // Cập nhật thông tin trong bảng booking_room
            try (PreparedStatement psUpdateBookingRoom = conn.prepareStatement("UPDATE booking_room SET real_checkin = ?, surcharge = ? WHERE brid = ?")) {
                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                psUpdateBookingRoom.setTimestamp(1, currentTimestamp);

                // Tính phí thêm (surcharge) dựa trên tỷ lệ phần trăm và tổng tiền của booking
                int totalAmount = getTotalAmountForBooking(conn, bookingId); // Hàm này cần được triển khai để tính tổng tiền của booking
                int surchargeAmount = totalAmount * surchargePercent / 100;
                psUpdateBookingRoom.setInt(2, surchargeAmount);

                psUpdateBookingRoom.setInt(3, bookingId);
                psUpdateBookingRoom.executeUpdate();
            }

            conn.commit();
        } catch (SQLException ex) {
            if (conn != null) {
                conn.rollback();
            }
            throw ex;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                ConnectionPool.releaseConnection(conn);
            }
        }
    }

    private int getTotalAmountForBooking(Connection conn, int bookingId) throws SQLException {
        int totalAmount = 0;
        try (PreparedStatement ps = conn.prepareStatement("SELECT total FROM booking WHERE bid = ?")) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalAmount = rs.getInt("total");
                }
            }
        }
        return totalAmount;
    }

    public void checkOutRooms(List<Integer> bookingRoomIds, int surchargePercent, boolean pay) throws SQLException {
        Connection conn = ConnectionPool.getconnection();
        PreparedStatement ps;
        if (pay) {
            ps = conn.prepareStatement("UPDATE booking_room"
                    + " SET real_checkout = current_timestamp, surcharge = surcharge + rprice * ? / 100 WHERE brid = ?");
            for (int id : bookingRoomIds) {
                ps.setInt(1, surchargePercent);
                ps.setInt(2, id);
                ps.addBatch();
            }
        } else {
            ps = conn.prepareStatement("UPDATE booking_room"
                    + " SET real_checkout = current_timestamp, rprice = 0 WHERE brid = ?");
            for (int id : bookingRoomIds) {
                ps.setInt(1, id);
                ps.addBatch();
            }
        }
        ps.executeBatch();
        ConnectionPool.releaseConnection(conn);
    }

    public void payBooking(int bookingId, int totalAmount) throws SQLException {
        Connection conn = null;
        try {
            conn = ConnectionPool.getconnection();
            conn.setAutoCommit(false);

            // Cập nhật bảng booking với thời gian thanh toán và tổng số tiền
            try (PreparedStatement psBooking = conn.prepareStatement(
                    "UPDATE booking SET paytime = CURRENT_TIMESTAMP, total = ? WHERE bid = ?")) {
                psBooking.setLong(1, totalAmount);
                psBooking.setInt(2, bookingId);
                psBooking.executeUpdate();
            }

            // Cập nhật thời gian checkout hoàn tất (thuctra) và tổng thanh toán (tongthanhtoan) cho hosothuephong
            try (PreparedStatement psHosothuephong = conn.prepareStatement(
                    "UPDATE hosothuephong SET thuctra = CURRENT_TIMESTAMP, tongthanhtoan = ? WHERE madatphong = ?")) {
                psHosothuephong.setLong(1, totalAmount);
                psHosothuephong.setInt(2, bookingId);
                psHosothuephong.executeUpdate();
            }

            // Cập nhật trạng thái phòng và giải phóng booking_room
            try (PreparedStatement psUpdateRoom = conn.prepareStatement(
                    "UPDATE room SET current_booking = 0, rstatus = 1 WHERE rid IN (SELECT rid FROM booking_room WHERE brid = ?)")) {
                psUpdateRoom.setInt(1, bookingId);
                psUpdateRoom.executeUpdate();
            }

            try (PreparedStatement psReleaseBookingRoom = conn.prepareStatement(
                    "DELETE FROM booking_room WHERE brid = ?")) {
                psReleaseBookingRoom.setInt(1, bookingId);
                psReleaseBookingRoom.executeUpdate();
            }

            conn.commit();
        } catch (SQLException ex) {
            if (conn != null) {
                conn.rollback();
            }
            throw ex;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                ConnectionPool.releaseConnection(conn);
            }
        }
    }
}
