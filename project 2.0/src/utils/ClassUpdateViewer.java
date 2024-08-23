package utils;
import java.sql.*;

public class ClassUpdateViewer {

    // Method to view class updates
    public static void viewClassUpdates(String className) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT update_details, update_date FROM class_updates WHERE class_name = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, className);

            ResultSet rs = stmt.executeQuery();

            System.out.println("Updates for " + className + " class:");
            System.out.println("Update Details                          | Update Date");
            System.out.println("----------------------------------------------------");

            boolean hasUpdates = false;
            while (rs.next()) {
                String updateDetails = rs.getString("update_details");
                Date updateDate = rs.getDate("update_date");

                System.out.println(updateDetails + " | " + updateDate);
                hasUpdates = true;
            }

            if (!hasUpdates) {
                System.out.println("No updates available for this class.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
