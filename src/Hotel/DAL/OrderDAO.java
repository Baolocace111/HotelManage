package Hotel.DAL;

import Hotel.DTO.Booking;
import Hotel.DTO.rooms.RoomForOrdering;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Yue
 */
public class OrderDAO {

    private final Connection conn = DbConn.getConnection();
    private static OrderDAO instance = null;

    private OrderDAO() {

    }

    public static OrderDAO getInstance() {
        if (instance == null) {
            instance = new OrderDAO();
        }
        return instance;
    }

    public int addOrder(Booking booking) throws SQLException {
        conn.setAutoCommit(false);
        int orderId = 0;
        PreparedStatement ps = conn.prepareStatement("INSERT INTO booking "
                + "(bid, btime, check_in, check_out, deposit, bstatus) "
                + "VALUES (?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, booking.getCustomerId());
        ps.setTimestamp(2, booking.getBookingTime());
        ps.setTimestamp(3, booking.getCheckInTime());
        ps.setTimestamp(4, booking.getCheckOutTime());
        ps.setInt(5, booking.getDeposit());
        ps.setInt(6, booking.getStatus());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        orderId = rs.getInt(1);
        rs.close();
        ps.close();

        return orderId;
    }

    public void addRoomToOrder(int orderId, ArrayList<RoomForOrdering> rooms) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO booking_room "
                + "(brid, rid, rprice) "
                + "VALUES (?, ?, ?)");
        for (RoomForOrdering room : rooms) {
            ps.setInt(1, orderId);
            ps.setInt(2, room.getId());
            ps.setInt(3, room.getPrice());
            ps.addBatch();
        }
        ps.executeBatch();
        ps.close();
        conn.commit();
        conn.setAutoCommit(true);
    }

    public ResultSet getOrderedRoomsByTime(Timestamp checkIn, Timestamp checkOut) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT DISTINCT booking_room.rid "
                + "FROM booking INNER JOIN booking_room "
                + "ON booking.brid = booking_room.brid "
                + "WHERE  check_out >= ? AND check_in <= ?  AND booking.bstatus != 3");
        ps.setTimestamp(1, checkIn);
        ps.setTimestamp(2, checkOut);
        ResultSet rs = ps.executeQuery();

        return rs;
    }

    public ResultSet getOrder() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT  * FROM booking "
                + "ORDER BY bstatus ASC, bid DESC");
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public void changeStateOrder(int orderId, int state) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("UPDATE booking "
                + "SET bstatus = ? WHERE bid = ?");
        ps.setInt(1, state);
        ps.setInt(2, orderId);
        ps.executeUpdate();
        ps.close();
    }

    public Timestamp getCheckInTimestampById(int orderId) throws SQLException {
        Timestamp timestamp = null;
        PreparedStatement ps = conn.prepareStatement("SELECT check_in "
                + "FROM booking WHERE brid = ?");
        ps.setInt(1, orderId);
        ResultSet rs = ps.executeQuery();
        rs.next();
        timestamp = rs.getTimestamp("check_in");
        ps.close();

        return timestamp;
    }

    public ResultSet getRoomInOrder(int orderId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT rname, tname, rprice "
                + "FROM booking_room INNER JOIN room on booking_room.rid = room.rid "
                + "inner join room_type on room.tid = room_type.tid "
                + "WHERE brid = ?");
        ps.setInt(1, orderId);
        ResultSet rs = ps.executeQuery();

        return rs;
    }

    public ResultSet getOrderById(int orderId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM booking "
                + "WHERE brid = ?");
        ps.setInt(1, orderId);
        ResultSet rs = ps.executeQuery();

        return rs;
    }

    public ResultSet checkEmptyRoomInOrder(int orderId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT booking_room.rid "
                + "FROM booking_room INNER JOIN room ON booking_room.rid = room.rid "
                + "WHERE brid = ? AND mahientai != 0");
        ps.setInt(1, orderId);
        ResultSet rs = ps.executeQuery();

        return rs;
    }

    public ResultSet getOrderTop(int topCount) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM booking "
                + "ORDER BY bstatus ASC, bid DESC "
                + "LIMIT 50 OFFSET ? ");
        ps.setInt(1, topCount);
        ResultSet rs = ps.executeQuery();

        return rs;
    }

    public ResultSet getCountOrder() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT COUNT(bid) AS soluong FROM booking");
        ResultSet rs = ps.executeQuery();

        return rs;
    }

}
