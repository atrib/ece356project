<%-- 
    Document   : view_patient_comments
    Created on : 25 Nov, 2014, 5:41:04 PM
    Author     : atri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View patient comments</title>
    </head>
        <c:if test="${sessionScope.CurrentDoctor != null}"> 
            <form action ="view_patient_comments" method="POST">
                    Enter patient SIN:<input type="number" name="patient_SIN" required="required" placeholder="10000000"/><p>
                <input type="submit" value="Submit"/><p>
            </form>
            <a href="search_patient">Search patient SIN</a>
        </c:if>
</html>
