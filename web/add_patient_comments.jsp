<%-- 
    Document   : add_patient_comments
    Created on : 25 Nov, 2014, 2:46:39 AM
    Author     : atri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add comments</title>
    </head>
    <body>
        <c:if test="${sessionScope.CurrentDoctor != null}"> 
            <form action ="add_patient_comments" method="POST">
                Enter patient SIN:<input type="number" name="patient_SIN" required="required" placeholder="10000000"/><p>
                Enter comments: <textarea name="comments" rows="5"  required="required" placeholder="--address--"></textarea>
                <input type="submit" value="Submit"/><p>
            </form>
            <a href="search_patient">Search patient SIN</a>
        </c:if>
    </body>
</html>
