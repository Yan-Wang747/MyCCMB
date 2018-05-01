/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import patientinfo.BasicInfo;
import java.sql.*;
/**
 *
 * @author student
 */
public class GetBasicInfo extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private String sql = "SELECT * FROM BasicInfo";
    private DBAccess dbAccess;
    
    private BasicInfo getBasicInfo(String userID) {
        try 
        {
            DBAccess dbAccess = new DBAccess();
            ResultSet rs = dbAccess.executeQuery(sql);

            while(rs.next()) {
                if(rs.getString("email").equals(userID)) {
                    System.out.println(rs.getString("email"));
                    System.out.println(rs.getString("first_name"));
                    System.out.println(rs.getString("last_name"));
                    System.out.println(rs.getString("gender"));
                    System.out.println(rs.getString("date_of_birth"));
                    System.out.println(rs.getString("phone"));
                }
            }
            
            dbAccess.dispose();
        } catch (SQLException e) {
            System.err.println("sql error: " + e.getMessage());
        } 
        
        return null;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null) {
            response.sendError(403);
        }
        
        String userID = (String)session.getAttribute("userID");
        getBasicInfo(userID);
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
