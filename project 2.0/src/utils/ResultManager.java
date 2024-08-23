package utils;
import java.sql.*;
import java.util.Scanner;

public class ResultManager {

    // Method to add results (accessible by Faculty only)
    public static void addResult() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Student Email: ");
        String emailId = scanner.nextLine();

        System.out.print("Enter Semester: ");
        int semester = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Result (Grade): ");
        String result = scanner.nextLine();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO results (email_id, semester, result) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, emailId);
            stmt.setInt(2, semester);
            stmt.setString(3, result);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Result added successfully!");
            } else {
                System.out.println("Failed to add result.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
