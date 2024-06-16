package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class MyConnection {

    public static final String username = "root";
    public static final String password = "0976794934t";
    public static final String url = "jdbc:mysql://localhost:3306/online_shopping?useSSL=false";
    public static final String driver = "com.mysql.cj.jdbc.Driver";

    public static Connection connection;

    public static Connection getConnection() {

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi kết nối cơ sở dữ liệu!");
        }
        return connection;
    }
    
    public static void closeConnection(Connection connection){
        try{
            if(connection!=null){
                connection.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
