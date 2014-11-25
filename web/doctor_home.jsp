<%-- 
    Document   : doctor_home
    Created on : 5 Nov, 2014, 2:07:42 AM
    Author     : atri
--%>

<%@page import="doctor.DoctorData"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Doctor Home</title>
    </head>
    <body>
        <p align="right"><a href="logout">Logout</a></p>
        <h1>Login successful</h1>


<%--        
        <jsp:declaration>
        String doctorname;
        </jsp:declaration>

        <jsp:scriptlet>
        DoctorData currentDoctor = (DoctorData)request.getAttribute("Doctor");
        </jsp:scriptlet>
--%>
        <c:if test="${sessionScope.CurrentDoctor != null}"> 
            Hello, Dr. ${sessionScope.CurrentDoctor.getName()}
            <p><li><a href="doctor_patient_view.jsp">See my patients</a></li>
            <li><a href="doctor_treatment_view.jsp">Record of patients treated</a></li>
        </c:if>
    
    </body>
</html>
