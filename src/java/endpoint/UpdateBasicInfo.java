package endpoint;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.BufferedReader;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.*;
import database.*;
import java.util.HashMap;
import java.sql.*;
/**
 *
 * @author student
 */
public class UpdateBasicInfo extends HttpServlet {

    private String baseSQL = "UPDATE BasicInfo SET ";
    private HashMap<String, String> dataBaseFieldNames = new HashMap();
    
    @Override
    public void init(){
        dataBaseFieldNames.put("FirstName", "first_name");
        dataBaseFieldNames.put("LastName", "last_name");
        dataBaseFieldNames.put("Gender", "gender");
        dataBaseFieldNames.put("DateOfBirth", "date_of_birth");
        dataBaseFieldNames.put("Phone", "phone");
        dataBaseFieldNames.put("Email", "email");
        
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
        response.sendError(400);
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
        response.sendError(400);
    }
    
    void update(BasicInfoField updateField) {
        String sql = baseSQL + dataBaseFieldNames.get(updateField.field) + "=" + "'" + updateField.newValue + "'";
        try {
            DBAccess db = (DBAccess)this.getServletContext().getAttribute("db");
            
            int rowsAffected = db.executeUpdate(sql);
            System.out.println(rowsAffected);
        } catch(SQLException e) {
            System.err.println("update database error: " + e.getMessage());
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if(!request.isRequestedSessionIdValid()) {
            response.sendError(403);
            
            return;
        }
        
        HttpSession session = request.getSession();
        String userID = (String)session.getAttribute("userID");
        BufferedReader reader = request.getReader();
        
        String JSONBody = reader.readLine();
        ObjectMapper mapper = new ObjectMapper();
        
        BasicInfoField updateField = mapper.readValue(JSONBody, BasicInfoField.class);
        update(updateField);
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
