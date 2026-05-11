package servlets;

import utils.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/viewDashboard")
public class ViewsDashboardServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html><head><title>e-Hotels | Reports</title></head><body>");
        out.println("<h1>Management Reports Dashboard</h1>");
        out.println("<a href='employee.html'>Back to Employee Portal</a><hr>");
        
        try (Connection conn = DBConnection.getConnection()) {
            
            // --- VIEW 1: Available Rooms Per Area ---
            out.println("<h3>View 1: Available Rooms Per Area (Today)</h3>");
            String sqlView1 = "SELECT area, available_rooms FROM Available_Rooms_Per_Area";
            PreparedStatement stmt1 = conn.prepareStatement(sqlView1);
            ResultSet rs1 = stmt1.executeQuery();
            
            out.println("<table border='1'><tr><th>Hotel Address (Area)</th><th>Available Rooms</th></tr>");
            while (rs1.next()) {
                out.println("<tr>");
                out.println("<td>" + rs1.getString("area") + "</td>");
                out.println("<td>" + rs1.getInt("available_rooms") + "</td>");
                out.println("</tr>");
            }
            out.println("</table><br><br>");
            
            // --- VIEW 2: Aggregated Capacity per Hotel ---
            out.println("<h3>View 2: Aggregated Capacity by Hotel</h3>");
            String sqlView2 = "SELECT hotel_address, total_capacity FROM Hotel_Aggregated_Capacity";
            PreparedStatement stmt2 = conn.prepareStatement(sqlView2);
            ResultSet rs2 = stmt2.executeQuery();
            
            out.println("<table border='1'><tr><th>Hotel Address</th><th>Total Guest Capacity</th></tr>");
            while (rs2.next()) {
                out.println("<tr>");
                out.println("<td>" + rs2.getString("hotel_address") + "</td>");
                out.println("<td>" + rs2.getInt("total_capacity") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            
        } catch (Exception e) {
            out.println("<h3>Database Error: " + e.getMessage() + "</h3>");
        }
        
        out.println("</body></html>");
    }
}