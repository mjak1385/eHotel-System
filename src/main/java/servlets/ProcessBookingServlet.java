// File: src/servlets/ProcessBookingServlet.java
package servlets;

import utils.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/processBooking")
public class ProcessBookingServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int roomId = Integer.parseInt(request.getParameter("roomId"));
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        java.sql.Date startDate = java.sql.Date.valueOf(request.getParameter("startDate"));
        java.sql.Date endDate = java.sql.Date.valueOf(request.getParameter("endDate"));
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html><html><head><title>Booking Status</title>");
        out.println("<link rel='stylesheet' type='text/css' href='style.css'></head><body>");
        out.println("<header><h1>e-Hotels</h1><p>Reservation Status</p></header>");
        out.println("<div class='container'><div class='card'>");
        
        try (Connection conn = DBConnection.getConnection()) {
            // Insert the booking into the database
            String sql = "INSERT INTO Booking (customer_id, room_id, start_date, end_date) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customerId);
            stmt.setInt(2, roomId);
            stmt.setDate(3, startDate);
            stmt.setDate(4, endDate);
            
            stmt.executeUpdate();
            
            out.println("<h2 style='color: green;'>Booking Successful!</h2>");
            out.println("<p>Your room has been reserved from " + startDate + " to " + endDate + ".</p>");
            
        } catch (Exception e) {
            out.println("<h2 style='color: red;'>Booking Failed</h2>");
            out.println("<p><strong>Error:</strong> " + e.getMessage() + "</p>");
            out.println("<p>The room might already be booked for these dates, or the Customer ID is invalid.</p>");
        }
        
        out.println("<br><a href='index.html'>Return to Home</a>");
        out.println("</div></div></body></html>");
    }
}