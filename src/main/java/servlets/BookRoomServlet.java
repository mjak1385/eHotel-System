package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/bookRoom")
public class BookRoomServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Grab the room ID from the URL (e.g., ?id=5)
        String roomId = request.getParameter("id");
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html><html><head><title>e-Hotels | Book Room</title>");
        out.println("<link rel='stylesheet' type='text/css' href='style.css'></head><body>");
        
        out.println("<header><h1>e-Hotels</h1><p>Secure Your Reservation</p></header>");
        
        out.println("<div class='container'><div class='card'>");
        out.println("<h2>Book Room #" + roomId + "</h2>");
        
        out.println("<form action='processBooking' method='POST'>");
        out.println("<input type='hidden' name='roomId' value='" + roomId + "'>");
        
        out.println("<label>Your Customer ID:</label>");
        out.println("<input type='number' name='customerId' required>");
        
        out.println("<label>Check-in Date:</label>");
        out.println("<input type='date' name='startDate' required>");
        
        out.println("<label>Check-out Date:</label>");
        out.println("<input type='date' name='endDate' required>");
        
        out.println("<input type='submit' value='Confirm Booking'>");
        out.println("</form>");
        
        out.println("<br><a href='index.html'>Cancel & Return to Search</a>");
        out.println("</div></div></body></html>");
    }
}