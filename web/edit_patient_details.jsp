<%-- 
    Document   : edit_patient_details
    Created on : 24 Nov, 2014, 7:48:01 PM
    Author     : atri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit your details</title>
    </head>
    <body>
        <h3>Enter details</h3>
        <form action="edit_my_details" method="POST">
        <table BORDER="0" text-align = "left">
            <tr><td><label>Health Card Number</label></td><th> <input type="number" name="HCN"  placeholder="100000000"/></th></tr>
            <tr><td><label>Phone number</label></td><th><input type="number" name="phone"  placeholder ="9876543210" /></th></tr>
            <tr><td><label>Address</label></td><th><textarea name="address" rows="5"  placeholder="--address--"></textarea></th></tr>
            <tr><td><label>Age</label></td><th><input type ="number" name ="age" placeholder="55"/></th></tr>
            <tr><td><input type="submit" value="Submit"></td></tr>
        </table>
        </form>
    </body>
</html>
