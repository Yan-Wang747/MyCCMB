/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoint;

import support.BaseEndpoint;

/**
 *
 * @author student
 */
public class BasicInfo extends BaseEndpoint {
    
    @Override
    protected String columnName() {
        return "BasicInfo";
    }
    
    @Override
    protected String tableName() {
        return "BasicInfo";
    }
}
