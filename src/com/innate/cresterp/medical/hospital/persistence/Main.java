/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.medical.hospital.persistence;

import com.innate.cresterp.security.entities.Company;
import com.innate.cresterp.security.entities.Settings;
import com.innate.cresterp.security.persistence.CompanyJpaController;

/**
 *
 * @author Edmund
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Settings settings = new Settings();
        Company company = new Company();
        company.setAddress("Test");
        company.setName("Kitsi yatota pvt");
        company.setCode(settings.encrypt("TETRG-84875 | 85875-BFHF7-NJDJHF-67675", company.getName()));
        company.setContactEmail("yete");

        CompanyJpaController companyManager = new CompanyJpaController(null);

        companyManager.create(company);
//          
        Company company1 = new CompanyJpaController(null).findCompanyEntities().get(0);
        System.out.println("Decrypted: " + settings.decrypt(company1.getCode(), company1.getName()));

    }

}
