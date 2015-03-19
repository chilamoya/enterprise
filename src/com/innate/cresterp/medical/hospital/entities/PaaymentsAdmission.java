/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.medical.hospital.entities;

import java.io.Serializable;
//import java.sql.Date;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;


/**
 *
 * @author Edmund
 */
@Entity
@NamedQueries({@NamedQuery(name="getAmountPaid", query="SELECT p FROM PaaymentsAdmission p where p.patientRecordId = :pid"),
@NamedQuery(name="totalReceipted", query="SELECT p FROM PaaymentsAdmission p where p.billingPointId = :bpid AND p.processedDate = :dp"),
@NamedQuery(name="totalReceiptedByBillingPoint", query="SELECT p FROM PaaymentsAdmission p where p.billingPointId = :bpid")
})
public class PaaymentsAdmission implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private BiillingPoint billingPointId;
    @ManyToOne
    private PatientRecord patientRecordId;
    private Double amount;
    private String datePaid;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date processedDate;

    public String getDatee() {
        return datePaid;
    }

    public void setDatee(String datee) {
        this.datePaid = datee;
    }
 


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(Date processedDate) {
        this.processedDate = processedDate;
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
        if (!(object instanceof PaaymentsAdmission)) {
            return false;
        }
        PaaymentsAdmission other = (PaaymentsAdmission) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.PaaymentsAdmission[id=" + id + "]";
    }

}
