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
@NamedQueries({@NamedQuery(name="getAdmissions", query = "SELECT a FROM Aadmissions a WHERE a.patientRecordId = :id"),
@NamedQuery(name="updateBed", query="UPDATE Aadmissions a SET a.status = 0 WHERE a.bedId = :b AND a.status=1")})
public class Aadmissions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private PatientRecord patientRecordId;
    private String dateAdmitted;
    private String dateDischarged;
    private int status;
    @ManyToOne
    private Beed bedId;

    public Beed getBedId() {
        return bedId;
    }

    public String getDateDischarged() {
        return dateDischarged;
    }

    public void setDateDischarged(String dateDischarged) {
        this.dateDischarged = dateDischarged;
    }

    public void setBedId(Beed bedId) {
        this.bedId = bedId;
    }

    public String getDateAdmitted() {
        return dateAdmitted;
    }

    public void setDateAdmitted(String dateAdmitted) {
        this.dateAdmitted = dateAdmitted;
    }

    public PatientRecord getPatientRecordId() {
        return patientRecordId;
    }

    public void setPatientRecordId(PatientRecord patientRecordId) {
        this.patientRecordId = patientRecordId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
        if (!(object instanceof Aadmissions)) {
            return false;
        }
        Aadmissions other = (Aadmissions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Aadmissions[id=" + id + "]";
    }

}
