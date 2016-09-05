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
public class Driver implements java.io.Serializable{
    private String driverID;
    private boolean address;
    private String contactNumber;
    private String driverName;
    private String vehicleRegistration;

    public Driver(boolean address, String contactNumber, String driverName, String vehicleRegistration) {
        this.address = address;
        this.contactNumber = contactNumber;
        this.driverName = driverName;
        this.vehicleRegistration = vehicleRegistration;
    }

    public boolean isAddress() {
        return address;
    }

    public void setAddress(boolean address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getVehicleRegistration() {
        return vehicleRegistration;
    }

    public void setVehicleRegistration(String vehicleRegistration) {
        this.vehicleRegistration = vehicleRegistration;
    }

    public String getID() {
        return driverID;
    }

    public void setID(String driverID) {
        this.driverID = driverID;
    }
    
    
}
