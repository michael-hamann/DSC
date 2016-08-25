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
@Table(name = "client_tb", catalog = "doorstepchef", schema = "")
@NamedQueries({
    @NamedQuery(name = "ClientTb.findAll", query = "SELECT c FROM ClientTb c"),
    @NamedQuery(name = "ClientTb.findByClientID", query = "SELECT c FROM ClientTb c WHERE c.clientID = :clientID"),
    @NamedQuery(name = "ClientTb.findByName", query = "SELECT c FROM ClientTb c WHERE c.name = :name"),
    @NamedQuery(name = "ClientTb.findBySurname", query = "SELECT c FROM ClientTb c WHERE c.surname = :surname"),
    @NamedQuery(name = "ClientTb.findByAddress", query = "SELECT c FROM ClientTb c WHERE c.address = :address"),
    @NamedQuery(name = "ClientTb.findByAdditionalInfo", query = "SELECT c FROM ClientTb c WHERE c.additionalInfo = :additionalInfo"),
    @NamedQuery(name = "ClientTb.findByContactNumber", query = "SELECT c FROM ClientTb c WHERE c.contactNumber = :contactNumber"),
    @NamedQuery(name = "ClientTb.findByAlternativeNumber", query = "SELECT c FROM ClientTb c WHERE c.alternativeNumber = :alternativeNumber"),
    @NamedQuery(name = "ClientTb.findByEmail", query = "SELECT c FROM ClientTb c WHERE c.email = :email"),
    @NamedQuery(name = "ClientTb.findBySuburbID", query = "SELECT c FROM ClientTb c WHERE c.suburbID = :suburbID")})
public class ClientTb implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ClientID")
    private Short clientID;
    @Column(name = "Name")
    private String name;
    @Column(name = "Surname")
    private String surname;
    @Basic(optional = false)
    @Column(name = "Address")
    private String address;
    @Basic(optional = false)
    @Column(name = "AdditionalInfo")
    private String additionalInfo;
    @Basic(optional = false)
    @Column(name = "ContactNumber")
    private String contactNumber;
    @Basic(optional = false)
    @Column(name = "AlternativeNumber")
    private String alternativeNumber;
    @Basic(optional = false)
    @Column(name = "Email")
    private String email;
    @Basic(optional = false)
    @Column(name = "SuburbID")
    private Short suburbID;

    public ClientTb() {
    }

    public ClientTb(Short clientID) {
        this.clientID = clientID;
    }

    public ClientTb(Short clientID, String address, String additionalInfo, String contactNumber, String alternativeNumber, String email) {
        this.clientID = clientID;
        this.address = address;
        this.additionalInfo = additionalInfo;
        this.contactNumber = contactNumber;
        this.alternativeNumber = alternativeNumber;
        this.email = email;
        
    }

    public Short getClientID() {
        return clientID;
    }

    public void setClientID(Short clientID) {
        Short oldClientID = this.clientID;
        this.clientID = clientID;
        changeSupport.firePropertyChange("clientID", oldClientID, clientID);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        changeSupport.firePropertyChange("name", oldName, name);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        String oldSurname = this.surname;
        this.surname = surname;
        changeSupport.firePropertyChange("surname", oldSurname, surname);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        String oldAddress = this.address;
        this.address = address;
        changeSupport.firePropertyChange("address", oldAddress, address);
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        String oldAdditionalInfo = this.additionalInfo;
        this.additionalInfo = additionalInfo;
        changeSupport.firePropertyChange("additionalInfo", oldAdditionalInfo, additionalInfo);
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        String oldContactNumber = this.contactNumber;
        this.contactNumber = contactNumber;
        changeSupport.firePropertyChange("contactNumber", oldContactNumber, contactNumber);
    }

    public String getAlternativeNumber() {
        return alternativeNumber;
    }

    public void setAlternativeNumber(String alternativeNumber) {
        String oldAlternativeNumber = this.alternativeNumber;
        this.alternativeNumber = alternativeNumber;
        changeSupport.firePropertyChange("alternativeNumber", oldAlternativeNumber, alternativeNumber);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        String oldEmail = this.email;
        this.email = email;
        changeSupport.firePropertyChange("email", oldEmail, email);
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
        hash += (clientID != null ? clientID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClientTb)) {
            return false;
        }
        ClientTb other = (ClientTb) object;
        if ((this.clientID == null && other.clientID != null) || (this.clientID != null && !this.clientID.equals(other.clientID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name +" "+ surname + "["+clientID+"]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
