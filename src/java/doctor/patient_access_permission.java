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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author atri
 */
public class patient_access_permission extends HttpServlet {

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
        String db_url = "jdbc:mysql://localhost:3306/project";
        String db_user = "testuser";
        String db_pwd = "test623";
        Connection con;
        
        try
        {
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<body>");
                
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(db_url, db_user, db_pwd);
            con.prepareStatement("LOCK TABLES doctor_permissions WRITE").execute();
            PreparedStatement pst;
            
            int SIN = Integer.parseInt(request.getParameter("patient_SIN"));
            int doctor_num = ((DoctorData)request.getSession().getAttribute("CurrentDoctor")).getNumber();
            
            pst = con.prepareStatement("SELECT * FROM doctor_permissions WHERE patient_SIN = ? AND doctor_num = ?");
            pst.setInt(1, SIN);
            pst.setInt(2, doctor_num);
            ResultSet result = pst.executeQuery();
            
            if(result.first())
            {

                pst = con.prepareStatement("INSERT INTO doctor_permissions(patient_SIN, doctor_num) VALUE (?, ?)");
                pst.setInt(1, SIN);
                pst.setInt(2, Integer.parseInt(request.getParameter("doc_ID")));
                pst.executeUpdate();
                con.prepareStatement("UNLOCK TABLES ").execute();
            
                response.setContentType("text/html;charset=UTF-8");
                out.println("<script>window.close();</script>");

            }
                
            else
            {
                out.println("You do not have permissions");
            }
            out.println("</body></html>");
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
