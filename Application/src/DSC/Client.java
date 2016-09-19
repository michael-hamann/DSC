/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC;

/**
 *
 * @author Aliens_Ross
 */
public class Client implements java.io.Serializable{
    
    private String clientID;
    private String name;
    private String surname;
    private String contactNumber;
    private String alternativeNumber;
    private String email;
    private String suburb;
    private String address;
    private String additionalInfo;

    public Client() {
        
    }

    public Client(String clientID, String name, String surname, String contactNumber, String alternativeNumber, String email, String suburb, String address, String additionalInfo) {
        this.clientID = clientID;
        this.name = name;
        this.surname = surname;
        this.contactNumber = contactNumber;
        this.alternativeNumber = alternativeNumber;
        this.email = email;
        this.suburb = suburb;
        this.address = address;
        this.additionalInfo = additionalInfo;
    }

    public String getID() {
        return clientID;
    }

    public void setID(String clientID) {
        this.clientID = clientID;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAlternativeNumber() {
        return alternativeNumber;
    }

    public void setAlternativeNumber(String alternativeNumber) {
        this.alternativeNumber = alternativeNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }
    
    
    
}
