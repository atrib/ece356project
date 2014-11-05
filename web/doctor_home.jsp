<%-- 
    Document   : doctor_home
    Created on : 5 Nov, 2014, 2:07:42 AM
    Author     : atri
--%>

<%@page import="doctor.DoctorData"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Doctor Home</title>
    </head>
    <body>
        <h1>Login successful</h1>


        <jsp:declaration>
        String doctorname;
        </jsp:declaration>

        <jsp:scriptlet>
        DoctorData currentDoctor = (DoctorData)request.getAttribute("Doctor");
        </jsp:scriptlet>
        Doctor name: 
        <%= currentDoctor.name %>
      
    </body>
</html>
