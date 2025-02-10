package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	
	static final String path="Login.properties";

	public static String getPropertyValue(String key) throws Exception{
      
        Properties properties = new Properties();


        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            properties.load(fileInputStream);

            
            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null; 
        }
    }

}

