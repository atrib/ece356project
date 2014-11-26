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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author atri
 */
public class see_appointments_today extends HttpServlet {

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
        Calendar calendar = Calendar.getInstance();
        String db_url = "jdbc:mysql://localhost:3306/project";
        String db_user = "testuser";
        String db_pwd = "test623";

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(db_url, db_user, db_pwd);
            PreparedStatement pst;
            ResultSet result;
            PrintWriter out = response.getWriter();
            
            pst = con.prepareStatement("SELECT * FROM appointment_info WHERE EXTRACT(YEAR FROM start_time) = ? AND EXTRACT(MONTH FROM start_time) = ? AND EXTRACT(DAY FROM start_time) = ?");
            pst.setInt(1, calendar.get(Calendar.YEAR));
            pst.setInt(2,  (calendar.get(Calendar.MONTH)+1));
            pst.setInt(3, calendar.get(Calendar.DAY_OF_MONTH));
            result = pst.executeQuery();
            out.println("<!DOCTYPE html>");
            out.println("<html>");out.println("<head>");
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "        <title>Today's appointments</title>");            
            out.println("</head>");
            out.println("<body>");
            if(result.first() == true)
            {
                out.println("<TABLE BORDER=\"1\">\n" +
                        "    <TR>\n" +
                        "        <TH>Patient SIN</TH>\n" +
                        "        <TH>Doctor number</TH>\n" +
                        "        <TH>Start time</TH>\n" +
                        "       <TH>Login</TH>\n"+
                        "    </TR>");
                do
                {
                    out.println("<TH>"+result.getInt("patient_SIN")+"</TH>"
                                +"<TH>"+result.getInt("doctor_num")+"</TH>"
                                +"<TH>"+result.getTimestamp("start_time").toString()+"</TH>"
                                +"<TH><a href = \"patient_enter_visit?patient_SIN="+result.getInt("patient_SIN")+
                                                                    "&doctor_num="+result.getInt("doctor_num")+
                                                                    "&start_time="+result.getTimestamp("start_time").toString()+
                                                                    "&end_time="+result.getTimestamp("end_time").toString()+"\">Enter</a></TR>");
                }
                while(result.next() == true);
                        
            }
            else
            {
                out.println("No appointments today");
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
