package DSC;

/**
 *
 * @author Aliens_Ross
 */
public class Driver implements java.io.Serializable {

    private String driverID;
    private boolean active;
    private String address;
    private String contactNumber;
    private String driverName;
    private String vehicleRegistration;

    public Driver(String address, String contactNumber, String driverName, String vehicleRegistration) {
        this.address = address;
        this.contactNumber = contactNumber;
        this.driverName = driverName;
        this.vehicleRegistration = vehicleRegistration;
    }

    public Driver(String id) {
        this.driverID = id;
    }

    public Driver() {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
