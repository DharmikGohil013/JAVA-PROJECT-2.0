package utils;
import java.sql.*;
import java.util.Scanner;

public class MaterialManager {

    // Method for CR to add materials
    public static void addMaterial() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Class Name (IT, CE, CS, CD): ");
        String className = scanner.nextLine().toUpperCase();

        System.out.print("Enter Material Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Material Link (URL): ");
        String link = scanner.nextLine();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO materials (class_name, title, link) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, className);
            stmt.setString(2, title);
            stmt.setString(3, link);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Material added successfully!");
            } else {
                System.out.println("Failed to add material.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
