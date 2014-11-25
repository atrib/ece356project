<%-- 
    Document   : newjspadd_patient.jsp
    Created on : 21 Nov, 2014, 9:01:59 PM
    Author     : atri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add patient</title>
    </head>
    <body>
        <h3>Patient details</h3><p>
        <form action="add_patient">
        <table BORDER="0" text-align = "left">
            <tr><td><label>Social Insurance number</label></th><th><input type="number" name="SIN" required ="required" placeholder="100000000"/></th></tr>
            <tr><td><label>Name</label> </td><th><input type="text" name="name" required = "required" placeholder="--name--"/></th></tr>
            <tr><td><label>Health Card Number</label></td><th> <input type="number" name="HCN" required="required" placeholder="100000000"/></th></tr>
            <tr><td><label>Phone number</label></td><th><input type="number" name="phone" required="required" placeholder ="9876543210" /></th></tr>
            <tr><td><label>Address</label></td><th><textarea name="address" rows="5"  required="required" placeholder="--address--"></textarea></th></tr>
            <tr><td><label>Age</label></td><th><input type ="number" name ="age" required="required" placeholder="55"/></th></tr>
            <tr><td><label>Default Doctor Number</label></td><th><input type="number" name="def_doc" required="required" placeholder="100000000"/></th></tr>
            <tr><td><input type="submit" value="Submit"></td><th><a href='search_doctor' target='_blank'>Search for Doctor ID</a></th></tr>
        </table>
        </form>
    </body>
</html>
