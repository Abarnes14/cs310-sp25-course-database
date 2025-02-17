package edu.jsu.mcis.cs310.coursedb;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.Jsoner;
import edu.jsu.mcis.cs310.coursedb.dao.*;

public class Main {
    
    private static final String USERNAME = "nobody@jsu.edu";
    
    public static void main(String[] args) {
        
        DAOFactory daoFactory = new DAOFactory("coursedb");
        
        RegistrationDAO registrationDao = daoFactory.getRegistrationDAO();
        SectionDAO sectionDao = daoFactory.getSectionDAO();
        
        int studentid = daoFactory.getStudentDAO().find(USERNAME);
        
        if ( !daoFactory.isClosed() ) {
            System.out.println("Connected Successfully!");
        }
        
        String result = sectionDao.find(1, "CS", "310");
        System.out.println("Section Search Result: " + result);
        
        boolean registrationResult = registrationDao.create(studentid, 1, 12345);
        System.out.println("Registration Creation: " + (registrationResult ? "Successful" : "Failed"));
        
        boolean deleteResult = registrationDao.delete(studentid, 1, 12345);
        System.out.println("Registration Deletion: " + (deleteResult ? "Successful" : "Failed"));
        
        String registrations = registrationDao.list(studentid, 1);
        System.out.println("Registrations List: " + registrations);
    }
}
