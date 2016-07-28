/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Aliens_Michael
 */
@Entity
@Table(name = "meal_tb", catalog = "doorstepchef", schema = "")
@NamedQueries({
    @NamedQuery(name = "MealTb.findAll", query = "SELECT m FROM MealTb m"),
    @NamedQuery(name = "MealTb.findByMealID", query = "SELECT m FROM MealTb m WHERE m.mealID = :mealID"),
    @NamedQuery(name = "MealTb.findByMealType", query = "SELECT m FROM MealTb m WHERE m.mealType = :mealType"),
    @NamedQuery(name = "MealTb.findByAllergy", query = "SELECT m FROM MealTb m WHERE m.allergy = :allergy"),
    @NamedQuery(name = "MealTb.findByExclusions", query = "SELECT m FROM MealTb m WHERE m.exclusions = :exclusions"),
    @NamedQuery(name = "MealTb.findByQuanity", query = "SELECT m FROM MealTb m WHERE m.quanity = :quanity"),
    @NamedQuery(name = "MealTb.findByOrderID", query = "SELECT m FROM MealTb m WHERE m.orderID = :orderID")})
public class MealTb implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "MealID")
    private Short mealID;
    @Basic(optional = false)
    @Column(name = "MealType")
    private String mealType;
    @Basic(optional = false)
    @Column(name = "Allergy")
    private String allergy;
    @Basic(optional = false)
    @Column(name = "Exclusions")
    private String exclusions;
    @Basic(optional = false)
    @Column(name = "Quanity")
    private short quanity;
    @Basic(optional = false)
    @Column(name = "OrderID")
    private short orderID;

    public MealTb() {
    }

    public MealTb(Short mealID) {
        this.mealID = mealID;
    }

    public MealTb(Short mealID, String mealType, String allergy, String exclusions, short quanity, short orderID) {
        this.mealID = mealID;
        this.mealType = mealType;
        this.allergy = allergy;
        this.exclusions = exclusions;
        this.quanity = quanity;
        this.orderID = orderID;
    }

    public Short getMealID() {
        return mealID;
    }

    public void setMealID(Short mealID) {
        Short oldMealID = this.mealID;
        this.mealID = mealID;
        changeSupport.firePropertyChange("mealID", oldMealID, mealID);
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        String oldMealType = this.mealType;
        this.mealType = mealType;
        changeSupport.firePropertyChange("mealType", oldMealType, mealType);
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        String oldAllergy = this.allergy;
        this.allergy = allergy;
        changeSupport.firePropertyChange("allergy", oldAllergy, allergy);
    }

    public String getExclusions() {
        return exclusions;
    }

    public void setExclusions(String exclusions) {
        String oldExclusions = this.exclusions;
        this.exclusions = exclusions;
        changeSupport.firePropertyChange("exclusions", oldExclusions, exclusions);
    }

    public short getQuanity() {
        return quanity;
    }

    public void setQuanity(short quanity) {
        short oldQuanity = this.quanity;
        this.quanity = quanity;
        changeSupport.firePropertyChange("quanity", oldQuanity, quanity);
    }

    public short getOrderID() {
        return orderID;
    }

    public void setOrderID(short orderID) {
        short oldOrderID = this.orderID;
        this.orderID = orderID;
        changeSupport.firePropertyChange("orderID", oldOrderID, orderID);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mealID != null ? mealID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MealTb)) {
            return false;
        }
        MealTb other = (MealTb) object;
        if ((this.mealID == null && other.mealID != null) || (this.mealID != null && !this.mealID.equals(other.mealID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DSC.MealTb[ mealID=" + mealID + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
