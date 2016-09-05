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
public class Meal implements java.io.Serializable{
    private int quantity;
    private String mealType;
    private String allergies;
    private String Exclusions;

    public Meal(int quantity, String mealType, String allergies, String Exclusions) {
        this.quantity = quantity;
        this.mealType = mealType;
        this.allergies = allergies;
        this.Exclusions = Exclusions;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getExclusions() {
        return Exclusions;
    }

    public void setExclutions(String Exclusions) {
        this.Exclusions = Exclusions;
    }
    
    public Object[] returnObj(){
        return new Object[]{getQuantity(),getMealType(),getAllergies(),getExclusions()};
    }
    
    
}
