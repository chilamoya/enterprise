/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.medical.hospital.persistence;

import com.innate.cresterp.insurance.risk.persistence.SystemUserJpaController;
import com.innate.cresterp.insurance.risk.entities.SystemUser;

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
            
                SystemUser su = new  SystemUser();
                su.setCellphoneNumber("263733863515");
                su.setEmail("matengwane@gmail.com");
                su.setFullName("Mmbada mugomo");
                su.setPassword("matengwane");
                su.setUserName("mbada");
                
                SystemUserJpaController suc = new SystemUserJpaController( );
                
                suc.create(su);
                       
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
