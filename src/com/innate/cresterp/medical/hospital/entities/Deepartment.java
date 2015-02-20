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
import javax.persistence.OneToMany;

/**
 *
 * @author Edmund
 */
@Entity
public class Deepartment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String remarks;
    @OneToMany (mappedBy="departmentId")
    private List<Waard> wardCollection;
    @OneToMany (mappedBy="departmentId")
    private List<Uusers> usersList;

    public List<Uusers> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Uusers> usersList) {
        this.usersList = usersList;
    }



    public List<Waard> getWardCollection() {
        return wardCollection;
    }

    public void setWardCollection(List<Waard> wardCollection) {
        this.wardCollection = wardCollection;
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
        if (!(object instanceof Deepartment)) {
            return false;
        }
        Deepartment other = (Deepartment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Department[id=" + id + "]";
    }

}
