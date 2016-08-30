/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC;

/**
 *
<<<<<<< HEAD
 * @author Aliens_Ross
 */
public class Client {
    
    private String clientID;
    private String name;
    private String surname;
    private String contactNumber;
    private String alternativeNumber;
    private String email;
    private String suburb;
    private String address;
    private String additionalInfo;

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

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
=======
 * @author Amina
 */
public class Client {
    private String clientId ;
    private String name;
    private String surname;
    private String address;
    private String contactNum;
    private String altNum;
    private String email;
    private String addInfo;
    private String suburb;
    

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
>>>>>>> refs/remotes/origin/master
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

<<<<<<< HEAD
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
=======
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getAltNum() {
        return altNum;
    }

    public void setAltNum(String altNum) {
        this.altNum = altNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
>>>>>>> refs/remotes/origin/master
    }
    
    
    
}
