package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres"; 
    private static final String USER = "postgres"; 
    private static final String PASSWORD = "mjak.1385";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // Load the PostgreSQL driver
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}