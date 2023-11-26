package com.workshop;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UserRegistrationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String location = request.getParameter("location");
        String status = request.getParameter("status");

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Replace "jdbc:mysql://localhost:3306/workshop" with the IP address and database name
            String jdbcUrl = "jdbc:postgresql://13.233.230.128:5432/workshop";
            String username = "myuser"; // MySQL root user
            String password = "mypassword"; // MySQL root password

            Connection con = DriverManager.getConnection(jdbcUrl, username, password);

            String query = "INSERT INTO users (name, email, location, status) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, location);
            ps.setString(4, status);

            // Execute the SQL query to insert user data into the database
            int rowsAffected = ps.executeUpdate();

            ps.close();
            con.close();

            if (rowsAffected > 0) {
                // Data successfully inserted
                response.sendRedirect("success.html");
            } else {
                // Insertion failed
                response.sendRedirect("error.html");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}
