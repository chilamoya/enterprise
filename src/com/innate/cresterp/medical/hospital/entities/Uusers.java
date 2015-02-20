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
    @NamedQuery(name="getUsersOnBillingPoint", query="SELECT u FROM Uusers u WHERE u.billingPointId = :bp")
})
public class Uusers implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String surname;
    private String username;
    private String password;
    private String employeeNumber;
    private String idNumber;
    private int status;
    private int departmentRestriction;

    @ManyToOne
    private Deepartment departmentId;
    @ManyToOne
    private BiillingPoint billingPointId;


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public Deepartment getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Deepartment departmentId) {
        this.departmentId = departmentId;
    }

    public int getDepartmentRestriction() {
        return departmentRestriction;
    }

    public void setDepartmentRestriction(int departmentRestriction) {
        this.departmentRestriction = departmentRestriction;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BiillingPoint getBillingPointId() {
        return billingPointId;
    }

    public void setBillingPointId(BiillingPoint billingPointId) {
        this.billingPointId = billingPointId;
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
        if (!(object instanceof Uusers)) {
            return false;
        }
        Uusers other = (Uusers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Uusers[id=" + id + "]";
    }

}
