/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package staff;

import java.sql.*;
import java.util.logging.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author atri
 */
public class add_patient extends HttpServlet {

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
        String url;
        Connection con;
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(db_url, db_user, db_pwd);
            PreparedStatement pst = con.prepareStatement(   "INSERT IGNORE INTO phone_number_map(phone_num, address)\n" +
                                                            "	VALUES (?, ?)");
            pst.setLong(1, Long.parseLong(request.getParameter("phone")));
            pst.setString(2, request.getParameter("address"));
            pst.executeUpdate();
            
            pst = con.prepareStatement(   "INSERT INTO patient_info(SocialIN, healthC_num, name, phone_num, age, status, default_doctor)\n" +
                                                            "	VALUES (?, ?, ?, ?, ?, \"Healthy\", ?)");
            pst.setInt(1, Integer.parseInt(request.getParameter("SIN")));
            pst.setInt(2, Integer.parseInt(request.getParameter("HCN")));
            pst.setString(3, request.getParameter("name"));
            pst.setLong(4, Long.parseLong(request.getParameter("phone")));
            pst.setInt(5, Integer.parseInt(request.getParameter("age")));
            pst.setInt(6, Integer.parseInt(request.getParameter("def_doc")));
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
        catch(Exception e)
        {
            request.setAttribute("exception", e);
            url = "/error.jsp";
            request.getServletContext().getRequestDispatcher(url).forward(request, response);
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
