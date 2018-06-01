/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoint;

import database.DBAccess;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author student
 */
public class AbstractEndpoint extends HttpServlet {
    
    //abstract function, subclass will override it to provide the column name
    protected String columnName() {
        return null;
    }
    
    //abstract function, subclass will override it to provide the table name
    protected String tableName() {
        return "Info";
    }
    
    protected String getSql(String userID) {
        String colName = columnName();
        String tableName = tableName();
        
        return "select " + colName + " from " + tableName + " where ID = '" + userID + "'";
    }

    
    protected String updateSql(String userID, String jsonData) {
        String colName = columnName();
        String tableName = tableName();
        
        return "update " + tableName + " set " + colName + " = '" + jsonData + "' " + 
                "where ID = '" + userID + "'";
    }
    
    protected String createSql(String userID, String jsonData) {
        String tableName = tableName();
        
        return "insert into " + tableName + " values ('" + userID + "', '" + jsonData + "')";
    }
    
    private String getInfo(String userID) {
        String res = null;
        
        String sql = getSql(userID);
        
        try {
            DBAccess db = (DBAccess)this.getServletContext().getAttribute("db");
            
            ResultSet dbRes = db.executeQuery(sql);
           
            if(dbRes.next())
                res = dbRes.getString(columnName());
        
        } catch(SQLException e) {
            System.err.println("database error: " + e.getMessage());
        } catch(Exception e) {
            System.err.println("unexpected error: ");
        }
            
        return res;
    }
    
    private boolean createInfo(String userID, String jsonData) {
        boolean created = false;
        
        String sql = createSql(userID, jsonData);
        
        try {
           DBAccess db = (DBAccess)this.getServletContext().getAttribute("db");
           created = db.executeUpdate(sql) != 0;
           
        } catch(SQLException e) {
            System.err.println("database error: " + e.getMessage());
        } catch(Exception e) {
            System.err.println("unexpected error: ");
        }
        
        return created;
    }
    
    private boolean updateInfo(String userID, String jsonData) {
        boolean updated = false;
        
        try {
           String sql = updateSql(userID, jsonData);
           DBAccess db = (DBAccess)this.getServletContext().getAttribute("db");
           if (db.executeUpdate(sql) == 0) {
               updated = createInfo(userID, jsonData);
           } else
               updated = true;
        } catch(SQLException e) {
            System.err.println("database error: " + e.getMessage());
        } catch(Exception e) {
            System.err.println("unexpected error: ");
        }
        
        return updated;
    }
    
    protected boolean isUserValid (HttpServletRequest request) {
        return request.isRequestedSessionIdValid();
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if (!isUserValid(request)) {
            response.sendError(401);
            
            return;
        }
        
        HttpSession session = request.getSession();
        String userID = (String)session.getAttribute("userID");
        
        if ("GET".equals(request.getMethod().toUpperCase())) { // get is used to retrieve records
            String jsonData = getInfo(userID);
            
            if(jsonData == null) 
                response.sendError(404, "Can't find the record in database");
            else {
                PrintWriter bodyWriter = response.getWriter();
                bodyWriter.print(jsonData);
            }
            
        } else if ("POST".equals(request.getMethod().toUpperCase())) { //post is used to create/update new records
            BufferedReader reader = request.getReader();
        
            String jsonData = reader.readLine();
           
            if(!updateInfo(userID, jsonData)) 
                response.sendError(417, "Database can't create/update the record");
        }
        
//        try {
//            Thread.sleep(5000);
//        } catch(InterruptedException e) {
//            
//        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
