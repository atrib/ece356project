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
public class view_patient_history extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        String db_url = "jdbc:mysql://localhost:3306/project";
        String db_user = "testuser";
        String db_pwd = "test623";
        Connection con;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(db_url, db_user, db_pwd);
            con.prepareStatement("LOCK TABLES patient_info READ, treatment_info READ, visit_info READ, treatment_info READ").execute();
            PreparedStatement pst;
            
            int SIN = Integer.parseInt(request.getParameter("SocialIN"));
            int doctor_num = ((DoctorData)request.getSession().getAttribute("CurrentDoctor")).getNumber();
            pst = con.prepareStatement("SELECT * FROM doctor_permissions WHERE patient_SIN = ? AND doctor_num = ?");
            pst.setInt(1, SIN);
            pst.setInt(2, doctor_num);
            ResultSet result = pst.executeQuery();
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "        <title>Doctor Search</title>");            
            out.println("</head>");
            out.println("<body>");
            if(result.first())
            {
                pst = con.prepareStatement("SELECT * FROM patient_info WHERE SocialIN = ?");
                pst.setInt(1, SIN);
                result = pst.executeQuery();

                
                out.println("<TABLE BORDER=\"1\">\n" +
    "            <TR>\n" +
    "                <TH>SIN</TH>\n" +
    "                <TH>Name</TH>\n" +
    "                <TH>Age</TH>\n" +
    "                <TH>Status</TH>\n" +
    "            </TR>");

                if(result.first())
                {
                    out.println("<TH>"+SIN+"</TH>\n" +
    "                <TH>"+result.getString("name")+"</TH>\n" +
    "                <TH>"+result.getInt("age")+"</TH>\n" +
    "                <TH>"+result.getString("status")+"</TH>\n" +
    "            </TR></TABLE>");
                }
                out.println("<p>Visit history:<p>");

                pst = con.prepareStatement("SELECT * FROM visit_info WHERE patient_SIN = ?");
                pst.setInt(1, SIN);
                ResultSet visit_result = pst.executeQuery();
                int visit_num=1;

                out.println("<TABLE BORDER=\"1\">\n" +
                    "    <TR>\n" +
                    "        <TH>Visit #</TH>\n" +
                    "        <TH>Visit Date</TH>\n" +
                        "<TH>Visit Doctor</TH>\n"+
                        "<TH>Diagnosis</TH>\n"+
                        "<TH>Prescription</TH>\n" +
                        "<TH>Treatment Name</TH>\n"+
                        "<TH>Treatment Number</TH>\n"+
                        "<TH>Treatment Doctor</TH>"+
                    "    </TR>");
                while(visit_result.next())
                {
                    pst = con.prepareStatement("SELECT * FROM treatment_info WHERE patient_SIN = ? AND visit_start_time = ?");
                    pst.setInt(1, SIN);
                    pst.setTimestamp(2, visit_result.getTimestamp("start_time"));
                    ResultSet treatment_result = pst.executeQuery();
                    treatment_result.beforeFirst();
                    int count = 0;
                    while(treatment_result.next() != false)
                        count++;
                    if(count == 0)
                        count=1;
                    treatment_result.beforeFirst();
                    out.println("<td rowspan = '"+count+"'>"+visit_num+"</td>");
                    out.println("<td rowspan = '"+count+"'>"+visit_result.getTimestamp("start_time")+"</td>");
                    out.println("<td rowspan = '"+count+"'>"+visit_result.getInt("doctor_num")+"</td>");
                    out.println("<td rowspan = '"+count+"'>"+ visit_result.getString("diagnosis")+"</td>"+
                            "<td rowspan = '"+count+"'>"+ visit_result.getString("prescription")+"</td>");
                    while(treatment_result.next() != false)
                    {
                        out.println("<td>"+treatment_result.getShort("treatment_num")+"</td>");
                        out.println("<td>"+treatment_result.getInt("doctor_num")+"</td>");
                        out.println("<td>"+treatment_result.getString("treatment_name")+"</td></tr>");
                    }
                    if(treatment_result.first() == false)
                        out.println("<td>N/A</td><td>N/A</td><td>N/A</td><tr>");
                    visit_num++;
                }
                out.println("</TABLE>");
                
            }
            else
            {
                out.println("You do not have permissions");
            }
            out.println("</body></html>");
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
