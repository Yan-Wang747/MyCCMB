/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoint;

import abstractendpoint.AbstractInfoEndpoint;

/**
 *
 * @author student
 */
public class Tobacco extends AbstractInfoEndpoint {

    @Override
    protected String columnName() {
        return "Tobacco";
    }
}
