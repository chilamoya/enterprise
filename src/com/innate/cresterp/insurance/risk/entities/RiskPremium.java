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
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Tafadzwa
 */
@Entity
public class RiskPremium implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Double annualPremium; 
    
    private Double premiumRate ;
    
    private Double basicPremium ;
    
    private Double levies;
    
    private Double stampDuty;
    
    private Double brokerageRate;
    
    private Double brokerageAmount;
    
    private Double premiumNet;
    

    
    private String transactionType;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBasicPremium() {
        return basicPremium;
    }

    public void setBasicPremium(Double basicPremium) {
        this.basicPremium = basicPremium;
    }

    public Double getBrokerageAmount() {
        return brokerageAmount;
    }

    public void setBrokerageAmount(Double brokerageAmount) {
        this.brokerageAmount = brokerageAmount;
    }

    public Double getBrokerageRate() {
        return brokerageRate;
    }

    public void setBrokerageRate(Double brokerageRate) {
        this.brokerageRate = brokerageRate;
    }

    public Double getLevies() {
        return levies;
    }

    public void setLevies(Double levies) {
        this.levies = levies;
    }

    public Double getPremiumNet() {
        return premiumNet;
    }

    public void setPremiumNet(Double premiumNet) {
        this.premiumNet = premiumNet;
    }

    public Double getPremiumRate() {
        return premiumRate;
    }

    public void setPremiumRate(Double premiumRate) {
        this.premiumRate = premiumRate;
    }

    public Double getStampDuty() {
        return stampDuty;
    }

    public void setStampDuty(Double stampDuty) {
        this.stampDuty = stampDuty;
    }

    
  
    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Double getAnnualPremium() {
        return annualPremium;
    }

    public void setAnnualPremium(Double annualPremium) {
        this.annualPremium = annualPremium;
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
        if (!(object instanceof RiskPremium)) {
            return false;
        }
        RiskPremium other = (RiskPremium) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.risk.RiskPremium[ id=" + id + " ]";
    }
    
}
