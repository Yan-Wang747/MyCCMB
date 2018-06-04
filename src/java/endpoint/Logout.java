/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoint;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author student
 */
public class Logout extends AbstractInfoEndpoint {

    protected void processGet(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        if(isSessionValid(request))
            request.getSession(false).invalidate();
        else
            response.sendError(400, "Not a valid session");
    }
}
