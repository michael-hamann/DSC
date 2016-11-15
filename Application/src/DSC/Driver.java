package DSC;

/**
 *
 * @author Aliens_Ross
 */
public class Driver implements java.io.Serializable {

<<<<<<< HEAD
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
        this.active = true;
=======
    private String DriverID;
    private boolean Active;
    private String Address;
    private String ContactNumber;
    private String DriverName;
    private String VehicleRegistration;

    public Driver(String Address, String ContactNumber, String DriverName, String VehicleRegistration) {
        this.Address = Address;
        this.ContactNumber = ContactNumber;
        this.DriverName = DriverName;
        this.VehicleRegistration = VehicleRegistration;
>>>>>>> origin/master
    }

    public Driver(String id) {
        this.DriverID = id;
    }

    public Driver() {

    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.ContactNumber = contactNumber;
    }

    public String getDriverName() {
        return DriverName;
    }

    public void setDriverName(String driverName) {
        this.DriverName = driverName;
    }

    public String getVehicleRegistration() {
        return VehicleRegistration;
    }

    public void setVehicleRegistration(String vehicleRegistration) {
        this.VehicleRegistration = vehicleRegistration;
    }

    public String getID() {
        return DriverID;
    }

    public void setID(String driverID) {
        this.DriverID = driverID;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        this.Active = active;
    }

}
