/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hotel.BLL;

import Hotel.DAL.ReportDAL.ReportDAO;
import Hotel.DAL.ReportDAL.ReportType;
import Hotel.DAL.ServiceDAO;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

/**
 *
 * @author Yue
 */
public class ReportBLL {

    private final ReportDAO reportDAO = ReportDAO.getInstance();

    public void setTimeline(int reportType, int timeType, JComboBox comboBox) {
        try {
            comboBox.removeAllItems();
            ResultSet rs = reportDAO.getTimeline(ReportType.SerialOf(reportType), timeType);
            while (rs.next()) {
                comboBox.addItem(rs.getString(1));
            }
            rs.getStatement().close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportBLL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getServiceNameList(JComboBox comboBox) {
        try {
            comboBox.removeAllItems();
            comboBox.addItem("Tất cả");
            ResultSet rs = ServiceDAO.getInstance().getService();
            while (rs.next()) {
                comboBox.addItem(rs.getString("sname"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReportBLL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DefaultTableModel getReport_ORDER_STATISTIC(ReportCondition reportCondition) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(ReportType.DOANH_SO_DAT_PHONG.getColumnHeader());
        try {
            ResultSet rs = reportDAO.getReport(ReportType.DOANH_SO_DAT_PHONG, reportCondition);
            java.sql.Date previousDate = null;
            int count = 0;
            long totalDeposit = 0;
            while (rs.next()) {
                java.sql.Date currentDate = rs.getDate(1);
                if (!currentDate.equals(previousDate) && previousDate != null) {
                    model.addRow(new Object[]{"Tổng cộng", count, totalDeposit});
                    count = 0;
                    totalDeposit = 0;
                }
                count++;
                totalDeposit += rs.getLong(3); // Assuming that the deposit is returned as a long
                model.addRow(new Object[]{currentDate, rs.getInt(2), rs.getLong(3)});
                previousDate = currentDate;
            }
            // Add last total row if resultSet is not empty
            if (previousDate != null) {
                model.addRow(new Object[]{"Tổng cộng", count, totalDeposit});
            }
            rs.getStatement().close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportBLL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }

    public DefaultTableModel getReport_STAY_STATISTIC(ReportCondition reportCondition) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(ReportType.DOANH_THU_PHONG.getColumnHeader());
        try {
            ResultSet rs = reportDAO.getReport(ReportType.DOANH_THU_PHONG, reportCondition);
            java.sql.Date previousCheckin = null;
            long totalPayment = 0;
            int totalRoomCount = 0;
            while (rs.next()) {
                java.sql.Date currentCheckin = rs.getDate(1);
                if (!currentCheckin.equals(previousCheckin) && previousCheckin != null) {
                    // Khi chúng ta gặp một ngày check-in mới, thêm dòng tổng cộng cho ngày trước đó
                    model.addRow(new Object[]{"Tổng cộng", "", "", totalRoomCount, totalPayment});
                    totalRoomCount = 0;
                    totalPayment = 0;
                }
                int roomCount = rs.getInt(4); // Assuming the count of rooms is the fourth column
                long payment = rs.getLong(5); // Assuming the payment amount is the fifth column
                Object[] obj = new Object[]{
                    rs.getDate(1), // Check-in date
                    rs.getDate(2), // Check-out date
                    rs.getInt(3), // Room ID
                    roomCount,
                    payment
                };
                model.addRow(obj);

                totalRoomCount += roomCount;
                totalPayment += payment;
                previousCheckin = currentCheckin;
            }
            // Add last total row if resultSet is not empty
            if (previousCheckin != null) {
                model.addRow(new Object[]{"Tổng cộng", "", "", totalRoomCount, totalPayment});
            }
            rs.getStatement().close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportBLL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }

    public DefaultTableModel getReport_SERVICE_STATISTIC(ReportCondition reportCondition) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(ReportType.DOANH_THU_DICH_VU.getColumnHeader());
        ResultSet rs;
        try {
            rs = reportDAO.getReport(ReportType.DOANH_THU_DICH_VU, reportCondition);
            String dupe = null;
            int count = 0;
            long money = 0;
            while (rs.next()) {
                Object[] obj = new Object[model.getColumnCount()];
                if (dupe == null) {
                    obj[0] = rs.getString(1);
                    dupe = rs.getString(1);
                    count = 0;
                    money = 0;
                } else if (rs.getString(1).equals(dupe)) {
                    obj[0] = "";
                } else {
                    obj[0] = "Tổng cộng";
                    obj[2] = count;
                    obj[4] = money;
                    model.addRow(obj);
                    dupe = null;
                    rs.previous();
                    continue;
                }
                obj[1] = rs.getDate(2);
                obj[2] = rs.getInt(3);
                count += rs.getInt(3);
                obj[3] = rs.getInt(4);
                obj[4] = rs.getInt(5);
                money += rs.getInt(5);
                model.addRow(obj);
            }
            model.addRow(new Object[]{
                "Tổng cộng",
                "",
                count,
                "",
                money
            });
            rs.getStatement().close();
        } catch (SQLException ex) {
            Logger.getLogger(ReportBLL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }

    public void exportToExcel(JTable table, File file) {
        String path = file.toString().concat(".xlsx");
        new Hotel.DAL.ReportDAL.ExcelExporter().exportToExcel(table, path);
    }
}
