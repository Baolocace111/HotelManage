/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hotel.DAL;

import Hotel.DTO.StayProfile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Yue
 */
public class StayProfileDAO {

    private final Connection conn = DbConn.getConnection();
    private static StayProfileDAO instance = null;

    private StayProfileDAO() {

    }

    public static StayProfileDAO getInstance() {
        if (instance == null) {
            instance = new StayProfileDAO();
        }
        return instance;
    }

    public int addStayProfile(StayProfile stayProfile) throws SQLException {
        int stayId = 0;
        PreparedStatement ps = conn.prepareStatement("INSERT INTO hosothuephong "
                + "(madatphong, thucnhan) VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, stayProfile.getDondatphong().getId());
        ps.setTimestamp(2, stayProfile.getThucnhan());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            stayId = rs.getInt(1);
        }
        ps.close();
        int updatedCount = setStayedRooms(stayId, stayProfile.getDondatphong().getId());

        return updatedCount;
    }

    public int setStayedRooms(int stayId, int orderId) throws SQLException {
        int updatedCount = 0;
        PreparedStatement ps = conn.prepareStatement("UPDATE room "
                + "SET current_booking = ? WHERE rid IN "
                + "(SELECT rid FROM booking_room WHERE brid = ?)");
        ps.setInt(1, stayId);
        ps.setInt(2, orderId);
        ps.executeUpdate();
        updatedCount = ps.getUpdateCount();
        ps.close();

        return updatedCount;
    }

    public ResultSet getAllProfile() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT mathuephong, hosothuephong.madatphong, thucnhan, thuctra, tongthanhtoan "
                + "FROM hosothuephong INNER JOIN booking WHERE hosothuephong.madatphong = booking.bid ");
        return rs;
    }

    public ResultSet getProfileById(int stayId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM booking WHERE bid = ?");
        ps.setInt(1, stayId);
        ResultSet rs = ps.executeQuery();

        return rs;
    }

    public void updateProfile(StayProfile stayProfile) throws SQLException {
        try {
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement("UPDATE hosothuephong "
                    + "SET thuctra = ?, tongthanhtoan = ? WHERE mathuephong = ?");
            ps.setTimestamp(1, stayProfile.getThuctra());
            ps.setLong(2, stayProfile.getTongthanhtoan());
            ps.setInt(3, stayProfile.getMathuephong());
            ps.executeUpdate();
            if (ps.getUpdateCount() == 0) {
                throw new SQLException("Cập nhật hồ sơ thất bại");
            }
            ps.close();
            updateEmptyRoom(stayProfile.getMathuephong());
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            conn.rollback();
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(StayProfileDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateEmptyRoom(int stayId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("UPDATE room "
                + "SET current_booking = 0 WHERE current_booking = ?");
        ps.setInt(1, stayId);
        ps.executeUpdate();
        if (ps.getUpdateCount() == 0) {
            throw new SQLException("Cập nhật phòng trống thất bại");
        }
        ps.close();
    }

    public ResultSet getAllProfileTop(int count) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT mathuephong, hosothuephong.madatphong, thucnhan, thuctra, tongthanhtoan "
                + "FROM  hotel_project.hosothuephong INNER JOIN hotel_project.booking "
                + "WHERE  hotel_project.hosothuephong.madatphong =  hotel_project.booking.madatphong "
                + "ORDER BY IF(isnull(thuctra),1,0) DESC, thuctra DESC, ngaytra ASC "
                + "LIMIT 50 OFFSET ? ");
        ps.setInt(1, count);
        ResultSet rs = ps.executeQuery();
        return rs;
    }

    public ResultSet getCountProfile() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(mathuephong) "
                + "FROM  hotel_project.hosothuephong INNER JOIN  hotel_project.booking "
                + "WHERE  hotel_project.hosothuephong.madatphong =  hotel_project.booking.bid "
                + "ORDER BY IF(isnull(thuctra),1,0) DESC, thuctra DESC, ngaytra ASC ");
        return rs;
    }

    public ResultSet getService(int mathuephong) throws SQLException {
        String sql = "SELECT sname, bstime, tenroom,hotel_project.service.dongia ,soluong "
                + "FROM hotel_project.booking_service INNER JOIN hotel_project.service ON hotel_project.booking_service.madv = hotel_project.service.madv "
                + "INNER JOIN hotel_project.room ON hotel_project.booking_service.rid = hotel_project.room.rid "
                + "WHERE mathuephong = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, mathuephong);
        ResultSet rs = ps.executeQuery();
        return rs;
    }
}
