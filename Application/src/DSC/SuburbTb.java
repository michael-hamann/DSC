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
 * @author Amina
 */
@Entity
@Table(name = "suburb_tb", catalog = "doorstepchef", schema = "")
@NamedQueries({
    @NamedQuery(name = "SuburbTb.findAll", query = "SELECT s FROM SuburbTb s"),
    @NamedQuery(name = "SuburbTb.findBySuburb", query = "SELECT s FROM SuburbTb s WHERE s.suburb = :suburb"),
    @NamedQuery(name = "SuburbTb.findByRouteID", query = "SELECT s FROM SuburbTb s WHERE s.routeID = :routeID"),
    @NamedQuery(name = "SuburbTb.findBySuburbID", query = "SELECT s FROM SuburbTb s WHERE s.suburbID = :suburbID")})
public class SuburbTb implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "Suburb")
    private String suburb;
    @Basic(optional = false)
    @Column(name = "Route_ID")
    private short routeID;
    @Id
    @Basic(optional = false)
    @Column(name = "SuburbID")
    private Short suburbID;

    public SuburbTb() {
    }

    public SuburbTb(Short suburbID) {
        this.suburbID = suburbID;
    }

    public SuburbTb(Short suburbID, String suburb, short routeID) {
        this.suburbID = suburbID;
        this.suburb = suburb;
        this.routeID = routeID;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        String oldSuburb = this.suburb;
        this.suburb = suburb;
        changeSupport.firePropertyChange("suburb", oldSuburb, suburb);
    }

    public short getRouteID() {
        return routeID;
    }

    public void setRouteID(short routeID) {
        short oldRouteID = this.routeID;
        this.routeID = routeID;
        changeSupport.firePropertyChange("routeID", oldRouteID, routeID);
    }

    public Short getSuburbID() {
        return suburbID;
    }

    public void setSuburbID(Short suburbID) {
        Short oldSuburbID = this.suburbID;
        this.suburbID = suburbID;
        changeSupport.firePropertyChange("suburbID", oldSuburbID, suburbID);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (suburbID != null ? suburbID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SuburbTb)) {
            return false;
        }
        SuburbTb other = (SuburbTb) object;
        if ((this.suburbID == null && other.suburbID != null) || (this.suburbID != null && !this.suburbID.equals(other.suburbID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DSC.SuburbTb[ suburbID=" + suburbID + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
