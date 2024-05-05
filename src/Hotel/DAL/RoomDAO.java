/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hotel.DAL;

import Hotel.DTO.rooms.Room;
import Hotel.DTO.rooms.RoomForManaging;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Yue
 */
public class RoomDAO {

    private final Connection conn = DbConn.getConnection();
    private static RoomDAO instance = null;

    private RoomDAO() {

    }

    public static RoomDAO getInstance() {
        if (instance == null) {
            instance = new RoomDAO();
        }
        return instance;
    }

    public ResultSet getAllRoom() throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery("SELECT * FROM room WHERE deleted = 0 ORDER BY rid");
    }

    public boolean checkRoomName(String roomName, int roomId) throws SQLException {
        boolean check = false;
        PreparedStatement ps = conn.prepareStatement("SELECT rname "
                + "FROM room WHERE rname = ? AND rid != ? WHERE deleted = 0");
        ps.setString(1, roomName);
        ps.setInt(2, roomId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            check = true;
        }
        rs.close();
        ps.close();

        return check;
    }

    public void addNewRoom(Room room) throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO room (rname, tid) VALUES (?, ?)");
            ps.setString(1, room.getName());
            ps.setInt(2, room.getTypeId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateRoomInfo(Room room) throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE room SET rname = ?, tid = ? WHERE rid = ?");
            ps.setString(1, room.getName());
            ps.setInt(2, room.getTypeId());
            ps.setInt(3, room.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRoom(int roomId) throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE room SET deleted = 1 WHERE rid = ?");
            ps.setInt(1, roomId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkRoomByType(int typeId) throws SQLException {
        boolean check = false;
        PreparedStatement ps = conn.prepareStatement("SELECT tid "
                + "FROM room WHERE rid = ? AND deleted = 0");
        ps.setInt(1, typeId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            check = true;
        }
        ps.close();

        return check;
    }

    public void changeStateRoom(int roomId, boolean state) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("UPDATE room "
                + "SET rstatus = ? WHERE rid = ?");
        ps.setBoolean(1, state);
        ps.setInt(2, roomId);
        ps.executeUpdate();
    }

    public ResultSet getOrdersByRoomId(int roomId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT booking.bid, check_in, check_out "
                + "FROM booking INNER JOIN booking_room ON booking.bid = booking_room.brid "
                + "WHERE rid = ? AND bstatus < 2");
        ps.setInt(1, roomId);
        ResultSet rs = ps.executeQuery();

        return rs;
    }

    public ArrayList<Room> getAvailableRoom(Timestamp from, Timestamp to) throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM room WHERE rid NOT IN (SELECT DISTINCT rid FROM booking_room WHERE (? BETWEEN real_checkin AND real_checkout) OR (? BETWEEN real_checkin AND real_checkout)) AND deleted = 0");
            ps.setTimestamp(1, from);
            ps.setTimestamp(2, to);
            ResultSet rs = ps.executeQuery();
            ArrayList<Room> roomList = new ArrayList<>();
            while (rs.next()) {
                roomList.add(new RoomForManaging(
                        rs.getInt("rid"),
                        rs.getString("rname"),
                        rs.getInt("tid"),
                        0, true
                ));
            }
            return roomList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
