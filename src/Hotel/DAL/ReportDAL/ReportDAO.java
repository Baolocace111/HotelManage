/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hotel.DAL.ReportDAL;

import Hotel.BLL.ReportCondition;
import Hotel.DAL.DbConn;
import java.sql.*;

/**
 *
 * @author Yue
 */
public class ReportDAO {

    private final Connection conn = DbConn.getConnection();
    private static ReportDAO instance = null;

    private ReportDAO() {

    }

    public static ReportDAO getInstance() {
        if (instance == null) {
            instance = new ReportDAO();
        }
        return instance;
    }

    public ResultSet getTimeline(ReportType reportType, int timeType) throws SQLException {
        String sql = null;
        if (null == reportType) {

        } else {
            switch (reportType) {
                case DOANH_SO_DAT_PHONG:
                    sql = "SELECT date_format(convert(btime, DATE), ?) as thang "
                            + "FROM booking group by date_format(convert(btime, DATE),?) "
                            + "ORDER BY btime DESC";
                    break;
                case DOANH_THU_PHONG:
                    sql = "SELECT date_format(convert(paytime, DATE), ?) as thang "
                            + "FROM booking "
                            + "WHERE NOT isnull(paytime) "
                            + "GROUP BY date_format(convert(paytime, DATE),?) "
                            + "ORDER BY paytime DESC";
                    break;
                case DOANH_THU_DICH_VU:
                    sql = "SELECT date_format(convert(bstime, DATE), ?) as thang "
                            + "FROM booking_service group by date_format(convert(bstime, DATE),?) "
                            + "ORDER BY bstime DESC";
            }
        }
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, timeType == 0 ? "%m/%Y" : "%Y");
        ps.setString(2, timeType == 0 ? "%m/%Y" : "%Y");
        ResultSet rs = ps.executeQuery();

        return rs;
    }

    public ResultSet getReport(ReportType reportType, ReportCondition reportCondition) throws SQLException {
        String sql = reportType.getSQL(); // Lấy câu truy vấn tương ứng với loại báo cáo
        switch (reportType) {
            case DOANH_SO_DAT_PHONG:
                // Thêm điều kiện cho báo cáo doanh số đặt phòng
                sql += " WHERE btime BETWEEN ? AND ? ORDER BY bid"; // Thêm điều kiện ngày đặt phòng
                break;
            case DOANH_THU_PHONG:
                // Thêm điều kiện cho báo cáo doanh thu phòng
                sql = "SELECT br.real_checkin, br.real_checkout, br.brid, COUNT(*) AS record_count, SUM(b.total) AS tongthanhtoan "
                        + "FROM booking_room br "
                        + "JOIN booking b ON br.brid = b.bid "
                        + "WHERE paytime BETWEEN ? AND ? AND NOT isnull(paytime) " // Điều kiện WHERE
                        + "GROUP BY br.brid " // GROUP BY sau WHERE
                        + "ORDER BY brid";
                break;
            case DOANH_THU_DICH_VU:
                // Thêm điều kiện cho báo cáo doanh thu dịch vụ
                sql += " WHERE bstime BETWEEN ? AND ? ORDER BY bstime"; // Thêm điều kiện ngày sử dụng dịch vụ
                break;
        }
        PreparedStatement ps = conn.prepareStatement(sql);
        // Sử dụng kiểu dữ liệu Timestamp thay vì Date
        ps.setTimestamp(1, new Timestamp(reportCondition.getFrom().getTime())); // Đặt tham số ngày bắt đầu
        ps.setTimestamp(2, new Timestamp(reportCondition.getTo().getTime())); // Đặt tham số ngày kết thúc
        System.out.println(ps.toString());
        ResultSet rs = ps.executeQuery(); // Thực hiện truy vấn
        return rs; // Trả về kết quả
    }

    public ResultSet getReportAddingCondition(ReportType reportType, ReportCondition reportCondition, String condition)
            throws SQLException {
        String sql = reportType.getSQL(); // Lấy câu truy vấn tương ứng với loại báo cáo
        sql += " " + condition; // Thêm điều kiện bổ sung
        PreparedStatement ps = conn.prepareStatement(sql);
        // Sử dụng kiểu dữ liệu Timestamp thay vì Date
        ps.setTimestamp(1, new Timestamp(reportCondition.getFrom().getTime())); // Đặt tham số ngày bắt đầu
        ps.setTimestamp(2, new Timestamp(reportCondition.getTo().getTime())); // Đặt tham số ngày kết thúc
        System.out.println(ps.toString());
        ResultSet rs = ps.executeQuery(); // Thực hiện truy vấn
        return rs; // Trả về kết quả
    }
}
