
package Dao;

import connection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import user.ForgotPassword;

/**
 *
 * @author nghia123
 */
public class ForgotPasswordDao {

    Connection con = MyConnection.getConnection();
    PreparedStatement ps;
    ResultSet rs;
    Statement st;

    // Check Email có tồn tại hay không
    public boolean isEmailExist(String email) {
        try {
            ps = con.prepareStatement("select * from user where uemail = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                ForgotPassword.jTextField3.setText(rs.getString(6));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Email không tồn tại");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // Check câu trả lời đúng hay không
    public boolean isAns(String email, String newAns) {
        try {
            ps = con.prepareStatement("select * from user where uemail = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                String oldAns = rs.getString(7);
                if (newAns.equals(oldAns)) {
                    return true;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Câu trả lời không chính xác");
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // Cập nhật mật khẩu mới
    public void setPassword(String email, String pass) {
        String passwork = MD5.md5(pass);
        
        String sql = "update user set upassword = ? where uemail = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, passwork);
            ps.setString(2, email);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Mật khẩu đã được đổi thành công");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
