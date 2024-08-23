package utils;
import java.sql.*;
import java.util.Scanner;

public class ClassUpdateManager {

    // Method to add class updates (accessible by CR only)
    public static void addClassUpdate() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Class Name (IT, CE, CS, CD): ");
        String className = scanner.nextLine().toUpperCase();

        System.out.print("Enter Update Details: ");
        String updateDetails = scanner.nextLine();

        // Get the current date for the update
        Date updateDate = new Date(System.currentTimeMillis());

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO class_updates (class_name, update_details, update_date) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, className);
            stmt.setString(2, updateDetails);
            stmt.setDate(3, updateDate);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Class update added successfully!");
            } else {
                System.out.println("Failed to add class update.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
