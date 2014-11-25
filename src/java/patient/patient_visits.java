/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patient;

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
public class patient_visits extends HttpServlet {

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
        PatientData currentPatient;
        if((currentPatient = ((PatientData)request.getSession().getAttribute("CurrentPatient"))) != null)
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
                ResultSet visit_result;
                PrintWriter out = response.getWriter();
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                
                pst = con.prepareStatement("SELECT * FROM visit_info WHERE patient_SIN = ?");
                pst.setInt(1, currentPatient.getSIN());
                visit_result = pst.executeQuery();
                int visit_num=1;
                out.println("<head>");
                    out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                        "        <title>Full billing information</title>");            
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<TABLE BORDER=\"1\">\n" +
                        "    <TR>\n" +
                        "        <TH>Visit #</TH>\n" +
                        "        <TH>Visit Date</TH>\n" +
                            "<TH>Visit Doctor</TH>\n"+
                            "<TH>Diagnosis</TH>\n"+
                            "<TH>Prescription</TH>\n"+
                        "    </TR>");
                while(visit_result.next())
                {
                    out.println("<td>"+visit_num+"</td>");
                    out.println("<td>"+visit_result.getTimestamp("start_time")+"</td>");
                    out.println("<td>"+visit_result.getInt("doctor_num")+"</td>");
                    out.println("<td>"+visit_result.getString("diagnosis")+"</td>");
                    out.println("<td>"+visit_result.getString("prescription")+"</td></tr>");
                    visit_num++;
                }
                out.println("</TABLE>");
                if (con != null) 
                    con.close();

            }
            catch(Exception e)
            {
                request.setAttribute("exception", e);
                request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);

            }
        }
        else
        {
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "        <title>Full billing information</title>");            
            out.println("</head>");
            out.println("<body> Not logged in</body>");
            out.println("</html>");
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
