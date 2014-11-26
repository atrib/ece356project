/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctor;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author atri
 */
public class add_treatment extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        try
        {
            String db_url = "jdbc:mysql://localhost:3306/project";
            String db_user = "testuser";
            String db_pwd = "test623";

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(db_url, db_user, db_pwd);
            con.prepareStatement("LOCK TABLES treatment_info WRITE, doctor_permissions WRITE").execute();
            PreparedStatement pst;
            
            pst = con.prepareStatement("SELECT MAX(treatment_num) from treatment_info where patient_SIN = ? AND visit_start_time = ?");
            pst.setInt(1, Integer.parseInt(request.getParameter("patient_SIN")));
            pst.setString(2, request.getParameter("time"));
            ResultSet result = pst.executeQuery();
            if(result.first())
            {
                int max = result.getInt(1);
                //Insrt perm
                pst = con.prepareStatement("INSERT IGNORE INTO doctor_permissions(patient_SIN, doctor_num)\n" +
                                        "	VALUES 	( ?, ?)");
                pst.setInt(1, Integer.parseInt(request.getParameter("patient_SIN")));
                pst.setInt(2, Integer.parseInt(request.getParameter("doc_ID")));
                pst.executeUpdate();
                
                pst = con.prepareStatement("INSERT INTO treatment_info (patient_SIN,visit_start_time,treatment_num, treatment_name, cost, doctor_num)\n" +
                                             "	VALUE 	(?, ?, ?, ?, ?, ?)");
                pst.setInt(1, Integer.parseInt(request.getParameter("patient_SIN")));
                pst.setString(2, request.getParameter("time"));
                pst.setInt(3, max+1);
                pst.setString(4, request.getParameter("treatment_name"));
                pst.setInt(5, Integer.parseInt(request.getParameter("treatment_cost")));
                pst.setInt(6, Integer.parseInt(request.getParameter("doc_ID")));
                pst.executeUpdate();
            
            }
            
            
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<body>");
            out.println("<script>window.close();</script>");
            out.println("</body>");
            out.println("</html>");
            con.prepareStatement("UNLOCK TABLES ").execute();
            if (con != null) 
                con.close();
        }
        catch(Exception e)
        {
            request.setAttribute("exception", e);
            request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
