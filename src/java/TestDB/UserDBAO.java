package TestDB;

import java.sql.*;

public class UserDBAO {
    //public static final String url = "jdbc:mysql://localhost:8084/";
    public static final String url = "jdbc:mysql://localhost:3306/project";
    //public static final String url = "jdbc:mysql://eceweb.uwaterloo.ca:3306/";
    //public static final Sting url = "http://ece.uwaterloo.ca/~ece356/lab1"
    public static final String user = "testuser";
    public static final String pwd = "test623";
    
    public static void testConnection()
            throws ClassNotFoundException, SQLException {
        Statement stmt;
        Connection con;
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(url, user, pwd);
        stmt = con.createStatement();
        con.close();
    }
}
