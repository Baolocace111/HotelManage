/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hotel.DAL.ReportDAL;

import Hotel.DAL.pool.ConnectionPool;
import java.sql.*;

/**
 *
 * @author Yue
 */
public enum ReportType {
    DOANH_SO_DAT_PHONG,
    DOANH_THU_PHONG,
    DOANH_THU_DICH_VU;

    @Override
    public String toString() {
        switch (this) {
            case DOANH_SO_DAT_PHONG:
                return "Doanh số đặt phòng";
            case DOANH_THU_PHONG:
                return "Doanh thu hồ sơ thuê phòng";
            case DOANH_THU_DICH_VU:
                return "Doanh thu dịch vụ";
        }
        return null;
    }

    public static ReportType SerialOf(int type) {
        switch (type) {
            case 0:
                return DOANH_SO_DAT_PHONG;
            case 1:
                return DOANH_THU_PHONG;
            case 2:
                return DOANH_THU_DICH_VU;
        }
        return null;
    }

    public String getSQL() {
        switch (this) {
            case DOANH_SO_DAT_PHONG:
                return "SELECT ngaydat, madatphong, soluongphong, tongcoc FROM hosothuephong";
            case DOANH_THU_PHONG:
                return "SELECT mathuephong, madatphong, tongthanhtoan FROM hosothuephong";
            case DOANH_THU_DICH_VU:
                return "SELECT s.sname, bs.bstime, bs.quantity, s.sprice, (bs.bsprice * bs.quantity) FROM booking_service bs JOIN service s ON bs.sid = s.sid";
        }
        return null;
    }

    public ResultSet executeQuery() {
        String query = getSQL();
        try (Connection conn = ConnectionPool.getconnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Xử lý ngoại lệ theo nhu cầu của bạn, ví dụ: ghi log, ném ngoại lệ khác, hoặc trả về null
            return null;
        }
    }

    public Object[] getColumnHeader() {
        switch (this) {
            case DOANH_SO_DAT_PHONG:
                return new Object[]{"Ngày đặt", "Mã đơn", "Số lượng phòng", "Tổng cọc"};
            case DOANH_THU_PHONG:
                return new Object[]{"Ngày", "Mã thuê phòng", " Số lượng phòng", " Tổng thanh toán"};
            case DOANH_THU_DICH_VU:
                return new Object[]{"Tên dịch vụ", "Ngày dùng", "Số lượng", "Đơn giá", "Thành tiền"};
        }
        return null;
    }

}
