// Faculty.java

package utils;

import java.sql.*;
import java.util.Scanner;

public class Faculty {

    public static void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Faculty Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (authenticateFaculty(email, password)) {
            System.out.println("Login successful!");
            facultyMenu(email);
        } else {
            System.out.println("Invalid credentials. Try again.");
        }
    }

    private static boolean authenticateFaculty(String email, String password) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM faculty WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // If a record is found, credentials are valid
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private static void facultyMenu(String email) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            
            System.out.println("\nFaculty Menu:");
            System.out.println("1. View Profile");
            System.out.println("2. Salary Details");
            System.out.println("3. All Student Details");
            System.out.println("4. Manage Attendance");
            System.out.println("5. Show Attendance for a Student");
            System.out.println("6. Add Material Link");
            System.out.println("7. Send Personal Message to a Student");
            System.out.println("8. Logout");
            System.out.println("9. Exit");
            System.out.println("10. View Events");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1 : {
                    viewProfile(email);
                    
                }
                case 2 : showSalary();
                case 3 : showAllStudentDetails();
                case 4 : manageAttendance();
                case 5 : showAttendanceForStudent();
                case 6 : addMaterialLink();
                case 7 : sendPersonalMessage();
                case 8 : {
                    System.out.println("Logging out...");
                    return;
                }
                case 9 : {
                    System.out.println("Exiting program...");
                    System.exit(0);
                }
                case 10 : viewEvents();
                default : System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void viewProfile(String email) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM faculty WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Mobile Number: " + rs.getString("mo_number"));
                System.out.println("Department: " + rs.getString("dept"));
                System.out.println("Role: " + rs.getString("rol"));
            } else {
                System.out.println("Faculty profile not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showSalary() {
        // This feature is under construction. You can implement salary details logic here.
        System.out.println("Salary details feature is coming soon!");
    }

    private static void showAllStudentDetails() {
        try (Connection conn = DBConnection.getConnection()) {
            // Assuming that faculty can view students from their department
            String query = "SELECT * FROM student_it UNION SELECT * FROM student_ce UNION SELECT * FROM student_cs UNION SELECT * FROM student_cd";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Mobile Number: " + rs.getString("mo_number"));
                System.out.println("Department: " + rs.getString("dept"));
                System.out.println("Role: " + rs.getString("rol"));
                System.out.println("------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void manageAttendance() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Student Email to Mark Attendance: ");
        String email = scanner.nextLine();
        System.out.print("Enter Date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter Attendance Status (1 for Present, 0 for Absent): ");
        int status = scanner.nextInt();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO attendance (student_email, date, status) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, date);
            stmt.setInt(3, status);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Attendance marked successfully.");
            } else {
                System.out.println("Error marking attendance.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showAttendanceForStudent() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Student Email to View Attendance: ");
        String email = scanner.nextLine();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM attendance WHERE student_email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("Date: " + rs.getString("date"));
                System.out.println("Status: " + (rs.getInt("status") == 1 ? "Present" : "Absent"));
                System.out.println("------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addMaterialLink() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Material Link: ");
        String link = scanner.nextLine();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO materials (link) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, link);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Material link added successfully.");
            } else {
                System.out.println("Error adding material link.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void sendPersonalMessage() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Student Email: ");
        String studentEmail = scanner.nextLine();
        System.out.print("Enter Message: ");
        String message = scanner.nextLine();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO messages (student_email, message) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, studentEmail);
            stmt.setString(2, message);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Message sent successfully.");
            } else {
                System.out.println("Error sending message.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewEvents() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM events";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("Event ID: " + rs.getInt("id"));
                System.out.println("Description: " + rs.getString("description"));
                System.out.println("------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
