/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.accounting.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author user
 */
@Entity
public class AccountProcessTransaction implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    AccountingProcessDefinition accountProcessDefinition;
    @OneToMany(mappedBy = "accountProcessTransaction")
   List<AccountTransactionDefinition> transactionsDefinition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountingProcessDefinition getAccountProcessDefinition() {
        return accountProcessDefinition;
    }

    public void setAccountProcessDefinition(AccountingProcessDefinition accountProcessDefinition) {
        this.accountProcessDefinition = accountProcessDefinition;
    }

    public List<AccountTransactionDefinition> getTransactionsDefinition() {
        return transactionsDefinition;
    }

    public void setTransactionsDefinition(List<AccountTransactionDefinition> transactionsDefinition) {
        this.transactionsDefinition = transactionsDefinition;
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
        if (!(object instanceof AccountProcessTransaction)) {
            return false;
        }
        AccountProcessTransaction other = (AccountProcessTransaction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.innate.cresterp.accounting.entities.AccountProcessTransaction[ id=" + id + " ]";
    }
    
}
