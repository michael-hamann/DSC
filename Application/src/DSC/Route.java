/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC;

import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Aliens_Ross
 */
public class Route implements java.io.Serializable{

    private String routeID;
    private boolean active;
    private String timeFrame;
    private ArrayList<String> suburbs = new ArrayList<>();
    private ArrayList<RouteDrivers> drivers;
    private Calendar startingDate;
    private Calendar endDate;

    public Route(boolean active, String timeFrame, ArrayList<String> suburbs, ArrayList<RouteDrivers> drivers, Calendar startingDate) {
        this.active = active;
        this.timeFrame = timeFrame;
        this.suburbs = suburbs;
        this.drivers = drivers;
        this.startingDate = startingDate;
        this.endDate = null;
    }
    
    public Route(){
        
    }

    public String getID() {
        return routeID;
    }

    public void setID(String routeID) {
        this.routeID = routeID;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }

    public ArrayList<String> getSuburbs() {
        return suburbs;
    }
    
    public void addSuburb(String suburb){
        suburbs.add(suburb);
    }

    public void setSuburbs(ArrayList<String> suburbs) {
        this.suburbs = suburbs;
    }

    public ArrayList<RouteDrivers> getDrivers() {
        return drivers;
    }

    public void setDrivers(ArrayList<RouteDrivers> drivers) {
        this.drivers = drivers;
    }

    public Calendar getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Calendar startingDate) {
        this.startingDate = startingDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Route: "+routeID;
    }
    
    
}
