/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import javax.servlet.*;
import database.*;
import java.sql.*;
/**
 *
 * @author student
 */
public class LifeCycleListener implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        try {
            DB db = new DB();
            context.setAttribute("db", db);
            System.out.println("database is connected.");
        } catch (SQLException e) {
            System.err.println("database connection error: " + e.getMessage());
        } catch (ClassNotFoundException e){
            System.err.println("database load error: " + e.getMessage());
        }
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        try {
            DB db = (DB)context.getAttribute("db");
            db.close();
            System.out.println("database connection is closed.");
        } catch (SQLException e) {
            System.err.println("database close error: " + e.getMessage());
        }
    }
}
