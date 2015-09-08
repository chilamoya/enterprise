/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.pos.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Tafadzwa
 */
@Entity
public class POSSetting implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    private String code ;
    
    private String name ;
    
    private String description;
    
    @Column(length = 2000)
    private String wfield1;
    
    
    @Column(length = 2000)
    private String wfield2;
    
    @Column(length = 2000)
    private String wfield3;
    
    @Column(length = 2000)
    private String wfield4;
    
    @Column(length = 2000)
    private String wfield5;
    
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
        if (!(object instanceof POSSetting)) {
            return false;
        }
        POSSetting other = (POSSetting) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.innate.cresterp.pos.entities.POSSetting[ id=" + id + " ]";
    }
    
}
