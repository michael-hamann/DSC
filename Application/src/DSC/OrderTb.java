/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Aliens_Ross
 */
@Entity
@Table(name = "order_tb", catalog = "doorstepchef", schema = "")
@NamedQueries({
    @NamedQuery(name = "OrderTb.findAll", query = "SELECT o FROM OrderTb o"),
    @NamedQuery(name = "OrderTb.findByOrderID", query = "SELECT o FROM OrderTb o WHERE o.orderID = :orderID"),
    @NamedQuery(name = "OrderTb.findByFamilySize", query = "SELECT o FROM OrderTb o WHERE o.familySize = :familySize"),
    @NamedQuery(name = "OrderTb.findByStartingDate", query = "SELECT o FROM OrderTb o WHERE o.startingDate = :startingDate"),
    @NamedQuery(name = "OrderTb.findByRouteID", query = "SELECT o FROM OrderTb o WHERE o.routeID = :routeID"),
    @NamedQuery(name = "OrderTb.findByDuration", query = "SELECT o FROM OrderTb o WHERE o.duration = :duration"),
    @NamedQuery(name = "OrderTb.findByClientID", query = "SELECT o FROM OrderTb o WHERE o.clientID = :clientID")})
public class OrderTb implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "OrderID")
    private Short orderID;
    @Basic(optional = false)
    @Column(name = "FamilySize")
    private short familySize;
    @Basic(optional = false)
    @Column(name = "StartingDate")
    @Temporal(TemporalType.DATE)
    private Date startingDate;
    @Basic(optional = false)
    @Column(name = "RouteID")
    private short routeID;
    @Basic(optional = false)
    @Column(name = "Duration")
    private String duration;
    @Basic(optional = false)
    @Column(name = "Client_ID")
    private short clientID;

    public OrderTb() {
    }

    public OrderTb(Short orderID) {
        this.orderID = orderID;
    }

    public OrderTb(Short orderID, short familySize, Date startingDate, short routeID, String duration, short clientID) {
        this.orderID = orderID;
        this.familySize = familySize;
        this.startingDate = startingDate;
        this.routeID = routeID;
        this.duration = duration;
        this.clientID = clientID;
    }

    public Short getOrderID() {
        return orderID;
    }

    public void setOrderID(Short orderID) {
        Short oldOrderID = this.orderID;
        this.orderID = orderID;
        changeSupport.firePropertyChange("orderID", oldOrderID, orderID);
    }

    public short getFamilySize() {
        return familySize;
    }

    public void setFamilySize(short familySize) {
        short oldFamilySize = this.familySize;
        this.familySize = familySize;
        changeSupport.firePropertyChange("familySize", oldFamilySize, familySize);
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        Date oldStartingDate = this.startingDate;
        this.startingDate = startingDate;
        changeSupport.firePropertyChange("startingDate", oldStartingDate, startingDate);
    }

    public short getRouteID() {
        return routeID;
    }

    public void setRouteID(short routeID) {
        short oldRouteID = this.routeID;
        this.routeID = routeID;
        changeSupport.firePropertyChange("routeID", oldRouteID, routeID);
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        String oldDuration = this.duration;
        this.duration = duration;
        changeSupport.firePropertyChange("duration", oldDuration, duration);
    }

    public short getClientID() {
        return clientID;
    }

    public void setClientID(short clientID) {
        short oldClientID = this.clientID;
        this.clientID = clientID;
        changeSupport.firePropertyChange("clientID", oldClientID, clientID);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderID != null ? orderID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderTb)) {
            return false;
        }
        OrderTb other = (OrderTb) object;
        if ((this.orderID == null && other.orderID != null) || (this.orderID != null && !this.orderID.equals(other.orderID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DSC.OrderTb[ orderID=" + orderID + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
