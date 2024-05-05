package Hotel.DAL;

import Hotel.DTO.RoomType;

import java.sql.*;

/**
 *
 * @author Yue
 */
public class RoomTypeDAO {

    private static RoomTypeDAO instance = null;
    private final Connection conn = DbConn.getConnection();

    private RoomTypeDAO() {

    }

    public static RoomTypeDAO getInstance() {
        if (instance == null) {
            instance = new RoomTypeDAO();
        }
        return instance;
    }

    public ResultSet getRoomTypeList() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM room_type WHERE deleted = 0 ORDER BY tprice");

        return rs;
    }

    public boolean checkRoomTypeName(RoomType rt) throws SQLException {
        boolean check = false;
        PreparedStatement ps = conn.prepareStatement("SELECT tname "
                + "FROM room_type WHERE tname = ? AND tid != ? AND deleted = 0");
        ps.setString(1, rt.getName());
        ps.setInt(2, rt.getId());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            check = true;
        }
        rs.close();

        return check;
    }

    public void addRoomType(RoomType roomType) throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO room_type (tname, tprice) VALUES (?, ?)");
            ps.setString(1, roomType.getName());
            ps.setInt(2, roomType.getPrice());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateRoomType(RoomType roomType) throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE room_type SET tname = ?, tprice = ? WHERE tid = ?");
            ps.setString(1, roomType.getName());
            ps.setInt(2, roomType.getPrice());
            ps.setInt(3, roomType.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRoomType(int typeId) throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE room_type SET deleted = 1 WHERE tid = ?");
            ps.setInt(1, typeId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getTypeIdByName(String name) throws SQLException {
        int id = 0;
        PreparedStatement ps = conn.prepareStatement("SELECT tid "
                + "FROM room_type WHERE tname = ? AND deleted = 0");
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            id = rs.getInt(1);
        }
        rs.close();
        ps.close();

        return id;
    }
}
