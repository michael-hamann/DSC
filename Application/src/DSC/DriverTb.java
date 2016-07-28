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
@Table(name = "driver_tb", catalog = "doorstepchef", schema = "")
@NamedQueries({
    @NamedQuery(name = "DriverTb.findAll", query = "SELECT d FROM DriverTb d"),
    @NamedQuery(name = "DriverTb.findByDriverID", query = "SELECT d FROM DriverTb d WHERE d.driverID = :driverID"),
    @NamedQuery(name = "DriverTb.findByDriverName", query = "SELECT d FROM DriverTb d WHERE d.driverName = :driverName"),
    @NamedQuery(name = "DriverTb.findByDriverSurname", query = "SELECT d FROM DriverTb d WHERE d.driverSurname = :driverSurname"),
    @NamedQuery(name = "DriverTb.findByContactNumber", query = "SELECT d FROM DriverTb d WHERE d.contactNumber = :contactNumber"),
    @NamedQuery(name = "DriverTb.findByAddress", query = "SELECT d FROM DriverTb d WHERE d.address = :address"),
    @NamedQuery(name = "DriverTb.findByVehicleReg", query = "SELECT d FROM DriverTb d WHERE d.vehicleReg = :vehicleReg")})
public class DriverTb implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "DriverID")
    private Short driverID;
    @Basic(optional = false)
    @Column(name = "DriverName")
    private String driverName;
    @Basic(optional = false)
    @Column(name = "DriverSurname")
    private String driverSurname;
    @Basic(optional = false)
    @Column(name = "ContactNumber")
    private String contactNumber;
    @Basic(optional = false)
    @Column(name = "Address")
    private String address;
    @Basic(optional = false)
    @Column(name = "VehicleReg")
    private String vehicleReg;

    public DriverTb() {
    }

    public DriverTb(Short driverID) {
        this.driverID = driverID;
    }

    public DriverTb(Short driverID, String driverName, String driverSurname, String contactNumber, String address, String vehicleReg) {
        this.driverID = driverID;
        this.driverName = driverName;
        this.driverSurname = driverSurname;
        this.contactNumber = contactNumber;
        this.address = address;
        this.vehicleReg = vehicleReg;
    }

    public Short getDriverID() {
        return driverID;
    }

    public void setDriverID(Short driverID) {
        Short oldDriverID = this.driverID;
        this.driverID = driverID;
        changeSupport.firePropertyChange("driverID", oldDriverID, driverID);
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        String oldDriverName = this.driverName;
        this.driverName = driverName;
        changeSupport.firePropertyChange("driverName", oldDriverName, driverName);
    }

    public String getDriverSurame() {
        return driverSurname;
    }

    public void setDriverSurame(String driverSurame) {
        String oldDriverSurame = this.driverSurname;
        this.driverSurname = driverSurame;
        changeSupport.firePropertyChange("driverSurame", oldDriverSurame, driverSurame);
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        String oldContactNumber = this.contactNumber;
        this.contactNumber = contactNumber;
        changeSupport.firePropertyChange("contactNumber", oldContactNumber, contactNumber);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        String oldAddress = this.address;
        this.address = address;
        changeSupport.firePropertyChange("address", oldAddress, address);
    }

    public String getVehicleReg() {
        return vehicleReg;
    }

    public void setVehicleReg(String vehicleReg) {
        String oldVehicleReg = this.vehicleReg;
        this.vehicleReg = vehicleReg;
        changeSupport.firePropertyChange("vehicleReg", oldVehicleReg, vehicleReg);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (driverID != null ? driverID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DriverTb)) {
            return false;
        }
        DriverTb other = (DriverTb) object;
        if ((this.driverID == null && other.driverID != null) || (this.driverID != null && !this.driverID.equals(other.driverID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return driverName+" "+driverSurname+" ["+driverID+"]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
