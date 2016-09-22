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
public class Chef {

    private String quantity;
    private String allergies;
    private String exclusions;
    private String route;
    private String mealType;

    public Chef(String quantity, String allergies, String exclusions, String route, String mealType) {
        this.quantity = quantity;
        this.allergies = allergies;
        this.exclusions = exclusions;
        this.route = route;
        this.mealType = mealType;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getExclusions() {
        return exclusions;
    }

    public void setExclusions(String exclusions) {
        this.exclusions = exclusions;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

   
    
}
