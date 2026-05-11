package servlets;

import utils.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/searchRooms")
public class RoomSearchServlet extends HttpServlet {
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String area = request.getParameter("area");
        String capacity = request.getParameter("capacity");
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html><html><head><title>e-Hotels | Search Results</title>");
        out.println("<link rel='stylesheet' type='text/css' href='style.css'></head><body>");
        out.println("<header><h1>e-Hotels</h1><p>Available Rooms in " + area + "</p></header>");
        out.println("<div class='container'><div class='card'>");
        
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT r.room_id, r.price, h.address, h.stars " +
                         "FROM Room r JOIN Hotel h ON r.hotel_id = h.hotel_id " +
                         "WHERE h.address ILIKE ? AND r.capacity = ?";
                         
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + area + "%");
            stmt.setInt(2, Integer.parseInt(capacity));
            
            ResultSet rs = stmt.executeQuery();
            
            out.println("<h2>Search Results</h2>");
            out.println("<table><tr><th>Room ID</th><th>Price</th><th>Location</th><th>Stars</th><th>Action</th></tr>");
            
            boolean foundRooms = false;
            while(rs.next()) {
                foundRooms = true;
                out.println("<tr>");
                out.println("<td>" + rs.getInt("room_id") + "</td>");
                out.println("<td>$" + rs.getBigDecimal("price") + "</td>");
                out.println("<td>" + rs.getString("address") + "</td>");
                out.println("<td>" + rs.getInt("stars") + " ★</td>");
                out.println("<td><a href='bookRoom?id=" + rs.getInt("room_id") + "'>Book Now</a></td>");
                out.println("</tr>");
            }
            out.println("</table>");
            
            if (!foundRooms) {
                out.println("<p>Sorry, no rooms match your search criteria.</p>");
            }
            
        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
        
        out.println("<br><a href='index.html'>Search Again</a>");
        out.println("</div></div></body></html>");
    }
}