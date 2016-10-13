
package DSC;

/**
 *
 * @author Aliens_Ross
 */
public class RouteDrivers {

    private String DriverID;
    private String EndDate;
    private String StartDate;

    public RouteDrivers(String driverID, String endDate, String startDate) {
        this.DriverID = driverID;
        this.EndDate = endDate;
        this.StartDate = startDate;
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
