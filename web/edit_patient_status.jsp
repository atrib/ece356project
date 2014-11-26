<%-- 
    Document   : edit_patient_status
    Created on : 26 Nov, 2014, 7:21:30 AM
    Author     : atri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit patient status</title>
    </head>
    <body>
        <form action="edit_patient_status" method="POST">
            <label>Patient number</label><input type="number" name="patient_SIN" required="required" placeholder="10000000"/><p>
            <label>Choose status</label><select name = "status">
                    <option value='Healthy'>Healthy</option>
                    <option value='Diagnosis in progress'>Diagnosis in progress</option>
                    <option value='Under prescription'>Under prescription</option>
                    <option value='Treatment underway'>Treatment underway</option>
                    <option value='Surgery'>Surgery</option>
                    <option value='Transferred'>Transferred</option>
            </select> <p>
            <input type="submit" name="bill_type" value="Submit"/>
        </form>
    </body>
</html>
