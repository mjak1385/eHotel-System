package servlets;

import utils.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/processPayment")
public class PaymentServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int rentingId = Integer.parseInt(request.getParameter("rentingId"));
        double amount = Double.parseDouble(request.getParameter("amount"));
        String method = request.getParameter("method");
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try (Connection conn = DBConnection.getConnection()) {
            
            String sql = "INSERT INTO Payment (renting_id, amount, payment_method) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1, rentingId);
            stmt.setDouble(2, amount);
            stmt.setString(3, method);
            
            int rowsAffected = stmt.executeUpdate();
            
            out.println("<html><head><title>Payment Status</title></head><body>");
            if (rowsAffected > 0) {
                out.println("<h2>Payment Successful!</h2>");
                out.println("<p>Successfully recorded a payment of $" + amount + " for Renting #" + rentingId + ".</p>");
            } else {
                out.println("<h2>Error: Payment could not be processed.</h2>");
                out.println("<p>Please verify the Renting ID is correct and active.</p>");
            }
            out.println("<br><a href='employee.html'>Return to Dashboard</a>");
            out.println("</body></html>");
            
        } catch (Exception e) {
            out.println("<h3>Database Error: " + e.getMessage() + "</h3>");
            out.println("<br><a href='employee.html'>Return to Dashboard</a>");
        }
    }
}