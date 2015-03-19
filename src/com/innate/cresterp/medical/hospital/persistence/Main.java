/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.medical.hospital.persistence;

import com.innate.cresterp.accounting.entities.Account;
import com.innate.cresterp.accounting.persistence.AccountJpaController;
import com.innate.cresterp.security.entities.Company;
import com.innate.cresterp.security.persistence.CompanyJpaController;
import java.util.Date;

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
        try {
            
           Company company = new CompanyJpaController(null).findCompanyEntities().get(0);
           System.out.println(company.getCode());
            
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
}
