/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.insurance.risk.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;
import org.eclipse.persistence.jpa.PersistenceProvider;

/**
 *
 * @author Tafadzwa
 */
public class JdgConfiguration extends javax.swing.JFrame {

    private String path;
      DBProps props ;

    /**
     * Creates new form JdgConfiguration
     */
    public JdgConfiguration(String path) {
        initComponents();
        this.path = path;
        this.props = new DBProps();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jtxtServer = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtxtPort = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jtxtUser = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jtxtPassword = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jcboDriver = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        jtxtDatabaseName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Crest ERP Database Configuration");

        jLabel1.setText("Server");

        jLabel2.setText("Port");

        jLabel3.setText("Database User");

        jButton1.setText("Save Configuration");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setText("Password");

        jLabel5.setText("Database Type");

        jcboDriver.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MySQL", "MSSQL", "Oracle" }));

        jButton2.setText("Test Connection");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jtxtDatabaseName.setText("cresterp");

        jLabel6.setText("Database Name");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtServer, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jtxtPort, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jcboDriver, 0, 236, Short.MAX_VALUE)
                            .addComponent(jtxtPassword, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jtxtUser, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jtxtDatabaseName))))
                .addContainerGap(16, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addGap(77, 77, 77))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtxtServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtxtPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtDatabaseName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jcboDriver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
//        try {
//            // TODO add your handling code here:
//            // Write to file
//            FileWriter fw = new FileWriter(path);
//            BufferedWriter bw = new BufferedWriter(fw);
//            bw.write(jtxtServer.getText() + ":" + jtxtPort.getText());
//            bw.close();
//            //  jTextArea1.setText("Your database config file was successfully created at: "+path);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        savePropertiesFile();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void savePropertiesFile() {

        try {
            Properties properties = new Properties();
            properties.setProperty("server", jtxtServer.getText());
            properties.setProperty("port", jtxtPort.getText());
            properties.setProperty("user", jtxtUser.getText());
            properties.setProperty("password", jtxtPassword.getText());
            properties.setProperty("database", jtxtDatabaseName.getText());
            
            if (jcboDriver.getSelectedItem().toString().contains("MySQL")) {
                properties.setProperty("driver", "com.mysql.jdbc.Driver");
                properties.setProperty("jdbcurl", "jdbc:mysql://"+jtxtServer.getText()+":"+jtxtPort.getText()
                        + "/"+jtxtDatabaseName.getText());
            }
            if (jcboDriver.getSelectedItem().toString().contains("MSSQL")) {
                properties.setProperty("driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
                properties.setProperty("jdbcurl", "jdbc:sqlserver://"+jtxtServer.getText()+ "/"+jtxtDatabaseName.getText());
            }
            if (jcboDriver.getSelectedItem().toString().contains("Oracle")) {
                properties.setProperty("driver", "oracle.jdbc.driver.OracleDriver");
                properties.setProperty("jdbcurl", "jdbc:oracle:thin:@"+jtxtServer.getText()+":"+jtxtPort.getText()+":"+ jtxtDatabaseName.getText());
            }

            File file = new File("cresterp.properties");
            FileOutputStream fileOut = new FileOutputStream(file);
            properties.store(fileOut, "Database Configuration");
            fileOut.close();
            JOptionPane.showMessageDialog(rootPane, "Your configurations have been successfully saved");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void testConnection() {

        readProperties();
        EntityManagerFactory emf = generateEntityManagerFactory();
        if (emf!=null){
            JOptionPane.showMessageDialog(rootPane, "Test Connection succeeded");
        }else {
                JOptionPane.showMessageDialog(rootPane, "OOPS! Test connection FAILED");
        }
        
    }

    public EntityManagerFactory generateEntityManagerFactory() {

        // EntityManagerFactory emf = Persistence.createEntityManagerFactory("BrokePU",properties);
        PersistenceProvider pp = new PersistenceProvider();
        Map map =  generateProperties();

        EntityManagerFactory emf = pp.createEntityManagerFactory("DebtorsDemoPersistancePU",
               map);

        return emf;

    }

    public Map generateProperties() {
        Map<String, String> properties = new HashMap<String, String>();

        //Read the properties file 
        properties.put("javax.persistence.jdbc.url", props.getJdbc()  );
        properties.put("javax.persistence.jdbc.password", props.getPassword());
        properties.put("javax.persistence.jdbc.driver", props.getDriver());
        properties.put("javax.persistence.jdbc.user", props.getUser());
        properties.put("eclipselink.ddl-generation", "create-tables");

        return properties;
    }

    private void readProperties() {
        
        try {
			File file = new File("cresterp.properties");
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();

			Enumeration enuKeys = properties.keys();
			while (enuKeys.hasMoreElements()) {
				String key = (String) enuKeys.nextElement();
				String value = properties.getProperty(key);
				System.out.println(key + ": " + value);
                                if (key.contains("server")){
                                    props.setServer(value);
                                }
                                if (key.contains("port")){
                                    props.setPort(value);
                                }
                                if (key.contains("user")){
                                    props.setUser(value);
                                }
                                  if (key.contains("database")){
                                    props.setDatabase(value);
                                }
                                if (key.contains("password")){
                                    props.setPassword(value);
                                }
                                if (key.contains("driver")){
                                    props.setDriver(value);
                                }
                                if (key.contains("jdbcurl")){
                                    props.setJdbc(value);
                                }
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        // TODO add your handling code here:
        testConnection();

    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JdgConfiguration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JdgConfiguration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JdgConfiguration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JdgConfiguration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JdgConfiguration(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JComboBox jcboDriver;
    private javax.swing.JTextField jtxtDatabaseName;
    private javax.swing.JTextField jtxtPassword;
    private javax.swing.JTextField jtxtPort;
    private javax.swing.JTextField jtxtServer;
    private javax.swing.JTextField jtxtUser;
    // End of variables declaration//GEN-END:variables
}
