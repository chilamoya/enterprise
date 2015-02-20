/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.insurance.risk.entities;

import com.innate.cresterp.medical.hospital.entities.PatientRecord;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
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
 * @author Tafadzwa
 */
@Entity
@NamedQueries(
        {
            @NamedQuery(name="findClientRisks", query="SELECT r FROM Risk r WHERE r.client  = :rc")
        }
        )
public class Risk implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private PatientRecord client;
    
    @ManyToOne
    private PolicyType policyType;
    
    @ManyToOne
    private PolicyPeriod policyPeriod;
    
    private BigDecimal numberOfRisks;
    
    private BigDecimal totalSumInsured;
    
    @ManyToOne
    private RiskPremium riskPremium;
    
    @Column(length=2000)
    private String description;
    
    private String coverNoteNumber ;

    
    @ManyToOne
    private Insurer insurer;
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PatientRecord getClient() {
        return client;
    }

    public void setClient(PatientRecord client) {
        this.client = client;
    }

    public BigDecimal getNumberOfRisks() {
        return numberOfRisks;
    }

    public void setNumberOfRisks(BigDecimal numberOfRisks) {
        this.numberOfRisks = numberOfRisks;
    }

    public PolicyPeriod getPolicyPeriod() {
        return policyPeriod;
    }

    public void setPolicyPeriod(PolicyPeriod policyPeriod) {
        this.policyPeriod = policyPeriod;
    }

    public PolicyType getPolicyType() {
        return policyType;
    }

    public void setPolicyType(PolicyType policyType) {
        this.policyType = policyType;
    }

    public RiskPremium getRiskPremium() {
        return riskPremium;
    }

    public void setRiskPremium(RiskPremium riskPremium) {
        this.riskPremium = riskPremium;
    }

    public BigDecimal getTotalSumInsured() {
        return totalSumInsured;
    }

    public void setTotalSumInsured(BigDecimal totalSumInsured) {
        this.totalSumInsured = totalSumInsured;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverNoteNumber() {
        return coverNoteNumber;
    }

    public void setCoverNoteNumber(String coverNoteNumber) {
        this.coverNoteNumber = coverNoteNumber;
    }

    public Insurer getInsurer() {
        return insurer;
    }

    public void setInsurer(Insurer insurer) {
        this.insurer = insurer;
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
        if (!(object instanceof Risk)) {
            return false;
        }
        Risk other = (Risk) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "debtorsdemopersistance.Risk[ id=" + id + " ]";
    }
    
}
