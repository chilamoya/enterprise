/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.innate.cresterp.insurance.risk.persistence;

/**
 *
 * @author Tafadzwa
 */
public class OSValidator {
 
	private static final String OS = System.getProperty("os.name").toLowerCase();
 
	public  String getDefaultConfigFile(String filename) {
 
		System.out.println(OS);
                String path = "";
 
		if (isWindows()) {
			path = filename;
		} else if (isMac()) {
			path = "/Users/"+filename;
		} else if (isUnix()) {
			System.out.println("This is Unix or Linux");
		} else if (isSolaris()) {
			System.out.println("This is Solaris");
		} else {
			System.out.println("Your OS is not supported!!");
		}
                return path;
	}
 
	public static boolean isWindows() {
 
		return (OS.indexOf("win") >= 0);
 
	}
 
	public static boolean isMac() {
 
		return (OS.indexOf("mac") >= 0);
 
	}
 
	public static boolean isUnix() {
 
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
 
	}
 
	public static boolean isSolaris() {
 
		return (OS.indexOf("sunos") >= 0);
 
	}
 
}