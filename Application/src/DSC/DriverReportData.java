/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC;

/**
 *
 * @author Aliens_Keanu
 */
public class DriverReportData {

    private String name;
    private String Surname;
    private String ContactNumber;
    private String Address;
    private String AdditionalInfo;

    public DriverReportData(String name, String Surname, String ContactNumber, String Address, String AdditionalInfo) {
        this.name = name;
        this.Surname = Surname;
        this.ContactNumber = ContactNumber;
        this.Address = Address;
        this.AdditionalInfo = AdditionalInfo;
    }

    DriverReportData() {
       
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String Surname) {
        this.Surname = Surname;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String ContactNumber) {
        this.ContactNumber = ContactNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getAdditionalInfo() {
        return AdditionalInfo;
    }

    public void setAdditionalInfo(String AdditionalInfo) {
        this.AdditionalInfo = AdditionalInfo;
    }

    
}
