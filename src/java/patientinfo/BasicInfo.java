/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patientinfo;

/**
 *
 * @author student
 */
public class BasicInfo {
    public final String firstName;
    public final String lastName;
    public final String gender;
    public final String dateOfBirth;
    public final String phone;
    public final String email;
    
    public BasicInfo(String firstName, String lastName, String gender, String dateOfBirth, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
    }
}
