package database;

import java.sql.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author student
 */
public class DBAccess {
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String user = "root";
    private static final String pswd= "12345678";
    private static final String dbURL = "jdbc:mysql://localhost/users?useLegacyDatetimeCode=false&serverTimezone=Australia/Melbourne";
    private static Connection conn;
    
    
    static {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(dbURL, user, pswd);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("error: " + e.getMessage());
        }
    }
    
    
    public static ResultSet executeQuery(String sql) throws SQLException {
        Statement smt = conn.createStatement();
        return smt.executeQuery(sql);
    }
    
    public static int executeUpdate(String sql) throws SQLException {
        Statement smt = conn.createStatement();
        return smt.executeUpdate(sql);
    }
}
