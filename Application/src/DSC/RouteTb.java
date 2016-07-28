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
@Table(name = "route_tb", catalog = "doorstepchef", schema = "")
@NamedQueries({
    @NamedQuery(name = "RouteTb.findAll", query = "SELECT r FROM RouteTb r"),
    @NamedQuery(name = "RouteTb.findByRouteID", query = "SELECT r FROM RouteTb r WHERE r.routeID = :routeID"),
    @NamedQuery(name = "RouteTb.findByTimeFrame", query = "SELECT r FROM RouteTb r WHERE r.timeFrame = :timeFrame"),
    @NamedQuery(name = "RouteTb.findByDriverID", query = "SELECT r FROM RouteTb r WHERE r.driverID = :driverID")})
public class RouteTb implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "RouteID")
    private Short routeID;
    @Basic(optional = false)
    @Column(name = "TimeFrame")
    private String timeFrame;
    @Basic(optional = false)
    @Column(name = "DriverID")
    private short driverID;

    public RouteTb() {
    }

    public RouteTb(Short routeID) {
        this.routeID = routeID;
    }

    public RouteTb(Short routeID, String timeFrame, short driverID) {
        this.routeID = routeID;
        this.timeFrame = timeFrame;
        this.driverID = driverID;
    }

    public Short getRouteID() {
        return routeID;
    }

    public void setRouteID(Short routeID) {
        Short oldRouteID = this.routeID;
        this.routeID = routeID;
        changeSupport.firePropertyChange("routeID", oldRouteID, routeID);
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(String timeFrame) {
        String oldTimeFrame = this.timeFrame;
        this.timeFrame = timeFrame;
        changeSupport.firePropertyChange("timeFrame", oldTimeFrame, timeFrame);
    }

    public short getDriverID() {
        return driverID;
    }

    public void setDriverID(short driverID) {
        short oldDriverID = this.driverID;
        this.driverID = driverID;
        changeSupport.firePropertyChange("driverID", oldDriverID, driverID);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (routeID != null ? routeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RouteTb)) {
            return false;
        }
        RouteTb other = (RouteTb) object;
        if ((this.routeID == null && other.routeID != null) || (this.routeID != null && !this.routeID.equals(other.routeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DSC.RouteTb[ routeID=" + routeID + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
