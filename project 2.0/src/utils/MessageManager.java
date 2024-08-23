package utils;
import java.sql.*;
import java.util.Scanner;

public class MessageManager {

    // Method to send a message from faculty to student
    public static void sendMessage() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your email (faculty): ");
        String senderEmail = scanner.nextLine();

        System.out.print("Enter recipient email (student): ");
        String receiverEmail = scanner.nextLine();

        System.out.print("Enter message content: ");
        String messageContent = scanner.nextLine();

        // Get the current date for the message
        Date sentDate = new Date(System.currentTimeMillis());

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO messages (sender_email, receiver_email, message_content, sent_date) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, senderEmail);
            stmt.setString(2, receiverEmail);
            stmt.setString(3, messageContent);
            stmt.setDate(4, sentDate);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Message sent successfully!");
            } else {
                System.out.println("Failed to send message.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
