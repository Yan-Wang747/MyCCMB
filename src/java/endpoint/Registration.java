/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoint;

import abstractendpoint.AbstractEndpoint;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import other.AuthenticationInfo;

/**
 *
 * @author student
 */
public class Registration extends AbstractEndpoint {
    
    private enum Errors {
        noError,
        duplicateUser
    }
    
    private void createNewUser(AuthenticationInfo info) throws SQLException {
        String sql = "insert into Account values('" + info.userID + "', '" + info.password + "')";
        
        db.executeUpdate(sql);
    }

    private boolean isUserExists(AuthenticationInfo info) throws SQLException {

        String sql = "select * from Account where ID = '" + info.userID + "'";

        ResultSet res = db.executeQuery(sql);
        return res.next();
    }
    
    private void registerNewUser(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String authString = request.getHeader("Registration");
        
        if(authString == null) {
          response.sendError(403, "No new userID and password");
          
          return;
        } 
        
        AuthenticationInfo info = new AuthenticationInfo(authString);
        
        PrintWriter writer = response.getWriter();

        if(isUserExists(info))
            writer.write("" + Errors.duplicateUser.ordinal());
        else {
            createNewUser(info);
            writer.write("" + Errors.noError.ordinal());
        }
    }
    
    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        registerNewUser(request, response);
    }
}
