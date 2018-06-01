/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoint;

import database.DBAccess;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import other.UserInfo;

/**
 *
 * @author student
 */
public class RegisterNewUser extends HttpServlet {
    
    private enum Errors {
        noError,
        duplicateUser
    }
    
    private void createNewUser(UserInfo info) throws SQLException {
        String sql = "insert into Account values('" + info.userID + "', '" + info.password + "')";
        
        try {
            DBAccess db = (DBAccess)this.getServletContext().getAttribute("db");
            
            if(db == null)
                throw new SQLException();
            
            int rowsAffected = db.executeUpdate(sql);
            System.out.println(rowsAffected);
        } catch (SQLException e) {
            System.err.println("database access error: " + e.getMessage());
        }
    }

    private boolean isUserExists(UserInfo info) throws SQLException {
       
        DBAccess db = (DBAccess)this.getServletContext().getAttribute("db");

        if(db == null) {
            throw new SQLException();
        }

        String sql = "select exists (select * from Account where ID = '" + info.userID + "')";

        ResultSet res = db.executeQuery(sql);
        res.next();

        return res.getInt(1) == 1;
    }
    
    private void registerNewUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authString = request.getHeader("Authorization");
        
        if(authString == null) {
          response.sendError(403, "No authorization information");
          
          return;
        } 
        
        UserInfo info = new UserInfo(authString);
            
        if (!"basic".equals(info.authType.trim().toLowerCase())){  //check if the app using basic auth method
            response.sendError(401, "Please use basic method.");

            return;
        }
        
        PrintWriter writer = response.getWriter();
        try {
            if(isUserExists(info))
                writer.write("" + Errors.duplicateUser.ordinal());
            else {
                createNewUser(info);
                int states = Errors.noError.ordinal();
                String s = "" + 0;
                writer.write("" + Errors.noError.ordinal());
            }
        } catch(SQLException e) {
            
        }
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if ("get".equals(request.getMethod().trim().toLowerCase())) 
            registerNewUser(request, response);
        else 
            response.sendError(405); //other methods are not allowed
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
