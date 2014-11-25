<%-- 
    Document   : assign_doc
    Created on : 25 Nov, 2014, 1:38:58 AM
    Author     : atri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Assign doctor to patient</title>
    </head>
    <body>
        <c:if test="${sessionScope.CurrentStaff != null}"> 
            <form action ="assign_doc" method="POST">
                    Enter doctor number:<input type="number" name="doc_ID" required="required" placeholder ="10000000" /><p>
                    Enter patient SIN:<input type="number" name="patient_SIN" required="required" placeholder="10000000"/><p>
                <input type="submit" value="Submit"/><p>
            </form>
            <a href="search_doctor">Search doctor ID</a> 
            <a href="search_patient">Search patient SIN</a>
        </c:if>
    </body>
</html>
