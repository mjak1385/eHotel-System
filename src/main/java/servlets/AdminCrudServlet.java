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

@WebServlet("/adminCrud")
public class AdminCrudServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Operation Result</title></head><body>");
        
        try (Connection conn = DBConnection.getConnection()) {
            int rowsAffected = 0;
            
            switch (action) {
                case "insert_customer":
                    String insertSql = "INSERT INTO Customer (full_name, address, id_type, id_number, registration_date) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                    insertStmt.setString(1, request.getParameter("fullName"));
                    insertStmt.setString(2, request.getParameter("address"));
                    insertStmt.setString(3, request.getParameter("idType"));
                    insertStmt.setString(4, request.getParameter("idNumber"));
                    insertStmt.setDate(5, java.sql.Date.valueOf(request.getParameter("regDate")));
                    rowsAffected = insertStmt.executeUpdate();
                    out.println("<h2>Success: Customer Added.</h2>");
                    break;
                    
                case "update_customer":
                    String updateSql = "UPDATE Customer SET address = ? WHERE customer_id = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                    updateStmt.setString(1, request.getParameter("newAddress"));
                    updateStmt.setInt(2, Integer.parseInt(request.getParameter("customerId")));
                    rowsAffected = updateStmt.executeUpdate();
                    out.println("<h2>Success: Customer Address Updated.</h2>");
                    break;
                    
                case "delete_customer":
                    String deleteSql = "DELETE FROM Customer WHERE customer_id = ?";
                    PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
                    deleteStmt.setInt(1, Integer.parseInt(request.getParameter("customerId")));
                    rowsAffected = deleteStmt.executeUpdate();
                    out.println("<h2>Success: Customer Deleted.</h2>");
                    break;
                 // --- ROOM CRUD ---
                case "insert_room":
                    String insertRoomSql = "INSERT INTO Room (hotel_id, price, capacity, view_type, amenities, is_extendable, problems_status) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement insertRoomStmt = conn.prepareStatement(insertRoomSql);
                    insertRoomStmt.setInt(1, Integer.parseInt(request.getParameter("hotelId")));
                    insertRoomStmt.setDouble(2, Double.parseDouble(request.getParameter("price")));
                    insertRoomStmt.setInt(3, Integer.parseInt(request.getParameter("capacity")));
                    insertRoomStmt.setString(4, request.getParameter("viewType"));
                    insertRoomStmt.setString(5, request.getParameter("amenities"));
                    insertRoomStmt.setBoolean(6, Boolean.parseBoolean(request.getParameter("isExtendable")));
                    insertRoomStmt.setString(7, request.getParameter("problems"));
                    rowsAffected = insertRoomStmt.executeUpdate();
                    out.println("<h2>Success: Room Added.</h2>");
                    break;
                    
                case "update_room":
                    String updateRoomSql = "UPDATE Room SET price = ? WHERE room_id = ?";
                    PreparedStatement updateRoomStmt = conn.prepareStatement(updateRoomSql);
                    updateRoomStmt.setDouble(1, Double.parseDouble(request.getParameter("newPrice")));
                    updateRoomStmt.setInt(2, Integer.parseInt(request.getParameter("roomId")));
                    rowsAffected = updateRoomStmt.executeUpdate();
                    out.println("<h2>Success: Room Price Updated.</h2>");
                    break;
                    
                case "delete_room":
                    String deleteRoomSql = "DELETE FROM Room WHERE room_id = ?";
                    PreparedStatement deleteRoomStmt = conn.prepareStatement(deleteRoomSql);
                    deleteRoomStmt.setInt(1, Integer.parseInt(request.getParameter("roomId")));
                    rowsAffected = deleteRoomStmt.executeUpdate();
                    out.println("<h2>Success: Room Deleted.</h2>");
                    break;

                // --- HOTEL CRUD ---
                case "insert_hotel":
                    String insertHotelSql = "INSERT INTO Hotel (chain_id, stars, num_rooms, address) VALUES (?, ?, ?, ?)";
                    PreparedStatement insertHotelStmt = conn.prepareStatement(insertHotelSql);
                    insertHotelStmt.setInt(1, Integer.parseInt(request.getParameter("chainId")));
                    insertHotelStmt.setInt(2, Integer.parseInt(request.getParameter("stars")));
                    insertHotelStmt.setInt(3, Integer.parseInt(request.getParameter("numRooms")));
                    insertHotelStmt.setString(4, request.getParameter("address"));
                    rowsAffected = insertHotelStmt.executeUpdate();
                    out.println("<h2>Success: Hotel Added.</h2>");
                    break;
                    
                case "update_hotel":
                    String updateHotelSql = "UPDATE Hotel SET stars = ? WHERE hotel_id = ?";
                    PreparedStatement updateHotelStmt = conn.prepareStatement(updateHotelSql);
                    updateHotelStmt.setInt(1, Integer.parseInt(request.getParameter("newStars")));
                    updateHotelStmt.setInt(2, Integer.parseInt(request.getParameter("hotelId")));
                    rowsAffected = updateHotelStmt.executeUpdate();
                    out.println("<h2>Success: Hotel Star Rating Updated.</h2>");
                    break;
                    
                case "delete_hotel":
                    String deleteHotelSql = "DELETE FROM Hotel WHERE hotel_id = ?";
                    PreparedStatement deleteHotelStmt = conn.prepareStatement(deleteHotelSql);
                    deleteHotelStmt.setInt(1, Integer.parseInt(request.getParameter("hotelId")));
                    rowsAffected = deleteHotelStmt.executeUpdate();
                    out.println("<h2>Success: Hotel Deleted.</h2>");
                    break;

                // --- EMPLOYEE CRUD ---
                case "insert_employee":
                    String insertEmpSql = "INSERT INTO Employee (hotel_id, full_name, address, ssn_sin, role) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement insertEmpStmt = conn.prepareStatement(insertEmpSql);
                    insertEmpStmt.setInt(1, Integer.parseInt(request.getParameter("hotelId")));
                    insertEmpStmt.setString(2, request.getParameter("fullName"));
                    insertEmpStmt.setString(3, request.getParameter("address"));
                    insertEmpStmt.setString(4, request.getParameter("ssn"));
                    insertEmpStmt.setString(5, request.getParameter("role"));
                    rowsAffected = insertEmpStmt.executeUpdate();
                    out.println("<h2>Success: Employee Added.</h2>");
                    break;
                    
                case "update_employee":
                    String updateEmpSql = "UPDATE Employee SET role = ? WHERE employee_id = ?";
                    PreparedStatement updateEmpStmt = conn.prepareStatement(updateEmpSql);
                    updateEmpStmt.setString(1, request.getParameter("newRole"));
                    updateEmpStmt.setInt(2, Integer.parseInt(request.getParameter("employeeId")));
                    rowsAffected = updateEmpStmt.executeUpdate();
                    out.println("<h2>Success: Employee Role Updated.</h2>");
                    break;
                    
                case "delete_employee":
                    String deleteEmpSql = "DELETE FROM Employee WHERE employee_id = ?";
                    PreparedStatement deleteEmpStmt = conn.prepareStatement(deleteEmpSql);
                    deleteEmpStmt.setInt(1, Integer.parseInt(request.getParameter("employeeId")));
                    rowsAffected = deleteEmpStmt.executeUpdate();
                    out.println("<h2>Success: Employee Deleted.</h2>");
                    break;
                
                default:
                    out.println("<h2>Error: Unknown Action.</h2>");
            }
            
            if (rowsAffected == 0 && !action.startsWith("insert")) {
                 out.println("<p>Notice: No records were changed. Check if the ID exists.</p>");
            }
            
        } catch (Exception e) {
            out.println("<h3>Database Error: " + e.getMessage() + "</h3>");
        }
        
        out.println("<br><a href='admin.html'>Return to Admin Dashboard</a>");
        out.println("</body></html>");
    }
}