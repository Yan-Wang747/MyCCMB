/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abstractendpoint;

import database.DB;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author student
 */
public abstract class AbstractEndpoint extends HttpServlet {
    
    protected DB db = null;

    protected void processGet(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        response.sendError(405);
    }
    
    protected void processPost(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        response.sendError(405);
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
        
        try {
            db = (DB)this.getServletContext().getAttribute("db");
            
            if(db == null)
                throw new SQLException();
            
            if ("GET".equals(request.getMethod().toUpperCase())) { // get is used to retrieve records
                processGet(request, response);

            } else if ("POST".equals(request.getMethod().toUpperCase())) { //post is used to create/update new records
                processPost(request, response);
            }
        } catch(SQLException e) {
            System.err.println("database error: " + e.getLocalizedMessage());
            response.sendError(500, "database error");
        } catch(IOException e) {
            System.err.println("IO error: " + e.getLocalizedMessage());
            response.sendError(500, "IO error");
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
