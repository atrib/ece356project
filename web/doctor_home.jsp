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


        <c:if test="${sessionScope.CurrentDoctor != null}"> 
            Hello, Dr. ${sessionScope.CurrentDoctor.getName()}
            <p><li><a href="doctor_patient_view.jsp" target="_blank">See my patients</a></li>
            <li><a href="doctor_treated_patient_view.jsp" target="_blank">Record of patients treated</a></li>
            <li><a href="view_patient_history.jsp" target="_blank">View patient history</a></li>
            <li><a href="give_permissions.jsp" target="_blank">Give patient-access permission</a></li>
            <li><a href="add_patient_comments.jsp" target="_blank">Insert patient comments</a></li>
            <li><a href="view_patient_comments.jsp" target="_blank">View patient comments</a></li>
            <li><a href='enter_diagnosis.jsp'>Enter diagnosis and prescription</a></li>
        </c:if>
    
    </body>
</html>
