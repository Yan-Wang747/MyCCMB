/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoint;

import abstractendpoint.AbstractEndpoint;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import other.AuthenticationInfo;

/**
 *
 * @author student
 */
public class Login extends AbstractEndpoint {

    private boolean authenticate(AuthenticationInfo info) throws SQLException {

        String sql = "select exists (select * from Account where ID = '" + info.userID + "' and password = '" + info.password + "')";

        ResultSet res = db.executeQuery(sql);
        res.next();

        return res.getInt(1) == 1;
    }
    
    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String authString = request.getHeader("Authorization");
        
        if(authString == null) {
          response.sendError(403, "No authorization information");
          
          return;
        } 
        
        try {
            AuthenticationInfo info = new AuthenticationInfo(authString);
 
            if (!info.authType.toLowerCase().equals("basic")){  //check if the app using basic auth method
                response.sendError(401, "Please use basic method.");

                return;
            }

            if(authenticate(info))
                request.getSession().setAttribute("userID", info.userID);
            else
                response.sendError(403, "Incorrect user name/password");
            
        } catch(UnsupportedEncodingException e) {
            response.sendError(400, "Can't decrypt authentication info");
        }
    }
}
