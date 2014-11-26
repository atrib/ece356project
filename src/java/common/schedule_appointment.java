/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author atri
 */
public class schedule_appointment extends HttpServlet {

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
            throws ServletException, IOException {
        
        String db_url = "jdbc:mysql://localhost:3306/project";
        String db_user = "testuser";
        String db_pwd = "test623";
        String url="";
        String error_message="";
        Connection con;
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(db_url, db_user, db_pwd);
            con.prepareStatement("LOCK TABLES appointment_info WRITE, patient_info WRITE").execute();
            PreparedStatement pst; 
            int month = Integer.parseInt(request.getParameter("start_month"));
            int day = Integer.parseInt(request.getParameter("start_day"));
            int hour = Integer.parseInt(request.getParameter("start_hour"));
            int minute = Integer.parseInt(request.getParameter("start_minute"));
            if(!(month > 12 || month <1 || day<1 || day>31 || hour<0 || hour>23 || minute < 0 || minute >59))
            {
                Timestamp start_time=new Timestamp(Integer.parseInt(request.getParameter("start_year"))-1900, month-1, day, hour , minute, 0, 0);
            
                Timestamp end_time;

                if(start_time.after(new Timestamp((new Date()).getTime())) == true)
                {
                    //generate endtime
                    end_time= new Timestamp(start_time.getTime() + ((long)Integer.parseInt(request.getParameter("length")))*60*1000);
                    //check if time is valid, in the future, and does not clash with other appointments 
                    pst = con.prepareStatement("SELECT start_time, end_time FROM appointment_info WHERE ((start_time < ? AND end_time>= ? AND doctor_num = ?) OR (start_time <= ? AND end_time> ? AND doctor_num = ?))");
                    pst.setTimestamp(1, end_time);
                    pst.setTimestamp(2, end_time);
                    pst.setInt(3, Integer.parseInt(request.getParameter("doc_ID")));
                    pst.setTimestamp(4, start_time);
                    pst.setTimestamp(5, start_time);
                    pst.setInt(6, Integer.parseInt(request.getParameter("doc_ID")));
                    ResultSet result = pst.executeQuery();

                    if(result.first() == false)
                    {
                        pst = con.prepareStatement("INSERT INTO appointment_info(patient_SIN, doctor_num, start_time, end_time)"
                                                            + " VALUE (?, ?, ?, ?)");
                        pst.setInt(1, Integer.parseInt(request.getParameter("patient_ID")));
                        pst.setInt(2, Integer.parseInt(request.getParameter("doc_ID")));
                        pst.setTimestamp(3, start_time);
                        pst.setTimestamp(4, end_time);
                        pst.executeUpdate();
                        
                        pst = con.prepareStatement("UPDATE patient_info SET status='Appointment' WHERE SocialIN = ?");
                        pst.setInt(1, Integer.parseInt(request.getParameter("patient_ID")));
                        pst.executeUpdate();

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
                    else
                    {
                        //Clash error
                        url = "/schedule_appointment.jsp";
                        request.setAttribute("unsuccessful_schedule", new Boolean(true));
                        request.setAttribute("error_msg", "Time clash");
                    }


                }
                else
                {
                    url = "/schedule_appointment.jsp";
                    request.setAttribute("unsuccessful_schedule", new Boolean(true));
                    request.setAttribute("error_msg", "Given date and time is past");
                    //Old date error 
                }
            }
            else
            {
                //Illegal time
                url = "/schedule_appointment.jsp";
                request.setAttribute("unsuccessful_schedule", new Boolean(true));
                request.setAttribute("error_msg", "Given date and time is invalid");
            }
            con.prepareStatement("UNLOCK TABLES ").execute();
            
            if(con != null)
                con.close();
        }
        catch(Exception e)
        {
            request.setAttribute("exception", e);
            url = "/error.jsp";
            
        }
        request.getServletContext().getRequestDispatcher(url).forward(request, response);
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
