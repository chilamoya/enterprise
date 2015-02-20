/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.medical.hospital.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Edmund
 */
@Entity
public class Waard implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Deepartment departmentId;
    private String name;
    private String headedBy;
    private String remarks;
    @OneToMany (mappedBy="wardId")
    private List<Roooms> rooomCollection;

    public List<Roooms> getRooomCollection() {
        return rooomCollection;
    }

    public void setRooomCollection(List<Roooms> rooomCollection) {
        this.rooomCollection = rooomCollection;
    }


    public String getAccountsClerk() {
        return headedBy;
    }

    public Deepartment getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Deepartment departmentId) {
        this.departmentId = departmentId;
    }

    public String getHeadedBy() {
        return headedBy;
    }

    public void setHeadedBy(String headedBy) {
        this.headedBy = headedBy;
    }

    public void setAccountsClerk(String accountsClerk) {
        this.headedBy = accountsClerk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
        if (!(object instanceof Waard)) {
            return false;
        }
        Waard other = (Waard) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Ward[id=" + id + "]";
    }

}
