<%-- 
    Document   : patient_login
    Created on : 24 Nov, 2014, 7:15:44 PM
    Author     : atri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patient Login</title>
    </head>
    <body>
        <jsp:scriptlet>            
            Boolean unsuccesfulLogin = (Boolean)request.getAttribute("unsuccessful_login");
            if(unsuccesfulLogin != null && unsuccesfulLogin.booleanValue() == true)
            {
                request.setAttribute("unsuccessful_login", new Boolean(false));
        </jsp:scriptlet>
                <%= "Login unsuccessful. Try again" %>
        <jsp:scriptlet>
            }  
        </jsp:scriptlet>
        <h1>Enter your details to login</h1>
        <form method="post" action="Patient_login_check">
            <label> ID number: </label><input type ='number' name="SIN" required="required" placeholder = "10000000"/><p>
                <label>Password: </label><input type ='password' name="password" required="required" placeholder = "password"/><p>
            <input type="submit" value="Login">
        </form>
    </body>
</html>
