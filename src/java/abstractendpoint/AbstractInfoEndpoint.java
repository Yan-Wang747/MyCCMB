/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abstractendpoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author student
 */
public abstract class AbstractInfoEndpoint extends AbstractEndpoint {
    
    private String userID = null;
    
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
        String colName = columnName();
        
        return "insert into " + tableName + " (ID, " + colName + ") values ('" + userID + "', '" + jsonData + "')";
    }
    
    private String getInfo(String userID) throws SQLException {
        String res = null;
        
        String sql = getSql(userID);
        
        ResultSet dbRes = db.executeQuery(sql);
           
        if(dbRes.next())
            res = dbRes.getString(columnName());
        
        return res;
    }
    
    private void createInfo(String userID, String jsonData) throws SQLException {
        String sql = createSql(userID, jsonData);
        
        db.executeUpdate(sql);
    }
    
    private void updateInfo(String userID, String jsonData) throws SQLException {
        
        String sql = updateSql(userID, jsonData);
           
        if (db.executeUpdate(sql) == 0)
            createInfo(userID, jsonData);
    }
    
    protected boolean isSessionValid (HttpServletRequest request) {
        return request.isRequestedSessionIdValid();
    }
    
    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        
        String jsonData = getInfo(userID);

        if(jsonData == null) 
            response.sendError(404, "Can't find the record in database");
        else {
            PrintWriter bodyWriter = response.getWriter();
            bodyWriter.print(jsonData);
        }
    }
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
       
        BufferedReader reader = request.getReader();

        String jsonData = reader.readLine();

        updateInfo(userID, jsonData);
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
    
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if (!isSessionValid(request)) {
            response.sendError(401);
            
            return;
        }
        
        userID = (String)request.getSession(false).getAttribute("userID");
        
        if(userID == null) {
            response.sendError(500, "failed to retrieve user ID");
            
            return;
        }
        
        super.processRequest(request, response);
    }
}
