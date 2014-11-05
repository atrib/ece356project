/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctor;

import java.sql.*;
import java.util.logging.*;

/**
 *
 * @author atri
 */
public class DoctorData 
{
    public int doctor_number;
    public String password;
    public String name;
    public Connection con;
    
    DoctorData()
    {
        doctor_number = 10000000;
        password = "password";
        name = "???";
        con = null;
    }
    
    void authenticate(int doc_num, String pass) throws ClassNotFoundException, SQLException
    {
        doctor_number = doc_num;
        password = pass;
        
        String db_url = "jdbc:mysql://localhost:3306/project";
        String db_user = "testuser";
        String db_pwd = "test623";


        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(db_url, db_user, db_pwd);
        PreparedStatement pst = con.prepareStatement("SELECT * FROM doctor_info WHERE doctor_num = ? AND password = ?");
        pst.setInt(1, doctor_number);
        pst.setString(2, password);
        pst.executeQuery();      
        ResultSet result = pst.executeQuery();

        if(result.first())
        {
            name = result.getString("name");
        }
        else
        {
            
        }

        if (con != null) 
            con.close();

        }   
}
