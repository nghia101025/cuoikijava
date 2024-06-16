package Dao;

import connection.MyConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author nghia123
 */
public class UserDao {

    Connection con = MyConnection.getConnection();
    PreparedStatement ps;
    ResultSet rs;
    Statement st;

    // Get ID 
    public int getMaxRow() {
        int row = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select max(uid) from user");
            while (rs.next()) {
                row = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row + 1;
    }

    // Check Email có tồn tại chưa
    public boolean isEmailExist(String email) {
        try {
            ps = con.prepareStatement("select * from user where uemail = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // Check Phone có tồn tại chưa
    public boolean isPhoneExist(String phone) {
        try {
            ps = con.prepareStatement("select * from user where uphone = ?");
            ps.setString(1, phone);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // Thêm tài khoản
    public void insert(int id, String username, String email, String pass, String phone, String seq, String ans, String address1, String address2) {
        String passwork = MD5.md5(pass);
        String sql = "insert into user values (?,?, ?, ?, ?, ?, ?, ?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, username);
            ps.setString(3, email);
            ps.setString(4, passwork);
            ps.setString(5, phone);
            ps.setString(6, seq);
            ps.setString(7, ans);
            ps.setString(8, address1);
            ps.setString(9, address2);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Đăng ký tài khoản thành công");
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void update(int id, String username, String email, String pass, String phone, String seq, String ans, String address1, String address2) {
        String passwork = MD5.md5(pass);

        String sql = "update user set uname = ?, uemail = ?,upassword = ?, uphone = ?, usecqus = ?, uans = ?, uaddress1 = ?, uaddress2 = ? where uid = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, passwork);
            ps.setString(4, phone);
            ps.setString(5, seq);
            ps.setString(6, ans);
            ps.setString(7, address1);
            ps.setString(8, address2);
            ps.setInt(9, id);

            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Thông tin đã được đổi thành công");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(int id) {
        int x = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa tài khoản?", "Xóa tài khoản", JOptionPane.OK_CANCEL_OPTION, 0);
        if (x == JOptionPane.OK_OPTION) {
            try {
                ps = con.prepareStatement("Delete from user where uid = ?");
                ps.setInt(1, id);
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Xóa tài khoản thành công");
                    System.exit(0);
                }

            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String[] getUserValue(int id) {
        String[] value = new String[9];
        try {
            ps = con.prepareStatement("Select * from user where uid = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                value[0] = rs.getString(1);
                value[1] = rs.getString(2);
                value[2] = rs.getString(3);
                value[3] = rs.getString(4);
                value[4] = rs.getString(5);
                value[5] = rs.getString(6);
                value[6] = rs.getString(7);
                value[7] = rs.getString(8);
                value[8] = rs.getString(9);

            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return value;
    }

    public int getUserID(String email) {
        int id = 0;
        try {
            ps = con.prepareStatement("select * from user where uemail = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public void getUsersValue(JTable table, String search) {
        String sql = "select * from user where concat(uid,uname,uemail) like ? order by uid desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[9];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(5);
                row[4] = rs.getString(6);
                row[5] = rs.getString(7);
                row[6] = rs.getString(8);
                row[7] = rs.getString(9);
                model.addRow(row);

            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
