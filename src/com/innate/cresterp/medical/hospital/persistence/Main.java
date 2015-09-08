/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.innate.cresterp.medical.hospital.persistence;

import com.innate.cresterp.communication.entities.SMSMessage;
import com.innate.cresterp.communication.persistence.SMSMessageJpaController;
import com.innate.cresterp.security.entities.Company;
import com.innate.cresterp.security.persistence.CompanyJpaController;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Edmund
 */
public class Main {

    final static SMSMessageJpaController smsManager = new SMSMessageJpaController();
    static List<SMSMessage> messsages = new ArrayList<SMSMessage>();
    private static final String USER_AGENT = "Mozilla/5.0";
    final CompanyJpaController companyManager = new CompanyJpaController(null);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        startMessagingThread();

    }

    private static String processMessage(String number) {
        String mobile = number.replaceAll("\\s", "");
        if (mobile.startsWith("0")) {
            mobile = "263" + mobile;
        }

        return mobile;
    }

    private static void sendMessage(String message, String recipient, Company company) throws MalformedURLException, IOException {

        System.out.println("Inside sendMessage () function");
        String url = "http://www.cytnet.net/smsapi/?";
        String urlParameters = "user=" + company.getSmsUsername() + "&password="
                + company.getSmsPassword() + "&SMSText=" + message.trim() + "&sender=" + company.getMobileNumber().trim()
                + "&GSM=" + recipient;
        url = url + urlParameters;
        url = url.replaceAll(" ", "%20");
        String connect = url;
        connect = URLEncoder.encode(connect, "UTF-8");
        url = "http://www.cresterp.com/developers/bulk/directSend.php"
                + "?url="+connect;
        
        //url = "http://www.google.com";

        //url = URLEncoder.encode(url, "UTF-8");
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("accept", "text/xml");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }

    public static void startMessagingThread() {

        final List<Company> company = new CompanyJpaController(null).findCompanyEntities();
        new Thread(new Runnable() {

            public void run() {

                while (true) {

                    messsages = smsManager.getEntityManager().createQuery(
                            "SELECT m FROM SMSMessage m WHERE m.status != ?1"
                    )//)
                            .setParameter(1, "SENT")
                            .getResultList();
                    System.out.println("The list has: " + messsages.size());
                    for (SMSMessage message : messsages) {

                        try {

                            sendMessage(message.getMessage(), processMessage(message.getRecipient()), company.get(0));
                            message.setStatus("SENT");
                            smsManager.edit(message);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                }
            }
        }).start();
    }

}
