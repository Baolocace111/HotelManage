package form;

import java.sql.*;
import javax.swing.*;

public class mySqlConnection {
    Connection conn = null;
    public static Connection ConnectDB(){
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/smarthotel?characterEncoding=UTF-8","root","123456");
        
        return conn;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
}
