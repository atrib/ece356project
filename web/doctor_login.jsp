<%-- 
    Document   : doctor_login
    Created on : 5 Nov, 2014, 12:11:21 AM
    Author     : atri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Doctor login page</title>
    </head>
    <body>
        <jsp:scriptlet>            
            Boolean unsuccesfulReturn = (Boolean)request.getAttribute("unsuccessful_login");
            if(unsuccesfulReturn != null && unsuccesfulReturn.booleanValue() == true)
            {
                request.setAttribute("unsuccessful_login", new Boolean(false));
        </jsp:scriptlet>
                <%= "Login unsuccessful. Try again" %>
        <jsp:scriptlet>
            }  
        </jsp:scriptlet>
        <h1>Enter your details to login</h1>
        <form method="post" action="Doctor_login_check">
            <label> ID number: </label><input type ='number' name="doctor_num" required="required" placeholder = "10000000"/><p>
                <label>Password: </label><input type ='password' name="password" required="required" placeholder = "password"/><p>
            <input type="submit" value="Login">
        </form>
    </body>
</html>
