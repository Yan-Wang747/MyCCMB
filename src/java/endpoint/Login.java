/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoint;

import database.DBAccess;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import support.LoginInfo;

/**
 *
 * @author student
 */
public class Login extends HttpServlet {


    private boolean isIDInfoValid(LoginInfo info) {
        boolean result = false;

        try {
            DBAccess db = (DBAccess)this.getServletContext().getAttribute("db");
            String sql = "select exists (select * from Account where ID = '" + info.userID + "' and password = '" + info.password + "')";
        
            ResultSet res = db.executeQuery(sql);
            res.next();
            
            return res.getInt(1) == 1;
 
        } catch (SQLException e) {
            System.err.println("database access error: " + e.getMessage());
        }
        
        return result;
    }
    
    private void authorize(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String authString = request.getHeader("Authorization");
        
        try {
            LoginInfo info = new LoginInfo(authString);
            
            if (!info.authType.toLowerCase().equals("basic")){  //check if the app using basic auth method
                response.sendError(401, "Please use basic method.");
                
                return;
            }
            
            if(isIDInfoValid(info)) 
                request.getSession().setAttribute("userID", info.userID);
            else 
                response.sendError(403, "Incorrect user name/password");
            

        } catch (UnsupportedEncodingException e) {
            response.sendError(403, "Unspported encoding");
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if ("GET".equals(request.getMethod())) {
            authorize(request, response);
        } else {
            response.sendError(405);
        }
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
