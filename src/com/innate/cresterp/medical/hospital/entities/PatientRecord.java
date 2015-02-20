/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.medical.hospital.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Edmund
 */
@Entity
@NamedQueries({@NamedQuery(name="findPatient", query="SELECT p FROM PatientRecord p WHERE p.hospitalNumber  = :hp"),
@NamedQuery(name="getAllPatients", query="SELECT s FROM PatientRecord s")})
public class PatientRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String hospitalNumber;
    private String name;
    private String surname;
    private String nationalId;
    private String address;
    private String cell;
    private String nokName;
    private String nokSurname;
    private String nokCell;
    private String nokAddress;
    private MedicalAid medicalAid ;
    private String email;
    
    @OneToMany (mappedBy="patientRecordId")
    private List<Aadmissions> admissionsList;
    @OneToMany (mappedBy="patientRecordId")
    private List<Invoices> invoicesList;

    public List<Invoices> getInvoicesList() {
        return invoicesList;
    }

    public void setInvoicesList(List<Invoices> invoicesList) {
        this.invoicesList = invoicesList;
    }

   
    
    public List<Aadmissions> getAdmissionsList() {
        return admissionsList;
    }

    public void setAdmissionsList(List<Aadmissions> admissionsList) {
        this.admissionsList = admissionsList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getHospitalNumber() {
        return hospitalNumber;
    }

    public void setHospitalNumber(String hospitalNumber) {
        this.hospitalNumber = hospitalNumber;
    }

 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getNokAddress() {
        return nokAddress;
    }

    public void setNokAddress(String nokAddress) {
        this.nokAddress = nokAddress;
    }

    public String getNokCell() {
        return nokCell;
    }

    public void setNokCell(String nokCell) {
        this.nokCell = nokCell;
    }

    public String getNokName() {
        return nokName;
    }

    public void setNokName(String nokName) {
        this.nokName = nokName;
    }

    public String getNokSurname() {
        return nokSurname;
    }

    public void setNokSurname(String nokSurname) {
        this.nokSurname = nokSurname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    private String DOB;

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MedicalAid getMedicalAid() {
        return medicalAid;
    }

    public void setMedicalAid(MedicalAid medicalAid) {
        this.medicalAid = medicalAid;
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
        if (!(object instanceof PatientRecord)) {
            return false;
        }
        PatientRecord other = (PatientRecord) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.PatientInfo[id=" + id + "]";
    }

}
