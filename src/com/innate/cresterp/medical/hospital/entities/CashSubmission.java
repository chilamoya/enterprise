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
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;

/**
 *
 * @author Edmund
 */
@Entity
@NamedQueries({@NamedQuery(name="submitted", query="SELECT p FROM CashSubmission p where p.BillingPointId = :bpid AND p.datee = :d")
})
public class CashSubmission implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

   private Double amount;
//   @Temporal (TemporalType.DATE)
//   private Date daate;
   private String datee;
   @ManyToOne
   BiillingPoint BillingPointId;

    public String getDate() {
        return datee;
    }

    public void setDate(String datee) {
        this.datee = datee;
    }



    public BiillingPoint getBillingPointId() {
        return BillingPointId;
    }

    public void setBillingPointId(BiillingPoint BillingPointId) {
        this.BillingPointId = BillingPointId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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
        if (!(object instanceof CashSubmission)) {
            return false;
        }
        CashSubmission other = (CashSubmission) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.CashSubmission[id=" + id + "]";
    }

}
