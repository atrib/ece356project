<%-- 
    Document   : schedule_appointment.jsp
    Created on : 22 Nov, 2014, 7:03:22 PM
    Author     : atri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Schedule Appointment</title>
    </head>
    <body>
        <jsp:scriptlet>            
            Boolean unsuccesfulSchedule = (Boolean)request.getAttribute("unsuccessful_schedule");
            if(unsuccesfulSchedule != null && unsuccesfulSchedule.booleanValue() == true)
            {
                String error_msg = (String)request.getAttribute("error_msg");
                request.removeAttribute("unsuccessful_schedule");
                request.removeAttribute("error_msg");
        </jsp:scriptlet>
        <%= "Scheduling unsuccessful. Try again" %><p>
        <%= error_msg %>
        <jsp:scriptlet>
            }  
        </jsp:scriptlet>
        <c:if test="${sessionScope.CurrentStaff != null}">
            <h3>Enter appointment details</h3>
            <table><tr><td><a href="search_doctor" target="_blank">Search doctor ID</a></td><td><a href="search_patient" target="_blank">Search patient SIN</a></td></tr></table>
            <form action="schedule_appointment">
                <label>Enter doctor ID: </label><input type="number" name="doc_ID" required="required" placeholder="10000000"/><p>
                <lable>Enter patient ID:</lable><input type="number" name="patient_ID" required="required" placeholder="10000000"/><p>
                <label>Enter start time: Year</label><input type="text" cols = "4" name="start_year" required="required" placeholder="2014"/>
                <label>Month</label><input type="text" cols = "2" name="start_month" required="required" placeholder="01"/>
                <label>Day</label><input type="text" cols = "2" name="start_day" required="required" placeholder="01"/>
                <label>Hour</label><input type="text" cols = "2" name="start_hour" required="required" placeholder="10"/>
                <label>Minute</label><input type="text" cols = "2" name="start_minute" required="required" placeholder="00"/><p>
                <label>Enter duration:</label><input type="text" name="length" required="required" placeholder="30"/>minutes<p>
                    <input type="Submit" value="Submit"/>
            </form>
        </c:if>
    </body>
</html>
