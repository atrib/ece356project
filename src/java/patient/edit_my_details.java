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
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author atri
 */
public class edit_my_details extends HttpServlet {

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
        Connection con;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(db_url, db_user, db_pwd);
            con.prepareStatement("LOCK TABLES patient_info WRITE, phone_number_map WRITE").execute();
            PreparedStatement pst;
            PatientData currentPatient;
            long old_phone = 0;
            currentPatient=(PatientData)(request.getSession().getAttribute("CurrentPatient"));
            int SIN =0;
            if(currentPatient != null)
            {
                SIN = currentPatient.getSIN();
                old_phone = currentPatient.getPhone();
            }
            else
            { 
                SIN = Integer.parseInt(request.getParameter("patient_SIN"));
                pst = con.prepareStatement("SELECT phone_num FROM patient_info WHERE SocialIN = ?");
                pst.setInt(1, SIN);
                ResultSet r = pst.executeQuery();
                if(r.first()!= false)
                    old_phone = r.getLong("phone_num");
                else
                    throw(new SQLException("Bad SIN specified"));
            }   
            
            
                
            
            
            String query = "UPDATE patient_info SET ";
            if(request.getParameter("HCN").equals("") == false)
            {
                pst = con.prepareStatement(query + "healthC_num = ? WHERE SocialIN = ?");
                pst.setInt(1, Integer.parseInt(request.getParameter("HCN")));
                pst.setInt(2, SIN);
                pst.executeUpdate();
            }

            if(request.getParameter("age").equals("") == false)
            {
                pst = con.prepareStatement(query + "age = ?" + "WHERE SocialIN = ?");
                pst.setInt(1, Integer.parseInt(request.getParameter("age")));
                pst.setInt(2, SIN);
                pst.executeUpdate();
            }  
            
            String address = request.getParameter("address");
            long new_phone=0;
            if(request.getParameter("phone").equals("") == false)
                new_phone = Long.parseLong(request.getParameter("phone"));
            
            if(address.equals("") == false && new_phone == 0)
            {
                pst = con.prepareStatement("UPDATE phone_number_map SET address = ? WHERE phone_num = ?");
                pst.setString(1, address);
                pst.setLong(2, currentPatient.getPhone());
                pst.executeUpdate();
            }
            else if(address.equals("") == false && new_phone != 0)
            {
                pst = con.prepareStatement("INSERT into phone_number_map(phone_num, address) value (?, ?) on duplicate key update address=?");
                pst.setLong(1, new_phone);
                pst.setString(2, address);
                pst.setString(3, address);
                pst.executeUpdate();
                pst = con.prepareStatement("UPDATE patient_info SET phone_num = ? WHERE SocialIN = ?");
                pst.setLong(1, new_phone);
                pst.setInt(2, SIN);
                pst.executeUpdate();
                pst = con.prepareStatement("DELETE FROM phone_number_map WHERE phone_num = ?");
                pst.setLong(1, old_phone);
                pst.executeUpdate();
                currentPatient.setPhone(new_phone);
            }
            else if(address.equals("") == true && new_phone != 0)
            {
                pst = con.prepareStatement("SELECT address FROM phone_number_map WHERE phone_num = ?");
                pst.setLong(1, old_phone);
                ResultSet result = pst.executeQuery();
                if(result.first() == true)
                {
                    address = result.getString(1);
                    pst = con.prepareStatement("INSERT into phone_number_map(phone_num, address) value (?, ?) on duplicate key update address=?");
                    pst.setLong(1, new_phone);
                    pst.setString(2, address);
                    pst.setString(3, address);
                    pst.executeUpdate();
                    pst = con.prepareStatement("UPDATE patient_info SET phone_num = ? WHERE SocialIN = ?");
                    pst.setLong(1, new_phone);
                    pst.setInt(2, SIN);
                    pst.executeUpdate();
                    pst = con.prepareStatement("DELETE FROM phone_number_map WHERE phone_num = ?");
                    pst.setLong(1, old_phone);
                    pst.executeUpdate();
                    currentPatient.setPhone(new_phone);
                }
            }
            con.prepareStatement("UNLOCK TABLES ").execute();
            if(con!= null)
                con.close();
        }
        catch(NumberFormatException e)
        {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<body>");
            out.println("<script>No SIN specified</script>");
            out.println("</body>");
            out.println("</html>");
            
        }
        catch(Exception e)
        {
            request.setAttribute("exception", e);
           
            request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
        }
        
        
        
        
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<body>");
            out.println("<script>window.close();</script>");
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
