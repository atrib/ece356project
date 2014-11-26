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

/**
 *
 * @author atri
 */
public class edit_patient_diagnosis extends HttpServlet {

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
        DoctorData currentDoctor;
        if((currentDoctor = ((DoctorData)request.getSession().getAttribute("CurrentDoctor"))) != null)
        {
            if(request.getParameter("diagnosis") != null)
            {
                try
                {
                    String db_url = "jdbc:mysql://localhost:3306/project";
                    String db_user = "testuser";
                    String db_pwd = "test623";

                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(db_url, db_user, db_pwd);
                    con.prepareStatement("LOCK TABLES visit_info WRITE").execute();
                    PreparedStatement pst;

                    pst = con.prepareStatement("UPDATE visit_info SET diagnosis = ?,prescription = ? WHERE patient_SIN = ? AND start_time = ?");
                    pst.setString(1, (request.getParameter("original_diagnosis")+"\n"+request.getParameter("diagnosis")));
                    pst.setString(2, (request.getParameter("original_prescription")+"\n"+request.getParameter("prescription")));
                    pst.setInt(3, Integer.parseInt(request.getParameter("patient_SIN")));
                    pst.setString(4, request.getParameter("start_time"));
                    pst.executeUpdate();
                    con.prepareStatement("UNLOCK TABLES ").execute();
            
                    request.removeAttribute("diagnosis");
                    request.removeAttribute("prescription");
                    
                }
                catch(Exception e)
                {
                    request.setAttribute("exception", e);
                    request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);

                }
                
            }
            response.setContentType("text/html;charset=UTF-8");
            try
            {

                String db_url = "jdbc:mysql://localhost:3306/project";
                String db_user = "testuser";
                String db_pwd = "test623";

                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(db_url, db_user, db_pwd);
                con.prepareStatement("LOCK TABLES visit_info READ").execute();
                PreparedStatement pst;
                ResultSet visit_result;
                PrintWriter out = response.getWriter();
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                int SIN = Integer.parseInt(request.getParameter("patient_SIN"));
                
                pst = con.prepareStatement("SELECT * FROM visit_info WHERE patient_SIN = ? AND doctor_num = ?");
                pst.setInt(1, SIN);
                pst.setInt(2, currentDoctor.getNumber());
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
                            "<TH>Diagnosis</TH>\n"+
                            "<TH>Prescription</TH>\n"+
                        "    </TR>");
                while(visit_result.next())
                {
                    out.println("<tr><td>"+visit_num+"</td>");
                    out.println("<td>"+visit_result.getTimestamp("start_time")+"</td>");
                    out.println("<td>"+visit_result.getString("diagnosis")+"<p><form action='edit_patient_diagnosis' method='POST'>"
                            + "<input type=\"hidden\" name=\"patient_SIN\" value='"+SIN+"' /> "
                            + "<input type=\"hidden\" name=\"start_time\" value='"+visit_result.getString("start_time")+"' /> "
                            + "<input type=\"hidden\" name=\"original_diagnosis\" value='"+visit_result.getString("diagnosis")+"' />"
                            + "<input type=\"hidden\" name=\"original_prescription\" value='"+visit_result.getString("prescription")+"' />"
                            + "<textarea rows='3' name='diagnosis'> </textarea></td>");
                    out.println("<td>"+visit_result.getString("prescription") +"<p><textarea rows='3' name='prescription'>"+"</textarea></td>");
                    out.println("<td><input type='submit' value='Edit'/></form></td></tr>");
                    visit_num++;
                }
                out.println("</TABLE>");
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
