<%-- 
    Document   : doctor_treatment_view
    Created on : 25 Nov, 2014, 3:58:23 PM
    Author     : atri
--%>

<%@page import="doctor.DoctorData"%>
<%@ page import="java.sql.*" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Treated patients</title>
    </head>
    <body>
        <h1>Treated patient details:</h1>
    <% 
            String db_url = "jdbc:mysql://localhost:3306/project";
            String db_user = "testuser";
            String db_pwd = "test623";
            Connection con = DriverManager.getConnection(db_url, db_user, db_pwd);
            con.prepareStatement("LOCK TABLES patient_info READ, treatment_info READ").execute();
            DoctorData currentDoctor = (DoctorData)(request.getSession().getAttribute("CurrentDoctor"));
            PreparedStatement pst = con.prepareStatement("SELECT DISTINCT(patient_SIN) FROM treatment_info WHERE doctor_num = ?");
            pst.setInt(1, currentDoctor.getNumber());
            //pst.setString(2, password);
            pst.executeQuery();     
            ResultSet resultset = pst.executeQuery();
        %>

        <TABLE BORDER="1">
            <TR>
                <TH>SIN</TH>
                <TH>Name</TH>
                <TH>Age</TH>
                <TH>Status</TH>
            </TR>
            <% while(resultset.next())
            {
                int SIN = resultset.getInt("patient_SIN");
            %>
            <TR>
                <%  pst = con.prepareStatement("SELECT name, age, status FROM patient_info WHERE SocialIN = ?");
                    pst.setInt(1, SIN);
                    ResultSet patient_info = pst.executeQuery();
                    if(patient_info.first() ==true)
                    {
                %>
                <TD> <a href="view_patient_history?SocialIN=<%=SIN %>" target="_blank"><%= SIN %></a></td>
                <TD> <%= patient_info.getString("name") %></TD>
                <TD> <%= patient_info.getString("age") %></TD>
                <TD> <%= patient_info.getString("status") %></TD>
            </TR>
            <%      }
                }
            con.prepareStatement("UNLOCK TABLES").execute(); %>
        </TABLE>
    </body>
</html>