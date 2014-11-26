/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package staff;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormatSymbols;

/**
 *
 * @author atri
 */
public class patient_enter_visit extends HttpServlet {

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
        if(request.getSession().getAttribute("CurrentStaff") != null)
        {
            response.setContentType("text/html;charset=UTF-8");

            try
            {
                String db_url = "jdbc:mysql://localhost:3306/project";
                String db_user = "testuser";
                String db_pwd = "test623";


                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(db_url, db_user, db_pwd);
                PreparedStatement pst;
                ResultSet result;
                PrintWriter out = response.getWriter();
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                
                int patient_SIN = Integer.parseInt(request.getParameter("patient_SIN"));
                int doctor_num = Integer.parseInt(request.getParameter("doctor_num"));
                
                pst = con.prepareStatement("INSERT INTO visit_info(patient_SIN, start_time, end_time, diagnosis, prescription, doctor_num) VALUE (?, ?, ?, ?, ?, ?)");
                pst.setInt(1, patient_SIN);
                pst.setString(2, request.getParameter("start_time"));
                pst.setString(3, request.getParameter("end_time"));
                pst.setString(4, "???");
                pst.setString(5, "???");
                pst.setInt(6, doctor_num);
                pst.executeUpdate();
                
                pst = con.prepareStatement("DELETE FROM appointment_info WHERE patient_SIN = ? AND start_time = ?");
                pst.setInt(1, patient_SIN);
                pst.setString(2, request.getParameter("start_time"));
                pst.executeUpdate();
                
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<body>");
                out.println("<script>window.close();</script>");
                out.println("</body>");
                out.println("</html>");
                
            }
            catch(Exception e)
            {
                request.setAttribute("exception", e);
                request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);

            }
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
