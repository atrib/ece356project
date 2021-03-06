<%-- 
    Document   : staff_home
    Created on : 20 Nov, 2014, 9:57:37 PM
    Author     : atri
--%>

<%@page import="staff.StaffData"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Staff Home</title>
    </head>
    <body>
        <p align="right"><a href="logout">Logout</a></p>
        <h1>Login successful</h1>
        
<%--        
        <jsp:declaration>
        </jsp:declaration>

        <jsp:scriptlet>
        StaffData currentStaff = (StaffData)request.getSession().getAttribute("Staff");
        </jsp:scriptlet>
--%> 
    
        <c:if test="${sessionScope.CurrentStaff != null}"> 
            Hello, ${sessionScope.CurrentStaff.getName()}
            <p>
            <c:if test = "${sessionScope.CurrentStaff.getStatus() == sessionScope.CurrentStaff.default_staff_status}" >
                Default staff
            </c:if> 
            <c:if test = "${sessionScope.CurrentStaff.getStatus() == sessionScope.CurrentStaff.financial_staff_status}" >
                <li><a href="patient_bill.jsp" target="_blank">View patient bill</a></li>
                <li><a href="all_patient_bill.jsp" target="_blank">View all patient monthly bill</a></li>
                <li><a href ="doctor_revenue.jsp" target="_blank">View doctor revenue</a></li>
            </c:if> 
            <c:if test = "${sessionScope.CurrentStaff.getStatus() == sessionScope.CurrentStaff.legal_staff_status}" >
                Legal staff
            </c:if> 
            <c:if test = "${sessionScope.CurrentStaff.getStatus() == sessionScope.CurrentStaff.secretarial_staff_status}" >
                <li><a href="add_patient.jsp" target="_blank">Add patient</a></li>
                <li><a href="schedule_appointment.jsp" target="_blank">Schedule appointment</a></li>
                <li><a href="see_appointments_today" target="_blank">See today's appointments</a></li>
                <li><a href="edit_patient_details.jsp" target="_blank">Edit patient details</a></li>
                <li><a href="assign_doc.jsp" target="_blank">Assign doctor to patient</a></li>
            </c:if>
        </c:if>
    </body>
</html>
