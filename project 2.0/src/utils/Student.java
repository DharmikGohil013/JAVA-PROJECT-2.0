// Student.java

package utils;

import java.sql.*;
import java.util.Scanner;

public class Student 
{

    public static void login() 
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Student Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (authenticateStudent(email, password)) {
            System.out.println("Login successful!");
            studentMenu(email);
        }
    }
        private static boolean authenticateStudent(String email, String password) {
            try (Connection conn = DBConnection.getConnection()) {
                String query = "SELECT * FROM student_it WHERE email = ? AND password = ? UNION " +
                               "SELECT * FROM student_ce WHERE email = ? AND password = ? UNION " +
                               "SELECT * FROM student_cs WHERE email = ? AND password = ? UNION " +
                               "SELECT * FROM student_cd WHERE email = ? AND password = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, email);
                stmt.setString(2, password);
                stmt.setString(3, email);
                stmt.setString(4, password);
                stmt.setString(5, email);
                stmt.setString(6, password);
                stmt.setString(7, email);
                stmt.setString(8, password);
                ResultSet rs = stmt.executeQuery();
                return rs.next(); // If a record is found, credentials are valid
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        }
    
    
        private static void studentMenu(String email) 
        {
            Scanner scanner = new Scanner(System.in);
    
            while (true) 
            {
                System.out.println("\nStudent Menu:");
                System.out.println("1. View Profile");
                System.out.println("2. View Attendance");
                System.out.println("3. Fees");
                System.out.println("4. View Result");
                System.out.println("5. Class Updates");
                System.out.println("6. Material Links");
                System.out.println("7. View Events");
                System.out.println("11. Univercity Upatde ");
                System.out.println("8. CR Menu");
                System.out.println("9. Logout");
                System.out.println("10. Exit");
                System.out.println("12.     ");
                System.out.print("Choose an option: ");
    
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline
    
                switch (choice) 
                {
                    case 1 : viewProfile(email);continue;
                    case 2 : 
                    {
                        System.out.print("Enter your email to view attendance: ");
                        String studentEmail = scanner.nextLine();
                        viewAttendanceForStudent(studentEmail);
                        continue;
                    }
                    case 3 : showFees();continue;
                    case 4 : 
                    {
                        System.out.print("Enter your email to view results: ");
                        String studentEmail = scanner.nextLine();
                        ResultViewer.viewResults(studentEmail);
                        continue;
                    }
                    case 5 : 
                    {
                        System.out.print("Enter Class Name (IT, CE, CS, CD) to view updates: ");
                        String className = scanner.nextLine().toUpperCase();
                        ClassUpdateViewer.viewClassUpdates(className);
                        continue;
                    }
                    case 6 : 
                    {
                        System.out.print("Enter your class name (IT, CE, CS, CD) to view materials: ");
    String className = scanner.nextLine().toUpperCase();
    MaterialViewer.viewMaterials(className);
    continue;
                    }
                    case 7 : viewAllEvents();continue;
                    case 8 : crMenu(email);continue;

                    case 9 : {
                        System.out.println("Logging out...");
                        return;
                    }
                    case 10 : {
                        System.out.println("Exiting program...");
                        System.exit(0);
                    }
                    case 11 : {
                        viewAllUniversityUpdates();
                        continue;
                    }
                    case 12 : {
                        PersonalMessageViewer.viewPersonalMessages(email);
                        continue;
                    }
                    default : System.out.println("Invalid option. Try again.");
                }
            }
        }
    
        private static void viewProfile(String email) 
        {
            try (Connection conn = DBConnection.getConnection()) 
            {
                String query = "SELECT * FROM student_it WHERE email = ? UNION " +
                               "SELECT * FROM student_ce WHERE email = ? UNION " +
                               "SELECT * FROM student_cs WHERE email = ? UNION " +
                               "SELECT * FROM student_cd WHERE email = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, email);
                stmt.setString(2, email);
                stmt.setString(3, email);
                stmt.setString(4, email);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) 
                {
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Name: " + rs.getString("name"));
                    System.out.println("Email: " + rs.getString("email"));
                    System.out.println("Mobile Number: " + rs.getString("mo_number"));
                    System.out.println("Department: " + rs.getString("dept"));
                    System.out.println("Role: " + rs.getString("rol"));
                } else 
                {
                    System.out.println("Student profile not found.");
                }
            } catch (SQLException e) 
            {
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
        
    
        private static void showFees() 
        {
            // This feature is under construction. You can implement fees details logic here.
            System.out.println("Fees feature is coming soon!");
        }
    
        private static void viewResult(String email) 
        {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Semester Number: ");
            int semester = scanner.nextInt();
    
            try (Connection conn = DBConnection.getConnection()) 
            {
                String query = "SELECT * FROM results WHERE student_email = ? AND semester = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, email);
                stmt.setInt(2, semester);
                ResultSet rs = stmt.executeQuery();
    
                if (rs.next()) 
                {
                    System.out.println("Semester: " + rs.getInt("semester"));
                    System.out.println("Result: " + rs.getString("result"));
                } else 
                {
                    System.out.println("No result found for the given semester.");
                }
            } catch (SQLException e) 
            {
                e.printStackTrace();
            }
        }
    
        private static void viewClassUpdates(String email) 
        {
            try (Connection conn = DBConnection.getConnection()) 
            {
                String query = "SELECT * FROM class_updates WHERE student_email = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();
    
                while (rs.next()) {
                    System.out.println("Update: " + rs.getString("update_info"));
                    System.out.println("------------------------");
                }
            } catch (SQLException e) 
            {
                e.printStackTrace();
            }
        }
    
        private static void viewMaterialLinks() 
        {
            try (Connection conn = DBConnection.getConnection()) 
            {
                String query = "SELECT * FROM materials";
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
    
                while (rs.next()) 
                {
                    System.out.println("Material Link: " + rs.getString("link"));
                    System.out.println("------------------------");
                }
            } 
            catch (SQLException e) 
            {
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
                    Date eventDate = rs.getDate("event_date");
                    String eventDescription = rs.getString("event_description");
                    String department = rs.getString("department");
        
                    System.out.println(eventId + " | " + eventName + " | " + eventDate + " | " + eventDescription + " | " + (department == null ? "All" : department));
                }
        
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
    
        private static void crMenu(String email) 
        {
            Scanner scanner = new Scanner(System.in);
    
            // Check if the student is a CR
            if (!isCR(email)) 
            {
                System.out.println("You are not a Class Representative (CR). Access denied.");
                return;
            }
    
            while (true) 
            {
                System.out.println("\nCR Menu:");
                System.out.println("1. Add Class Update");
                System.out.println("2. Add Material Link");
                System.out.println("3. Logout");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");
    
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline
    
                switch (choice) {
                    case 1 : 
                    {
                        ClassUpdateManager.addClassUpdate();
                        continue;
                    }
                    case 2 : MaterialManager.addMaterial();continue;
                    case 3 : {
                        System.out.println("Logging out...");
                        return;
                    }
                    case 4 : {
                        System.out.println("Exiting program...");
                        System.exit(0);
                    }
                    default : System.out.println("Invalid option. Try again.");
                }
            }
        }
    
        private static boolean isCR(String email) 
        {
            try (Connection conn = DBConnection.getConnection()) 
            {
                String query = "SELECT * FROM student_it WHERE email = ? AND rol = 'CR' UNION " +
                               "SELECT * FROM student_ce WHERE email = ? AND rol = 'CR' UNION " +
                               "SELECT * FROM student_cs WHERE email = ? AND rol = 'CR' UNION " +
                               "SELECT * FROM student_cd WHERE email = ? AND rol = 'CR'";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, email);
                stmt.setString(2, email);
                stmt.setString(3, email);
                stmt.setString(4, email);
                ResultSet rs = stmt.executeQuery();
                return rs.next(); // If a record is found, the student is a CR
            } catch (SQLException e) 
            {
                e.printStackTrace();
            }
            return false;
        }
    
        private static void addClassUpdate(String email) 
        {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Class Update: ");
            String updateInfo = scanner.nextLine();
    
            try (Connection conn = DBConnection.getConnection()) 
            {
                String query = "INSERT INTO class_updates (student_email, update_info) VALUES (?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, email);
                stmt.setString(2, updateInfo);
                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) 
                {
                    System.out.println("Class update added successfully.");
                } else {
                    System.out.println("Error adding class update.");
                }
            } catch (SQLException e) 
            {
                e.printStackTrace();
            }
        }
    
        private static void addMaterialLink() 
        {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Material Link: ");
            String link = scanner.nextLine();
    
            try (Connection conn = DBConnection.getConnection()) 
            {
                String query = "INSERT INTO materials (link) VALUES (?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, link);
    
                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) 
                {
                    System.out.println("Material link added successfully.");
                } else
                {
                    System.out.println("Error adding material link.");
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

