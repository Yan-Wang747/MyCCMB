package endpoint;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import support.AuthInfo;
import database.DBAccess;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.sql.*;

/**
 *
 * @author student
 */
public class AppLogin extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private String sql = "SELECT * FROM AccountInfo";
    
    private boolean authenticate(AuthInfo info) {
        boolean result = false;
       
        try {
            DBAccess db = (DBAccess)this.getServletContext().getAttribute("db");
            
            ResultSet rs = db.executeQuery(sql);
            while(rs.next()) {
                if (rs.getString("email").equals(info.userID) && rs.getString("password").equals(info.password))
                    result = true;
            }
            
        } catch (SQLException e) {
            System.err.println("database access error: " + e.getMessage());
        }
        
        return result;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String authString = request.getHeader("authorization");
        if (authString == null) {
            response.sendError(401);
            
            return;
        }
        
        AuthInfo info = new AuthInfo(authString);
        if (!info.type.equals("Basic")) {
            response.sendError(401, "Please use basic method.");
            
            return;
        }
        
        if(authenticate(info)) {
            HttpSession session = request.getSession();
            session.setAttribute("userID", info.userID);
        } else 
            response.sendError(403);

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
