<%-- 
    Document   : doctor_patient_view
    Created on : 21 Nov, 2014, 1:03:57 AM
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
        <title>Your patients</title>
    </head>
    <body>
        <H1>Patient details: </H1>

        <% 
            String db_url = "jdbc:mysql://localhost:3306/project";
            String db_user = "testuser";
            String db_pwd = "test623";
            Connection con = DriverManager.getConnection(db_url, db_user, db_pwd);
            DoctorData currentDoctor = (DoctorData)(request.getSession().getAttribute("CurrentDoctor"));
            PreparedStatement pst = con.prepareStatement("SELECT * FROM patient_info WHERE default_doctor = ?");
            pst.setInt(1, currentDoctor.getNumber());
            //pst.setString(2, password);
            pst.executeQuery();      
            ResultSet resultset = pst.executeQuery();
        %>

        <TABLE BORDER="1">
            <TR>
                <TH>ID</TH>
                <TH>Name</TH>
                <TH>City</TH>
                <TH>State</TH>
                <TH>Country</TH>
            </TR>
            <% while(resultset.next()){ %>
            <TR>
                <TD> <%= resultset.getString(1) %></td>
                <TD> <%= resultset.getString(2) %></TD>
                <TD> <%= resultset.getString(3) %></TD>
                <TD> <%= resultset.getString(4) %></TD>
                <TD> <%= resultset.getString(5) %></TD>
            </TR>
            <% } %>
        </TABLE>
    </body>
</html>
