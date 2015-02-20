/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.insurance.risk.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Tafadzwa
 */
import org.eclipse.persistence.jpa.PersistenceProvider;

public class Configuration {
    
    String propertiesLocation ;

    public String getPropertiesLocation() {
        return propertiesLocation;
    }

    public void setPropertiesLocation(String propertiesLocation) {
        this.propertiesLocation = propertiesLocation;
    }
    
    

    public Configuration() {
        //Check if the properties file exists. 
        //If it doesnt then the system must popup a dialog prompting the 
        //user to enter the database details 
        OSValidator os = new OSValidator();
        this.propertiesLocation = os.getDefaultConfigFile("cresterp.properties");
        checkIfDatabaseIsSetup();
    }

    private void checkIfDatabaseIsSetup() {
        if (!checkForDBProperties()) {
            JdgDBConfig configuration = new JdgDBConfig(null, true);
            configuration.setLocationByPlatform(true);
            configuration.setVisible(true);
        }

    }

    private boolean checkForDBProperties() {
        boolean result;
        try {
            File file = new File(getPropertiesLocation());
            FileInputStream fileInput = new FileInputStream(file);
            result = true;
        } catch (Exception ex) {
            result = false;
        }
        return result;
    }

    public EntityManagerFactory generateEntityManagerFactory() {

        // EntityManagerFactory emf = Persistence.createEntityManagerFactory("BrokePU",properties);
        PersistenceProvider pp = new PersistenceProvider();

        EntityManagerFactory emf = pp.createEntityManagerFactory("DebtorsDemoPersistancePU",
                generateProperties());

        return emf;

    }

    public Map generateProperties() {
        Map<String, String> properties = new HashMap<String, String>();
        readProperties();
        properties.put("javax.persistence.jdbc.url", props.getJdbc());
        properties.put("javax.persistence.jdbc.password", props.getPassword());
        properties.put("javax.persistence.jdbc.driver", props.getDriver());
        properties.put("javax.persistence.jdbc.user", props.getUser());
        properties.put("eclipselink.ddl-generation", "create-tables");

        return properties;
    }

    DBProps props = new DBProps();

    private void readProperties() {

        try {
            File file = new File(getPropertiesLocation());
            FileInputStream fileInput = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(fileInput);
            fileInput.close();

            Enumeration enuKeys = properties.keys();
            while (enuKeys.hasMoreElements()) {
                String key = (String) enuKeys.nextElement();
                String value = properties.getProperty(key);
               // System.out.println(key + ": " + value);
                if (key.contains("server")) {
                    props.setServer(value);
                }
                if (key.contains("port")) {
                    props.setPort(value);
                }
                if (key.contains("user")) {
                    props.setUser(value);
                }
                if (key.contains("database")) {
                    props.setDatabase(value);
                }
                if (key.contains("password")) {
                    props.setPassword(value);
                }
                if (key.contains("driver")) {
                    props.setDriver(value);
                }
                if (key.contains("jdbcurl")) {
                    props.setJdbc(value);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
