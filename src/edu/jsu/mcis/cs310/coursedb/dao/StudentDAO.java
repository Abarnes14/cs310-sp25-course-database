package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentDAO {
    
    private static final String QUERY_FIND = "SELECT * FROM student WHERE username = ?";
    
    private final DAOFactory daoFactory;
    
    StudentDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public int find(String username) {
        
        int id = 0;
        
        try {
            
            Connection conn = daoFactory.getConnection();
        
            
            PreparedStatement pstmt = conn.prepareStatement(QUERY_FIND);
            pstmt.setString(1, username);
            
            boolean hasresults = pstmt.execute();
            
            if ( hasresults ) {
                
                ResultSet resultset = pstmt.getResultSet();
                
                if (resultset.next())
                    
                    id = resultset.getInt("id");
                
            }
            
        }
        catch (Exception e) { e.printStackTrace(); }
        
        return id;
        
    }
        
}