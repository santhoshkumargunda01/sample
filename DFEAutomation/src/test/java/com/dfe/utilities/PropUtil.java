package com.dfe.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropUtil {
	
	public static Properties prop = new Properties();
	
	public String getPropertyValue(String key) {
		try {
			prop.load( new FileInputStream("./config/application.properties") );
			return prop.getProperty(key);
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

}
