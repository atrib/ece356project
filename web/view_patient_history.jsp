<%-- 
    Document   : view_patient_history
    Created on : 25 Nov, 2014, 4:53:15 PM
    Author     : atri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View patient history</title>
    </head>
    <body>
        <c:if test="${sessionScope.CurrentDoctor != null}"> 
            <form action ="view_patient_history" method="POST">
                    Enter patient SIN:<input type="number" name="SocialIN" required="required" placeholder="10000000"/><p>
                <input type="submit" value="Submit"/><p>
            </form>
            <a href="search_patient">Search patient SIN</a>
        </c:if>
    </body>
</html>