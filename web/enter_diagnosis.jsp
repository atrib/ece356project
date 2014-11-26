<%-- 
    Document   : enter_diagnosis
    Created on : 25 Nov, 2014, 11:40:52 PM
    Author     : atri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Choose patient</title>
    </head>
        <c:if test="${sessionScope.CurrentDoctor != null}"> 
            <form action ="edit_patient_diagnosis" method="POST">
                    Enter patient SIN:<input type="number" name="patient_SIN" required="required" placeholder="10000000"/><p>
                <input type="submit" value="Submit"/><p>
            </form>
            <a href="search_patient">Search patient SIN</a>
        </c:if>
</html>
