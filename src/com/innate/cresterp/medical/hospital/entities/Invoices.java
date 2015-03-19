/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.medical.hospital.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author Edmund
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "getInvoiceNumber", query = "SELECT p FROM Invoices p WHERE p.patientRecordId = :pr"),
    @NamedQuery(name = "currentTotal", query = "SELECT p FROM Invoices p where p.id = :iid"),
    @NamedQuery(name = "getInvoice", query = "SELECT p FROM Invoices p where p.patientRecordId = :prid"),
    @NamedQuery(name="getInvoices", query="SELECT i FROM Invoices i WHERE i.billingPointId = :bp AND i.dateCreated = :dt AND i.authorised = :au"),
    @NamedQuery(name="getInvoicesDuration", query="SELECT i FROM Invoices i WHERE i.billingPointId = :bp AND i.dateCreated BETWEEN :sdt and :edt AND i.authorised = :au"),
    @NamedQuery(name="getAllInvoicesOnBillingPointDuration", query="SELECT i FROM Invoices i WHERE i.billingPointId = :bp AND i.dateCreated BETWEEN :sdt and :edt"),
    @NamedQuery(name="getAllInvoicesForToday", query="SELECT i FROM Invoices i WHERE i.billingPointId = :bp AND i.dateCreated = :sdt"),
    @NamedQuery(name="getAllInvoicesForTodayByUser", query="SELECT i FROM Invoices i WHERE i.billingPointId = :bp AND i.userId = :user AND i.dateCreated = :sdt AND i.authorised = :au"),
    @NamedQuery(name="getAllInvoicesForDurationByUser", query="SELECT i FROM Invoices i WHERE i.billingPointId = :bp AND i.userId = :user AND i.dateCreated BETWEEN :sdt and :edt AND i.authorised = :au"),
    @NamedQuery(name="getAllInvoicesOnBillingPointByUser", query="SELECT i FROM Invoices i WHERE i.billingPointId = :bp AND i.userId = :user AND i.dateCreated BETWEEN :sdt and :edt"),
    @NamedQuery(name="getAllInvoicesForTodayOnBillingPointByUser", query="SELECT i FROM Invoices i WHERE i.billingPointId = :bp AND i.userId = :user AND i.dateCreated = :sdt")
})
public class Invoices implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private BiillingPoint billingPointId;
    @ManyToOne
    private Uusers userId;
    @ManyToOne
    private PatientRecord patientRecordId;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateCreated;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateAuthorised;
    @ManyToOne
    private Uusers authorisedBy;
    private int settled;
    private int authorised;
    @OneToMany(mappedBy = "invoiceID")
    private List<Biiills> biiillsList;
    private boolean isCashPayment ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BiillingPoint getBillingPointId() {
        return billingPointId;
    }

    public void setBillingPointId(BiillingPoint billingPointId) {
        this.billingPointId = billingPointId;
    }

    public PatientRecord getPatientRecordId() {
        return patientRecordId;
    }

    public void setPatientRecordId(PatientRecord patientRecordId) {
        this.patientRecordId = patientRecordId;
    }

    public Uusers getUserId() {
        return userId;
    }

    public void setUserId(Uusers userId) {
        this.userId = userId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getSettled() {
        return settled;
    }

    public void setSettled(int settled) {
        this.settled = settled;
    }

    public List<Biiills> getBiiillsList() {
        return biiillsList;
    }

    public void setBiiillsList(List<Biiills> biiillsList) {
        this.biiillsList = biiillsList;
    }

    public int getAuthorised() {
        return authorised;
    }

    public void setAuthorised(int authorised) {
        this.authorised = authorised;
    }

    public Uusers getAuthorisedBy() {
        return authorisedBy;
    }

    public void setAuthorisedBy(Uusers authorisedBy) {
        this.authorisedBy = authorisedBy;
    }

    public Date getDateAuthorised() {
        return dateAuthorised;
    }

    public void setDateAuthorised(Date dateAuthorised) {
        this.dateAuthorised = dateAuthorised;
    }

    public boolean isIsCashPayment() {
        return isCashPayment;
    }

    public void setIsCashPayment(boolean isCashPayment) {
        this.isCashPayment = isCashPayment;
    }
    
   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Invoices)) {
            return false;
        }
        Invoices other = (Invoices) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Invoices[id=" + id + "]";
    }
}
