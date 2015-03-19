/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.medical.hospital.persistence;

import com.innate.cresterp.accounting.entities.Account;
import com.innate.cresterp.accounting.persistence.AccountJpaController;
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
            
               /** SystemUser su = new  SystemUser();
                su.setCellphoneNumber("263733863515");
                su.setEmail("matengwane@gmail.com");
                su.setFullName("Mmbada mugomo");
                su.setPassword("matengwane");
                su.setUserName("mbada");
                
                
                
                SystemUserJpaController suc = new SystemUserJpaController( );
                
                suc.create(su);*/
            
            Account acc = new Account();
            acc.setName("Simba");
            acc.setCode("1234");
            acc.setDateCreated(new Date());
            acc.setDiscription("kunakirwa baba");
            acc.setBalance(9887.98);
            
            AccountJpaController ac = new AccountJpaController();
            ac.create(acc);
           /** AccountTransaction acct = new AccountTransaction();
            acct.setNameOfTransactions("Mari");
            acct.setDebitAccount(null);
            acct.setCreditAccount(null);
            acct.setDescription(null);
            acct.setDateCreated(null);
            acct.setAccountProcessTransaction(null);*/
            
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
}
