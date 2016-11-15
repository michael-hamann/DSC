
package DSC;

/**
 *
 * @author Aliens_Ross
 */
public class RouteDrivers {

    private String DriverID;
    private String EndDate;
    private String StartDate;
    private Driver Driver;

    public RouteDrivers(String DriverID, String EndDate, String StartDate) {
        this.DriverID = DriverID;
        this.EndDate = EndDate;
        this.StartDate = StartDate;
    }
    
    public RouteDrivers(Driver Driver, String EndDate, String StartDate) {
        this.Driver = Driver;
        this.EndDate = EndDate;
        this.StartDate = StartDate;
    }

    public Driver getDriver() {
        return Driver;
    }

    public void setDriver(Driver Driver) {
        this.Driver = Driver;
    }

    public String getDriverID() {
        return DriverID;
    }

    public void setDriverID(String driverID) {
        this.DriverID = driverID;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        this.EndDate = endDate;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        this.StartDate = startDate;
    }
    
}
