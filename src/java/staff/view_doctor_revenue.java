/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package staff;

import doctor.DoctorData;
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
public class view_doctor_revenue extends HttpServlet {

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
            Connection con = DriverManager.getConnection(db_url, db_user, db_pwd);
            con.prepareStatement("LOCK TABLES treatment_info READ, visit_info READ").execute();
            PreparedStatement pst = con.prepareStatement("SELECT * FROM visit_info LEFT JOIN treatment_info ON (visit_info.start_time = treatment_info.visit_start_time AND visit_info.patient_SIN = treatment_info.patient_SIN) WHERE (visit_info.doctor_num = ? OR treatment_info.doctor_num = ?)");
            int doc_num=Integer.parseInt(request.getParameter("doc_ID"));
            pst.setInt(1, doc_num);
            pst.setInt(2, doc_num);
            pst.executeQuery();     
            ResultSet resultset = pst.executeQuery();
            
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "        <title>Doctor Search</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<table BORDER=\"1\"><tr><th>Visit #</th><th>Patient SIN</th><th>Start time</th><th>Treatment Number</th><th>Cost</th></tr>");
            int visit_num = 0;
            int cur_SIN=0;
            String cur_start_time="";
            while(resultset.next() != false)
            {
                int SIN = resultset.getInt("patient_SIN");
                String start_time = resultset.getString("start_time");
                
                if(cur_SIN != SIN || cur_start_time.equals(start_time) == false)
                {
                    cur_SIN = SIN;
                    cur_start_time=start_time;
                    visit_num++;
                    out.println("<tr><th>"+visit_num+"</th>");
                    out.println("<th>"+cur_SIN+"</th>");
                    out.println("<th>"+cur_start_time+"</th>");
                    out.println("<th></th>");
                    if(resultset.getInt("visit_info.doctor_num") == doc_num)
                        out.println("<th>"+100+"</th></tr>");
                    else
                        out.println("<th></th></tr>");
                }
                int treatment_num = resultset.getInt("treatment_num");
                if(treatment_num != 0)
                {
                    out.println("<tr><th>"+visit_num+"</th><th></th><th></th>");
                    out.println("<th>"+treatment_num+"</th>");
                    out.println("<th>"+resultset.getInt("cost")+"</th></tr>");
                }
                
                out.println();
            }
            out.println("</table></body></html>");
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
