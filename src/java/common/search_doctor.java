/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

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
public class search_doctor extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "        <title>Doctor Search</title>");            
            out.println("</head>");
            out.println("<body>");
                if(request.getSession().getAttribute("CurrentStaff") != null || request.getSession().getAttribute("CurrentDoctor") != null)
                {
                    out.println("<form action=\"\" method=\"POST\">\n" +
                                "Search for doctor: <input type=\"text\" name=\"doc_search_name\" required=\"required\" placeholder=\"Eg. John\"/><p>\n" +
                                "<input type=\"submit\" value=\"Search\">\n" +
                                "</form>");
                    if(request.getParameter("doc_search_name") != null)
                    {
                        String url;
                        String db_url = "jdbc:mysql://localhost:3306/project";
                        String db_user = "testuser";
                        String db_pwd = "test623";

                        try
                        {
                            Class.forName("com.mysql.jdbc.Driver");
                            Connection con = DriverManager.getConnection(db_url, db_user, db_pwd);
                            PreparedStatement pst = con.prepareStatement("SELECT * FROM doctor_info WHERE name like ?");
                            pst.setString(1, "%"+request.getParameter("doc_search_name")+"%");
                            ResultSet result = pst.executeQuery();
                            
                            out.println("<TABLE BORDER=\"1\">\n" +
                                        "    <TR>\n" +
                                        "        <TH>ID</TH>\n" +
                                        "        <TH>Name</TH>\n" +
                                        "    </TR>");
                            while(result.next())
                            {
                                out.println("   <TR>"
                                        + "         <TH>"+result.getInt("doctor_num")+"</TH>"
                                        + "         <TH>"+result.getString("name")+ "</TH>"
                                        + "     </TR>");
                            }
                            out.println("</TABLE>");
                            if (con != null) 
                                con.close();
                        }
                        catch(Exception e)
                        {
                            request.setAttribute("exception", e);
                            url = "/error.jsp";
                            request.getServletContext().getRequestDispatcher(url).forward(request, response);
                    
                        }
                    }
                }
            out.println("</body>");
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
