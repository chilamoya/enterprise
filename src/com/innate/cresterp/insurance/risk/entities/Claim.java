/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.insurance.risk.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
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
public class Claim implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private Risk claimPolicy;
    
    private String claimant;
    
    @ManyToOne
    private SystemUser handler ;
    
    @ManyToOne
    private ClaimConfiguration claimType;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date registrationDate ;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datesubmittedNotification;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateOfLoss ;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date followUpDate ;
    
    @ManyToOne
    private ClaimConfiguration claimStatus ;
    
    @Column(length=2000)
    private String remarks ;
    
    private Double grossClaim ;
    
    private Double excess ;
    
    private Double betterment ;
    
    private Double netClaim ;
    
    @ManyToOne
    private ClaimConfiguration premiumStatus ;
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Risk getClaimPolicy() {
        return claimPolicy;
    }

    public void setClaimPolicy(Risk claimPolicy) {
        this.claimPolicy = claimPolicy;
    }

    public String getClaimant() {
        return claimant;
    }

    public void setClaimant(String claimant) {
        this.claimant = claimant;
    }

    public SystemUser getHandler() {
        return handler;
    }

    public void setHandler(SystemUser handler) {
        this.handler = handler;
    }

    public ClaimConfiguration getClaimType() {
        return claimType;
    }

    public void setClaimType(ClaimConfiguration claimType) {
        this.claimType = claimType;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getDatesubmittedNotification() {
        return datesubmittedNotification;
    }

    public void setDatesubmittedNotification(Date datesubmittedNotification) {
        this.datesubmittedNotification = datesubmittedNotification;
    }

    public Date getDateOfLoss() {
        return dateOfLoss;
    }

    public void setDateOfLoss(Date dateOfLoss) {
        this.dateOfLoss = dateOfLoss;
    }

    public Date getFollowUpDate() {
        return followUpDate;
    }

    public void setFollowUpDate(Date followUpDate) {
        this.followUpDate = followUpDate;
    }

    public ClaimConfiguration getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(ClaimConfiguration claimStatus) {
        this.claimStatus = claimStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Double getGrossClaim() {
        return grossClaim;
    }

    public void setGrossClaim(Double grossClaim) {
        this.grossClaim = grossClaim;
    }

    public Double getExcess() {
        return excess;
    }

    public void setExcess(Double excess) {
        this.excess = excess;
    }

    public Double getBetterment() {
        return betterment;
    }

    public void setBetterment(Double betterment) {
        this.betterment = betterment;
    }

    public Double getNetClaim() {
        return netClaim;
    }

    public void setNetClaim(Double netClaim) {
        this.netClaim = netClaim;
    }

    public ClaimConfiguration getPremiumStatus() {
        return premiumStatus;
    }

    public void setPremiumStatus(ClaimConfiguration premiumStatus) {
        this.premiumStatus = premiumStatus;
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
        if (!(object instanceof Claim)) {
            return false;
        }
        Claim other = (Claim) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.risk.Claim[ id=" + id + " ]";
    }
    
}
