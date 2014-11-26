<%-- 
    Document   : doctor_revenue
    Created on : 26 Nov, 2014, 3:55:29 AM
    Author     : atri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Doctor Revenue</title>
    </head>
    <body>
        <c:if test="${sessionScope.CurrentStaff != null}"> 
            <form action ="view_doctor_revenue" method="POST">
                    Enter doctor number:<input type="number" name="doc_ID" required="required" placeholder ="10000000" /><p>
                <input type="submit" value="Submit"/><p>
            </form>
            <a href="search_doctor">Search doctor ID</a> 
        </c:if>
    </body>
</html>
