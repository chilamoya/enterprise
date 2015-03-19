/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.insurance.risk.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author Tafadzwa
 */
@Entity
public class Renewal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Risk risk;
    
    private PolicyPeriod period;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createdDated;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PolicyPeriod getPeriod() {
        return period;
    }

    public void setPeriod(PolicyPeriod period) {
        this.period = period;
    }

    public Risk getRisk() {
        return risk;
    }

    public void setRisk(Risk risk) {
        this.risk = risk;
    }

    public Date getCreatedDated() {
        return createdDated;
    }

    public void setCreatedDated(Date createdDated) {
        this.createdDated = createdDated;
    }

       @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateCreated ;

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = new Date ();
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
        if (!(object instanceof Renewal)) {
            return false;
        }
        Renewal other = (Renewal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.risk.Renewal[ id=" + id + " ]";
    }
    
}
