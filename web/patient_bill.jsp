<%-- 
    Document   : patient_bill
    Created on : 23 Nov, 2014, 7:29:32 PM
    Author     : atri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patient Bill</title>
    </head>
    <body>
        <h3>View patient bill</h3>
        
        <form action="patient_bill" method="POST">
            <label>Patient number</label><input type="number" name="patient_SIN" required="required" placeholder="10000000"/><p>
            <label>Choose month and year</label><select name = "month">
                    <option value='1'>1</option>
                    <option value='2'>2</option>
                    <option value='3'>3</option>
                    <option value='4'>4</option>
                    <option value='5'>5</option>
                    <option value='6'>6</option>
                    <option value='7'>7</option>
                    <option value='8'>8</option>
                    <option value='9'>9</option>
                    <option value='10'>10</option>
                    <option value='11'>11</option>
                    <option value='12'>12</option>
            </select> <input type="number" name="year" placeholder="2014"/><p>
            <input type="submit" name="bill_type" value="Get monthly bill"/>
            <input type="submit" name="bill_type" value="Get last 5 visit bill"/>
        </form>
    </body>
</html>
