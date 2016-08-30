/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC;

import java.util.Date;


/**
 *
 * @author Amina
 */
public class Orders {
    
    private String orderid;
    private String active;
    private String duration;
    private String familySize;
    private String routeId;
    private String startingDate;
    private String orderclientid;
    
    private String Mealid;
    private String Mealtype;

    public String getMealid() {
        return Mealid;
    }

    public void setMealid(String Mealid) {
        this.Mealid = Mealid;
    }

    public String getMealtype() {
        return Mealtype;
    }

    public void setMealtype(String Mealtype) {
        this.Mealtype = Mealtype;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String Quantity) {
        this.Quantity = Quantity;
    }

    public String getAllergy() {
        return Allergy;
    }

    public void setAllergy(String Allergy) {
        this.Allergy = Allergy;
    }

    public String getExclusions() {
        return Exclusions;
    }

    public void setExclusions(String Exclusions) {
        this.Exclusions = Exclusions;
    }
    private String Quantity;
    private String Allergy;
    private String Exclusions;
    

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFamilySize() {
        return familySize;
    }

    public void setFamilySize(String familySize) {
        this.familySize = familySize;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

   
    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrderClientid() {
        return orderclientid;
    }

    public void setOrderClientid(String orderclientid) {
        this.orderclientid = orderclientid;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    
    
    
}
