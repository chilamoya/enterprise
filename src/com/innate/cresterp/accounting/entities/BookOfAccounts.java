/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.accounting.entities;

import com.innate.cresterp.insurance.risk.entities.SystemUser;
import java.io.Serializable;
import java.math.BigDecimal;
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
 *
 */
@Entity
public class BookOfAccounts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private AccountProcessTransaction transaction;
    private BigDecimal balance;
    @ManyToOne
    private SystemUser user;
    private String description;
    @Column(length = 1000)
    private String rawData;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createDate;
    private String erpModule;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date transactionDate;

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getErpModule() {
        return erpModule;
    }

    public void setErpModule(String erpModule) {
        this.erpModule = erpModule;
    }

    public AccountProcessTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(AccountProcessTransaction transaction) {
        this.transaction = transaction;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public SystemUser getUser() {
        return user;
    }

    public void setUser(SystemUser user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
        if (!(object instanceof BookOfAccounts)) {
            return false;
        }
        BookOfAccounts other = (BookOfAccounts) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.innate.cresterp.accounting.entities.Ledger[ id=" + id + " ]";
    }

}
