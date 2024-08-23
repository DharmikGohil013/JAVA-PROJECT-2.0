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
            System.out.println("11. View Upadets");
            System.out.println("12. Add Result With Same");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1 : {
                    viewProfile(email);
                    
                }
                case 2 : showSalary();continue;
                case 3 : showAllStudentDetails();continue;
                case 4 : markAttendanceByEmail();continue;
                case 5 : {
                    System.out.print("Enter your email to view attendance: ");
    String studentEmail = scanner.nextLine();
    viewAttendanceForStudent(studentEmail);
    continue;
                }
                case 6 : MaterialManager.addMaterial();continue;
                case 7 :  MessageManager.sendMessage();continue;
                case 8 : {
                    System.out.println("Logging out...");
                    return;
                }
                case 9 : {
                    System.out.println("Exiting program...");
                    System.exit(0);
                }
                case 10 : viewAllEvents();continue;
                case 11 : viewAllUniversityUpdates();continue;
                case 12 : 
                {
                    ResultManager.addResult();
                    continue;
                }
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

    private static void markAttendanceByEmail() {
        Scanner scanner = new Scanner(System.in);
    
        System.out.print("Enter Student Email: ");
        String studentEmail = scanner.nextLine();
    
        System.out.print("Enter Attendance Date (YYYY-MM-DD): ");
        String attendanceDate = scanner.nextLine();
    
        System.out.print("Enter Attendance Status (Present/Absent): ");
        String status = scanner.nextLine();
    
        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO attendance (student_email, attendance_date, status) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, studentEmail);
            stmt.setDate(2, java.sql.Date.valueOf(attendanceDate));
            stmt.setString(3, status);
    
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Attendance marked successfully!");
            } else {
                System.out.println("Failed to mark attendance.");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    

    public static void viewAttendanceForStudent(String studentEmail) {
        try (Connection conn = DBConnection.getConnection()) {
            // SQL query to fetch attendance records for the given student email
            String query = "SELECT attendance_date, status FROM attendance WHERE student_email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, studentEmail);

            // Execute query
            ResultSet rs = stmt.executeQuery();

            // Display the attendance records
            System.out.println("Attendance Records for " + studentEmail + ":");
            System.out.println("Date       | Status");
            System.out.println("--------------------");

            boolean hasRecords = false;
            while (rs.next()) {
                java.sql.Date attendanceDate = rs.getDate("attendance_date");
                String status = rs.getString("status");

                System.out.println(attendanceDate + " | " + status);
                hasRecords = true;
            }

            if (!hasRecords) {
                System.out.println("No attendance records found for this student.");
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
    private static void viewAllEvents() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM event";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
    
            System.out.println("Event ID | Event Name | Event Date | Description | Department");
            System.out.println("---------------------------------------------------------------");
    
            while (rs.next()) {
                int eventId = rs.getInt("event_id");
                String eventName = rs.getString("event_name");
                java.sql.Date eventDate = rs.getDate("event_date");  // Use java.sql.Date to match SQL date format
                String eventDescription = rs.getString("event_description");
                String department = rs.getString("department");
    
                System.out.println(eventId + " | " + eventName + " | " + eventDate + " | " + eventDescription + " | " + (department == null ? "All" : department));
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void viewAllUniversityUpdates() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM university_updates";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
    
            System.out.println("Update ID | Title | Date | Content");
            System.out.println("---------------------------------------------------------------");
    
            while (rs.next()) {
                int updateId = rs.getInt("update_id");
                String updateTitle = rs.getString("update_title");
                java.sql.Date updateDate = rs.getDate("update_date");
                String updateContent = rs.getString("update_content");
    
                System.out.println(updateId + " | " + updateTitle + " | " + updateDate + " | " + updateContent);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
