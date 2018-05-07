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
    private Connection conn;
    
    
    static {
        try {
            Class.forName(driver);
            
        } catch (ClassNotFoundException e) {
            System.err.println("class error: " + e.getMessage());
        }
    }
    
    public DBAccess() throws SQLException {
        conn = DriverManager.getConnection(dbURL, user, pswd);
    }
    
    public ResultSet executeQuery(String sql) throws SQLException {
        Statement smt = conn.createStatement();
        return smt.executeQuery(sql);
    }
    
    public void dispose() throws SQLException {
        conn.close();
    }
}
