/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC;

import java.util.Calendar;

/**
 *
 * @author Aliens_Ross
 */
public class RouteDrivers {

    private Driver driver;
    private Calendar endDate;
    private Calendar startDate;

    public RouteDrivers(Driver driver, Calendar endDate, Calendar startDate) {
        this.driver = driver;
        this.endDate = endDate;
        this.startDate = startDate;
    }
    
    public RouteDrivers(String name){
        driver = new Driver(name);
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }
    
}
