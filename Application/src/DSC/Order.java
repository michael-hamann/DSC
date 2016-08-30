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
public class Order {
    private String orderID;
    private boolean active;
    private Client client;
    private String duration;
    private Calendar startingDate;
    private Calendar endDate;
    private String route;
    private ArrayList<Meal> meals;

    public Order(String orderID, boolean active, Client client, String duration, Calendar startingDate, Calendar endDate, String route, ArrayList<Meal> meals) {
        this.orderID = orderID;
        this.active = active;
        this.client = client;
        this.duration = duration;
        this.startingDate = startingDate;
        this.endDate = endDate;
        this.route = route;
        this.meals = meals;
    }
    
    

    public String getID() {
        return orderID;
    }

    public void setID(String orderID) {
        this.orderID = orderID;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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

    public ArrayList<Meal> getMeals() {
        return meals;
    }

    public void setMeals(ArrayList<Meal> meals) {
        this.meals = meals;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
    
    
    
}
