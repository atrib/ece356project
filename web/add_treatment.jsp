<%-- 
    Document   : add_treatment
    Created on : 26 Nov, 2014, 6:09:52 AM
    Author     : atri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add treatment</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${requestScope.visit_list != null}">
                <table border='1'>
                    <tr><th>Visit time</th><th></th></tr>
                    <c:forEach items="${requestScope.visit_list}" var="item">
                        <tr><th>${item}</th><th><a href='add_treatment?time=${item}&patient_SIN=${requestScope.patient_SIN}&treatment_name=${requestScope.treatment_name}&treatment_cost=${requestScope.treatment_cost}&doc_ID=${requestScope.doc_ID}'>Add</a></th></tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:when test="${sessionScope.CurrentDoctor != null}"> 
                <form action ="get_patient_visit_list" method="POST">
                        Enter patient SIN:<input type="number" name="patient_SIN" required="required" placeholder="10000000"/><p>
                        Enter treatment name:<input type="text" name="treatment_name" required="required" placeholder="--Treatment Name--"/><p>
                        Enter cost:<input type="number" name="treatment_cost" required="required" placeholder="100"/><p>
                        Enter doctor number:<input type="number" name="doc_ID" required="required" placeholder ="10000000" /><p>
                    <input type="submit" value="Submit"/><p>
                </form>
                <a href="search_doctor">Search doctor ID</a> 
                <a href="search_patient">Search patient SIN</a>
            </c:when>
        </c:choose>
            
        
    </body>
</html>
