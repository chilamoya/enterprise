/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.medical.hospital.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Edmund
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "getBill", query = "SELECT b FROM Biiills b WHERE b.invoiceID = :in"),
    @NamedQuery(name = "getAll", query = "SELECT p FROM Biiills p WHERE p.invoiceID = :in"),
     @NamedQuery(name = "deleteBill", query = "DELETE FROM Biiills p WHERE p.id = :bill")
})
public class Biiills implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String aatribute;
    private double amount;
    //REVERT
//    @ManyToOne
//    private BiillingPoint billingPointId;
//    @ManyToOne
//    private Uusers userId;
    @ManyToOne
    private Invoices invoiceID;
    @ManyToOne
    private Aadmissions admissionId;

    public String getAatribute() {
        return aatribute;
    }

    public void setAatribute(String aatribute) {
        this.aatribute = aatribute;
    }

    public Aadmissions getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(Aadmissions admissionId) {
        this.admissionId = admissionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Invoices getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(Invoices invoiceID) {
        this.invoiceID = invoiceID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Biiills)) {
            return false;
        }
        Biiills other = (Biiills) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Biiills[id=" + id + "]";
    }
}
