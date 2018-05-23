package support;

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

public class LoginInfo {
    public final String userID;
    public final String password;
    public final String authType;
    
    public LoginInfo(String authString) throws UnsupportedEncodingException {
        authString = authString.trim();
        int spaceIndex = authString.indexOf(' ');
        authType = authString.substring(0, spaceIndex);
        
        String encodedLoginInfo = authString.substring(spaceIndex + 1);
        byte[] decodedLoginInfo = Base64.getDecoder().decode(encodedLoginInfo);

        String loginInfo = new String(decodedLoginInfo, "UTF-8");

        int semicolunIndex = loginInfo.indexOf(":");
        userID = loginInfo.substring(0, semicolunIndex);
        password = loginInfo.substring(semicolunIndex + 1);
        
    }
}
