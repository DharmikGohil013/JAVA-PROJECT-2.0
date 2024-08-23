package utils;
import java.sql.*;
import java.util.Scanner;

public class PersonalMessageViewer {

    // Method for students to view their personal messages
    public static void viewPersonalMessages(String studentEmail) {
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
