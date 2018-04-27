/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author student
 */
import java.util.Base64;
import java.io.UnsupportedEncodingException;

public class AuthInfo {
    public final String type;
    public final String userID;
    public final String password;
    
    public AuthInfo(String authString) throws UnsupportedEncodingException {
        int spaceIndex = authString.indexOf(' ');
        type = authString.substring(0, spaceIndex);
        
        if (type.equals("Basic")) {
            String encodedLoginInfo = authString.substring(spaceIndex + 1);
            byte[] decodedLoginInfo = Base64.getDecoder().decode(encodedLoginInfo);
        
            String loginInfo = new String(decodedLoginInfo, "UTF-8");
        
            int semicolunIndex = loginInfo.indexOf(":");
            userID = loginInfo.substring(0, semicolunIndex);
            password = loginInfo.substring(semicolunIndex + 1);
        } else {
            userID = null;
            password = null;
        }
    }
}
