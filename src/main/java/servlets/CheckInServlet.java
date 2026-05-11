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

@WebServlet("/processCheckIn")
public class CheckInServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        int employeeId = Integer.parseInt(request.getParameter("employeeId"));
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try (Connection conn = DBConnection.getConnection()) {
            // Step 1: Retrieve the booking details
            String getBookingSql = "SELECT customer_id, room_id, start_date, end_date FROM Booking WHERE booking_id = ?";
            PreparedStatement getStmt = conn.prepareStatement(getBookingSql);
            getStmt.setInt(1, bookingId);
            ResultSet rs = getStmt.executeQuery();
            
            if (rs.next()) {
                int customerId = rs.getInt("customer_id");
                int roomId = rs.getInt("room_id");
                java.sql.Date startDate = rs.getDate("start_date");
                java.sql.Date endDate = rs.getDate("end_date");
                
                // Step 2: Insert into Renting table to formalize the check-in
                String insertRentingSql = "INSERT INTO Renting (booking_id, customer_id, room_id, employee_id, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertRentingSql);
                insertStmt.setInt(1, bookingId);
                insertStmt.setInt(2, customerId);
                insertStmt.setInt(3, roomId);
                insertStmt.setInt(4, employeeId);
                insertStmt.setDate(5, startDate);
                insertStmt.setDate(6, endDate);
                
                int rowsAffected = insertStmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    out.println("<h2>Check-in Successful!</h2>");
                    out.println("<p>Booking #" + bookingId + " has been transformed into an active renting.</p>");
                    out.println("<a href='employee.html'>Return to Dashboard</a>");
                } else {
                    out.println("<h2>Error: Could not process check-in.</h2>");
                }
            } else {
                out.println("<h2>Error: Booking ID not found.</h2>");
            }
            
        } catch (Exception e) {
            out.println("<h3>Database Error: " + e.getMessage() + "</h3>");
        }
    }
}