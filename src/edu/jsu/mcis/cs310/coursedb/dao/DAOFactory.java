package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.*;

public final class DAOFactory {
    
    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_DRIVER = "driver";
    private static final String PROPERTY_USERNAME = "username";
    private static final String PROPERTY_PASSWORD = "password";
    
    private final String url, username, password;
    private Connection conn;
    
    public DAOFactory(String prefix) {

        DAOProperties properties = new DAOProperties(prefix);

        this.url = properties.getProperty(PROPERTY_URL);
        this.username = properties.getProperty(PROPERTY_USERNAME);
        this.password = properties.getProperty(PROPERTY_PASSWORD);
        
        try {
            conn = DriverManager.getConnection(url, username, password);
        }
        catch (Exception e) {
            conn = null;
            e.printStackTrace();
        }
        
    }
    
    Connection getConnection() {
        return conn;
    }
    
    public boolean isClosed() {
        
        boolean isClosed = true;
        
        try {
            isClosed = conn.isClosed();
        }
        catch (Exception e) { e.printStackTrace(); }
        
        return isClosed;
        
    }
    
    public RegistrationDAO getRegistrationDAO() {
        return new RegistrationDAO(this);
    }
    
    public SectionDAO getSectionDAO() {
        return new SectionDAO(this);
    }
    
    public StudentDAO getStudentDAO() {
        return new StudentDAO(this);
    }
    
}
