package utils;
import java.sql.*;
import java.util.Scanner;

public class MaterialViewer {

    // Method to view materials for a specific class
    public static void viewMaterials(String className) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT title, link, upload_date FROM materials WHERE class_name = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, className);

            ResultSet rs = stmt.executeQuery();

            System.out.println("Materials for " + className + " class:");
            System.out.println("Title                           | Link                        | Upload Date");
            System.out.println("--------------------------------------------------------------------------");

            boolean hasMaterials = false;
            while (rs.next()) {
                String title = rs.getString("title");
                String link = rs.getString("link");
                Date uploadDate = rs.getDate("upload_date");

                System.out.println(title + " | " + link + " | " + uploadDate);
                hasMaterials = true;
            }

            if (!hasMaterials) {
                System.out.println("No materials available for this class.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
