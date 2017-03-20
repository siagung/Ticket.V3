/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opass.kiosk;

/**
 *
 * @author agung
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class QueuePropertyValues {
      
    private Properties prop;
    private FileInputStream input = null;
    
    public QueuePropertyValues(){
        prop = new Properties();
    }
    
    public Properties getProperties() throws FileNotFoundException, IOException{
        File jarPath =new File(QueuePropertyValues.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String propertiesPath = jarPath.getParentFile().getAbsolutePath();
        System.out.println(propertiesPath);
        input = new FileInputStream(propertiesPath+"/config.properties"); 
	prop.load(input);        
        return prop;    
    }
    
    public void closeProperties(){
        if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
			}
	}
    }

    /*public String getRedisServer2() throws IOException {
        String result = "";
        Properties prop = new Properties();
        String propFileName = "/config.properties";
    try {
        File jarPath =new File(QueuePropertyValues.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String propertiesPath=jarPath.getParentFile().getAbsolutePath();
        System.out.println(" propertiesPath-"+propertiesPath);
        prop.load(new FileInputStream(propertiesPath+propFileName));
    } catch (IOException e1) {
        System.out.println(e1);
    }
        result = prop.getProperty("JEDIS_SERVER");
        return result;
    }*/
}
