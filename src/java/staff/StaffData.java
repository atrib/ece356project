/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package staff;

import java.sql.*;
import java.util.logging.*;

/**
 *
 * @author atri
 */
public class StaffData 
{
    protected final byte default_staff_status = 0;
    protected final byte financial_staff_status = 1;
    protected final byte legal_staff_status = 2;
    protected final byte secretarial_staff_status = 3;
    
    private int staff_number;
    private String password;
    private String name;
    private byte status;
    private Connection con;
    
    StaffData()
    {
        staff_number = 10000000;
        password = "password";
        name = "???";
        con = null;
    }
    
    void authenticate(int staff_num, String passwd) throws ClassNotFoundException, SQLException
    {
        staff_number = staff_num;
        password = passwd;
        
        String db_url = "jdbc:mysql://localhost:3306/project";
        String db_user = "testuser";
        String db_pwd = "test623";


        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(db_url, db_user, db_pwd);
        PreparedStatement pst = con.prepareStatement("SELECT * FROM staff_info WHERE staff_num = ? AND password = ?");
        pst.setInt(1, staff_number);
        pst.setString(2, password);  
        ResultSet result = pst.executeQuery();

        if(result.first())
        {
            name = result.getString("name");
            String tmpstatus = result.getString("status");
            if(tmpstatus.equals("???"))
                status = default_staff_status;
            else if (tmpstatus.equals("Financial"))
                status = financial_staff_status;
            else if (tmpstatus.equals("Secretarial"))
                status = secretarial_staff_status;
            else
                status = legal_staff_status;
        }

        if (con != null) 
            con.close();
    }
    
    public String getName()
    {
        return this.name;
    } 
    
    public int getNumber()
    {
        return this.staff_number;
    }
    
    public byte getStatus()
    {
        return this.status;
    }
    
    public byte getDefault_staff_status()
    {
        return this.default_staff_status;
    }
    
    public byte getFinancial_staff_status()
    {
        return this.financial_staff_status;
    }
    
    public byte getLegal_staff_status()
    {
        return this.legal_staff_status;
    }
    
    public byte getSecretarial_staff_status()
    {
        return this.secretarial_staff_status;
    }
}