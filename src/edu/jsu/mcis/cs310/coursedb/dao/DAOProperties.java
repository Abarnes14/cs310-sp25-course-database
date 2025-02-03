package edu.jsu.mcis.cs310.coursedb.dao;

import java.io.InputStream;
import java.util.Properties;

public class DAOProperties {

    private static final String PROPERTIES_FILE = "dao.properties";
    private static final Properties PROPERTIES = new Properties();
    
    private final String prefix;

    static {
        
        InputStream file = DAOProperties.class.getResourceAsStream(PROPERTIES_FILE);

        try {
            PROPERTIES.load(file);
        }
        catch (Exception e) { e.printStackTrace(); }
        
    }

    DAOProperties(String prefix) { this.prefix = prefix; }

    String getProperty(String key) {
        
        String fullKey = prefix + "." + key;
        String property = PROPERTIES.getProperty(fullKey);

        if (property == null || property.trim().length() == 0)
            property = null;

        return property;
        
    }

}