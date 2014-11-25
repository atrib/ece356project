/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patient;

import java.sql.*;
import java.util.logging.*;

/**
 *
 * @author atri
 */
public class PatientData 
{
    private int SIN;
    private String password;
    private String name;
    private long phone_number;
    
    private Connection con;
    
    PatientData()
    {
        SIN = 10000000;
        password = "password";
        name = "???";
        con = null;
    }
    
    void authenticate(int patient_SIN, String passwd) throws ClassNotFoundException, SQLException
    {
        SIN = patient_SIN;
        password = passwd;
        
        String db_url = "jdbc:mysql://localhost:3306/project";
        String db_user = "testuser";
        String db_pwd = "test623";


        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(db_url, db_user, db_pwd);
        PreparedStatement pst = con.prepareStatement("SELECT * FROM patient_info WHERE SocialIN = ? AND password = ?");
        pst.setInt(1, SIN);
        pst.setString(2, password);
        ResultSet result = pst.executeQuery();

        if(result.first())
        {
            name = result.getString("name");
            phone_number = result.getLong("phone_num");
        }

        if (con != null) 
            con.close();
    }   
    
    public String getName()
    {
        return this.name;
    } 
    
    public int getSIN()
    {
        return this.SIN;
    }
    
    public long getPhone()
    {
        return this.phone_number;
    }
    
    public void setPhone(long phone)
    {
        this.phone_number = phone;
    }
    
}
