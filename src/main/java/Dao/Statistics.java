package Dao;

import connection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import admin.AdminDashboard;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import user.UserDashboard;
import Supplier.SupplierDashboard;

public class Statistics {
    
    SupplierDashboard supplierDashboard;
    AdminDashboard adminDashboard;
    UserDashboard dashboard;
    Connection con = MyConnection.getConnection();
    PreparedStatement ps;
    ResultSet rs;
    Statement st;

    private int total(String tableName) {
        int total = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("Select count(*) as 'total' from " + tableName + "");
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    private double totalSales() {
        double total = 0.0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("Select sum(total) as 'total' from purchase");
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    private double TodaySales() {
        double total = 0.0;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = new Date();
        String today = df.format(date);

        try {
            st = con.createStatement();
            rs = st.executeQuery("Select sum(total) as 'total' from purchase where p_date = '" + today + "'");
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    private double totalSPurchase(int id) {
        double total = 0.0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("Select sum(total) as 'total' from purchase where uid = " + id);
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
    
    private int totalDeliveries(String name) {
        int total = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("Select count(*) as 'total' from purchase where supplier = '"+name+"' and status = 'Đã giao hàng'");
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Statistics.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    public void admin() {
        AdminDashboard.jCat.setText(String.valueOf(total("category")));
        AdminDashboard.jPro.setText(String.valueOf(total("product")));
        AdminDashboard.jUsers.setText(String.valueOf(total("user")));
        AdminDashboard.jSuppliers.setText(String.valueOf(total("supplier")));
        AdminDashboard.jCat.setText(String.valueOf(total("category")));
        AdminDashboard.jSales.setText(String.valueOf(totalSales()));
        AdminDashboard.jTsales.setText(String.valueOf(TodaySales()));

    }

    public void user(int id) {
        UserDashboard.jCat.setText(String.valueOf(total("category")));
        UserDashboard.jPro.setText(String.valueOf(total("product")));
        UserDashboard.jPurchase.setText(String.valueOf(totalSPurchase(id)));

    }
    
    public void supplier(String name){
        SupplierDashboard.jDeliverys.setText(String.valueOf(totalDeliveries(name)));
    }
}
