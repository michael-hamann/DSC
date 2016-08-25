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
public class Meal {
    private int quantity;
    private String mealType;
    private String allergies;
    private String Exclutions;

    public Meal(int quantity, String mealType, String allergies, String Exclutions) {
        this.quantity = quantity;
        this.mealType = mealType;
        this.allergies = allergies;
        this.Exclutions = Exclutions;
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

    public String getExclutions() {
        return Exclutions;
    }

    public void setExclutions(String Exclutions) {
        this.Exclutions = Exclutions;
    }
    
    public Object[] returnObj(){
        return new Object[]{getQuantity(),getMealType(),getAllergies(),getExclutions()};
    }
    
    
}
