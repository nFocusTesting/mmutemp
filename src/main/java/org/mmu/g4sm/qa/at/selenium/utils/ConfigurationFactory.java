package org.mmu.g4sm.qa.at.selenium.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationFactory {

	private static Properties prop;
	
	private static String propertiesResourcePath = "src/resources/properties/";
	private static String frameworkPropertiesFilename = "framework.properties";
	
	
	public ConfigurationFactory() {
		ConfigurationFactory.loadProperties();
	}
		
	
	public static Properties getProp() {
        return prop;
    }
    
    private static void loadProperties() {
        try {
            if ( prop == null ) {
                prop = new Properties();
                prop.load( new FileInputStream( propertiesResourcePath + frameworkPropertiesFilename ) );
            }

        } catch( Exception e ) {
        	LoggingFactory.error(e);
        }
    }

}
