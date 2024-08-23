package utils;

import java.sql.*;
import java.util.Scanner;

public class StudentApp {

    // Method to handle student login and message display
    public static void main(String[] args) {
        // Login to capture email
        String studentEmail = StudentLogin.login();

        // Display personal messages for the logged-in student
        viewPersonalMessages(studentEmail);
    }

    // Method for students to view their personal messages
    private static void viewPersonalMessages(String studentEmail) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT sender_email, message_content, sent_date FROM messages WHERE receiver_email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, studentEmail);

            ResultSet rs = stmt.executeQuery();

            System.out.println("Personal Messages for " + studentEmail + ":");
            System.out.println("Sender | Message Content                          | Sent Date");
            System.out.println("---------------------------------------------------------------");

            boolean hasMessages = false;
            while (rs.next()) {
                String senderEmail = rs.getString("sender_email");
                String messageContent = rs.getString("message_content");
                Date sentDate = rs.getDate("sent_date");

                System.out.println(senderEmail + " | " + messageContent + " | " + sentDate);
                hasMessages = true;
            }

            if (!hasMessages) {
                System.out.println("No personal messages available.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
