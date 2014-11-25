<%-- 
    Document   : patient_home
    Created on : 5 Nov, 2014, 2:07:42 AM
    Author     : atri
--%>

<%@page import="patient.PatientData"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patient Home</title>
    </head>
    <body>
        <p align="right"><a href="logout">Logout</a></p>
        <h1>Login successful</h1>


<%--        
        <jsp:declaration>
        String patientname;
        </jsp:declaration>

        <jsp:scriptlet>
        PatientData currentPatient = (PatientData)request.getAttribute("CurrentPatient");
        </jsp:scriptlet>
--%>
        <c:if test="${sessionScope.CurrentPatient != null}">
            Hello, ${sessionScope.CurrentPatient.getName()}
            <p>
            <li><a href="edit_patient_details.jsp" target="_blank">Edit my details</a></li>
            <li><a href="patient_invoice" target="_blank">View my bills</a></li>
        </c:if> 
    </body>
</html>
