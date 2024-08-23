package utils;
import java.sql.*;
import java.util.Scanner;

public class ResultViewer {

    // Method to view results for a specific student
    public static void viewResults(String studentEmail) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Semester to view results: ");
        int semester = scanner.nextInt();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT semester, result FROM results WHERE email_id = ? AND semester = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, studentEmail);
            stmt.setInt(2, semester);

            ResultSet rs = stmt.executeQuery();

            System.out.println("Results for " + studentEmail + " in Semester " + semester + ":");
            System.out.println("Semester | Result");
            System.out.println("-------------------");

            boolean hasResults = false;
            while (rs.next()) {
                String result = rs.getString("result");

                System.out.println(semester + " | " + result);
                hasResults = true;
            }

            if (!hasResults) {
                System.out.println("No results available for this semester.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
