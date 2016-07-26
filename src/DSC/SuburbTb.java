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
@Table(name = "suburb_tb", catalog = "doorstepchef", schema = "")
@NamedQueries({
    @NamedQuery(name = "SuburbTb.findAll", query = "SELECT s FROM SuburbTb s"),
    @NamedQuery(name = "SuburbTb.findBySuburb", query = "SELECT s FROM SuburbTb s WHERE s.suburb = :suburb"),
    @NamedQuery(name = "SuburbTb.findByPostalCode", query = "SELECT s FROM SuburbTb s WHERE s.postalCode = :postalCode"),
    @NamedQuery(name = "SuburbTb.findBySubLocation", query = "SELECT s FROM SuburbTb s WHERE s.subLocation = :subLocation"),
    @NamedQuery(name = "SuburbTb.findByRouteIDAfternoon", query = "SELECT s FROM SuburbTb s WHERE s.routeIDAfternoon = :routeIDAfternoon"),
    @NamedQuery(name = "SuburbTb.findByRouteIDLateAfternoon", query = "SELECT s FROM SuburbTb s WHERE s.routeIDLateAfternoon = :routeIDLateAfternoon"),
    @NamedQuery(name = "SuburbTb.findByRouteIDEvening", query = "SELECT s FROM SuburbTb s WHERE s.routeIDEvening = :routeIDEvening")})
public class SuburbTb implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Suburb")
    private String suburb;
    @Basic(optional = false)
    @Column(name = "PostalCode")
    private String postalCode;
    @Basic(optional = false)
    @Column(name = "Sub_Location")
    private String subLocation;
    @Basic(optional = false)
    @Column(name = "RouteID_Afternoon")
    private short routeIDAfternoon;
    @Basic(optional = false)
    @Column(name = "RouteID_LateAfternoon")
    private short routeIDLateAfternoon;
    @Basic(optional = false)
    @Column(name = "RouteID_Evening")
    private short routeIDEvening;

    public SuburbTb() {
    }

    public SuburbTb(String suburb) {
        this.suburb = suburb;
    }

    public SuburbTb(String suburb, String postalCode, String subLocation, short routeIDAfternoon, short routeIDLateAfternoon, short routeIDEvening) {
        this.suburb = suburb;
        this.postalCode = postalCode;
        this.subLocation = subLocation;
        this.routeIDAfternoon = routeIDAfternoon;
        this.routeIDLateAfternoon = routeIDLateAfternoon;
        this.routeIDEvening = routeIDEvening;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        String oldSuburb = this.suburb;
        this.suburb = suburb;
        changeSupport.firePropertyChange("suburb", oldSuburb, suburb);
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        String oldPostalCode = this.postalCode;
        this.postalCode = postalCode;
        changeSupport.firePropertyChange("postalCode", oldPostalCode, postalCode);
    }

    public String getSubLocation() {
        return subLocation;
    }

    public void setSubLocation(String subLocation) {
        String oldSubLocation = this.subLocation;
        this.subLocation = subLocation;
        changeSupport.firePropertyChange("subLocation", oldSubLocation, subLocation);
    }

    public short getRouteIDAfternoon() {
        return routeIDAfternoon;
    }

    public void setRouteIDAfternoon(short routeIDAfternoon) {
        short oldRouteIDAfternoon = this.routeIDAfternoon;
        this.routeIDAfternoon = routeIDAfternoon;
        changeSupport.firePropertyChange("routeIDAfternoon", oldRouteIDAfternoon, routeIDAfternoon);
    }

    public short getRouteIDLateAfternoon() {
        return routeIDLateAfternoon;
    }

    public void setRouteIDLateAfternoon(short routeIDLateAfternoon) {
        short oldRouteIDLateAfternoon = this.routeIDLateAfternoon;
        this.routeIDLateAfternoon = routeIDLateAfternoon;
        changeSupport.firePropertyChange("routeIDLateAfternoon", oldRouteIDLateAfternoon, routeIDLateAfternoon);
    }

    public short getRouteIDEvening() {
        return routeIDEvening;
    }

    public void setRouteIDEvening(short routeIDEvening) {
        short oldRouteIDEvening = this.routeIDEvening;
        this.routeIDEvening = routeIDEvening;
        changeSupport.firePropertyChange("routeIDEvening", oldRouteIDEvening, routeIDEvening);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (suburb != null ? suburb.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SuburbTb)) {
            return false;
        }
        SuburbTb other = (SuburbTb) object;
        if ((this.suburb == null && other.suburb != null) || (this.suburb != null && !this.suburb.equals(other.suburb))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DSC.SuburbTb[ suburb=" + suburb + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
