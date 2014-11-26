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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author atri
 */
public class get_patient_visit_list extends HttpServlet {

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
            con.prepareStatement("LOCK TABLES visit_info READ, doctor_permissions READ").execute();
            PreparedStatement pst;
            int currentDoctorNum = ((DoctorData)request.getSession().getAttribute("CurrentDoctor")).getNumber();
            pst = con.prepareStatement("SELECT * FROM doctor_permissions WHERE patient_SIN = ? AND doctor_num=?");
            pst.setInt(1, Integer.parseInt(request.getParameter("patient_SIN")));
            pst.setInt(2, currentDoctorNum);
            ResultSet result = pst.executeQuery();
            
            if( result.first())
            {
                pst = con.prepareStatement("SELECT start_time FROM visit_info WHERE patient_SIN = ? AND doctor_num = ?");
                pst.setInt(1, Integer.parseInt(request.getParameter("patient_SIN")));
                pst.setInt(2, currentDoctorNum);
                result = pst.executeQuery();

                List <Timestamp> visit_list = new ArrayList<Timestamp>();
                while(result.next())
                {
                    visit_list.add(result.getTimestamp("start_time"));
                }
                request.setAttribute("patient_SIN", request.getParameter("patient_SIN"));
                request.setAttribute("treatment_name", request.getParameter("treatment_name"));
                request.setAttribute("treatment_cost", request.getParameter("treatment_cost"));
                request.setAttribute("doc_ID", request.getParameter("doc_ID"));
                request.setAttribute("visit_list", visit_list);
                request.getServletContext().getRequestDispatcher("/add_treatment.jsp").forward(request, response);
                con.prepareStatement("UNLOCK TABLES ").execute();
            }
            else
            {
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<body>");
                out.println("<script>window.close();</script>");
                out.println("</body>");
                out.println("</html>");
            }
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
