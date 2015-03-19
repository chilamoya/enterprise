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
public class JdgDBConfig extends javax.swing.JDialog {

      DBProps props ;
      
    /**
     * Creates new form JdgDBConfig
     */
    public JdgDBConfig(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.props = new DBProps();
    }
    
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


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jtxtServer = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jtxtPort = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jtxtUser = new javax.swing.JTextField();
        jtxtPassword = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jtxtDatabaseName = new javax.swing.JTextField();
        jcboDriver = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Crest Database Connection");

        jLabel2.setText("Server");

        jLabel3.setText("Port");

        jLabel4.setText("Database User");

        jLabel5.setText("Password");

        jLabel6.setText("Database Name");

        jtxtDatabaseName.setText("cresterp");

        jcboDriver.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MySQL", "MSSQL", "Oracle" }));

        jLabel7.setText("Database Type");

        jButton1.setText("Save Configuration");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Test Connection");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Close");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jtxtServer, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jtxtPort, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jtxtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jtxtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jtxtDatabaseName, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jcboDriver, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jButton1)
                .addGap(10, 10, 10)
                .addComponent(jButton2)
                .addGap(6, 6, 6)
                .addComponent(jButton3))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel2))
                    .addComponent(jtxtServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel3))
                    .addComponent(jtxtPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel4))
                    .addComponent(jtxtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel5))
                    .addComponent(jtxtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel6))
                    .addComponent(jtxtDatabaseName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel7))
                    .addComponent(jcboDriver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(19, Short.MAX_VALUE))
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        // TODO add your handling code here:
        testConnection();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(JdgDBConfig.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JdgDBConfig.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JdgDBConfig.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JdgDBConfig.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JdgDBConfig dialog = new JdgDBConfig(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JComboBox jcboDriver;
    private javax.swing.JTextField jtxtDatabaseName;
    private javax.swing.JTextField jtxtPassword;
    private javax.swing.JTextField jtxtPort;
    private javax.swing.JTextField jtxtServer;
    private javax.swing.JTextField jtxtUser;
    // End of variables declaration//GEN-END:variables
}
