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
public class patient_bill extends HttpServlet {

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
                con.prepareStatement("LOCK TABLES visit_info READ, treatment_info READ").execute();
                PreparedStatement pst;
                ResultSet result;
                PrintWriter out = response.getWriter();
                out.println("<!DOCTYPE html>");
                out.println("<html>");
            
                int patient_SIN = Integer.parseInt(request.getParameter("patient_SIN"));
                if(request.getParameter("bill_type").equals("Get monthly bill") == true)
                {
                    int month = Integer.parseInt(request.getParameter("month"));
                    int year = Integer.parseInt(request.getParameter("year"));
                    int num_visits=0;
                    int cost_treatment=0;
                    pst = con.prepareStatement("SELECT count(*) FROM visit_info WHERE EXTRACT(YEAR FROM start_time) = ?  AND EXTRACT(MONTH FROM start_time) = ? AND patient_SIN = ?");
                    pst.setInt(1, year);
                    pst.setInt(2, month);
                    pst.setInt(3, patient_SIN);
                    result = pst.executeQuery();
                    if(result.first())
                    {
                        num_visits=result.getInt(1);
                    }
                    pst = con.prepareStatement("SELECT SUM(cost) from treatment_info WHERE EXTRACT(YEAR FROM visit_start_time) = ?  AND EXTRACT(MONTH FROM visit_start_time) = ? AND patient_SIN = ?");
                    pst.setInt(1, year);
                    pst.setInt(2, month);
                    pst.setInt(3, patient_SIN);
                    result = pst.executeQuery();
                    if(result.first())
                    {
                        cost_treatment=result.getInt(1);
                    }
                    out.println("<head>");
                    out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                        "        <title>Monthly bill for "+ new DateFormatSymbols().getMonths()[month-1]+","+year+"</title>");            
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<TABLE BORDER=\"1\">\n" +
                        "    <TR>\n" +
                        "        <TH># visits</TH>\n" +
                        "        <TH>Cost of treatments</TH>\n" +
                            "<TH>Net bill amount</TH>\n" +
                        "    </TR>");
                    out.println("<TR><TH>"+ num_visits+"</TH>\n" +
                        "        <TH>"+cost_treatment+"</TH>\n" +
                            "<TH>"+(100*num_visits+ cost_treatment)+"</TH>\n" +
                        "    </TR></TABLE>");
                    con.prepareStatement("UNLOCK TABLES ").execute();
            
                }
                else if(request.getParameter("bill_type").equals("Get last 5 visit bill") == true)
                {
                    pst = con.prepareStatement("SELECT start_time FROM visit_info WHERE patient_SIN = ?");
                    pst.setInt(1, patient_SIN);
                    result = pst.executeQuery();
                    out.println("<head>");
                    out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                        "        <title>Last 5 visit bill</title>");            
                    out.println("</head>");
                    out.println("<body>");

                    if(result.last()!= false)
                    {
                        out.println("Cost of visit is $100 for visit plus cost of treatments received<p>");
                        out.println("<TABLE BORDER=\"1\">\n" +
                        "    <TR>\n" +
                        "        <TH>Visit Start Date</TH>\n" +
                        "        <TH>Bill</TH>\n" +
                        "    </TR>");
                        int count=0;
                        do
                        {
                            pst = con.prepareStatement("SELECT SUM(cost) from treatment_info WHERE patient_SIN = ? AND visit_start_time = ?");
                            pst.setInt(1, patient_SIN);
                            Timestamp start_time = result.getTimestamp("start_time");
                            pst.setTimestamp(2, start_time);
                            ResultSet result_2 = pst.executeQuery();
                            result_2.first();
                            out.println("   <TR>"
                                    + "         <TH>"+start_time.toString()+"</TH>"
                                    + "         <TH>"+(100+result_2.getInt(1))+ "</TH>"
                                    + "     </TR>");
                            count++;
                        }
                        while(result.previous() != false && count < 5);
                        out.println("</TABLE>");
                    }
                    else
                    {
                        out.println("No visits");
                    }
                    con.prepareStatement("UNLOCK TABLES ").execute();
            
                }
                else
                {
                    //we have a problem
                }
                out.println("</body>");
                out.println("</html>");
                if (con != null) 
                    con.close();
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
