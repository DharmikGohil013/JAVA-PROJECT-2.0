package utils;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Admin {

    public static void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Admin Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (authenticateAdmin(email, password)) {
            System.out.println("Login Successful!");
            showAdminMenu();
        } else {
            System.out.println("Invalid credentials. Try again.");
        }
    }

    private static boolean authenticateAdmin(String email, String password) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM admin WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // True if admin exists
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private static void showAdminMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Admin Menu:");
            System.out.println("1. View All Admin Details");
            System.out.println("2. View My Details");
            System.out.println("3. View All Student Details");
            System.out.println("4. View All Faculty Details");
            System.out.println("5. Update Student Details");
            System.out.println("6. Update Faculty Details");
            System.out.println("7. Add New Faculty");
            System.out.println("8. Add New Student");
            System.out.println("9. Add Events");
            System.out.println("10. Add University Updates");
            System.out.println("11. Logout");
            System.out.println("12. Exit");

            int choice = scanner.nextInt();
            switch (choice) {
                
                case 1:
                {
                    viewAllAdminDetails();
                    continue;
                   
                }
                case 2:
                {
                    viewMyDetails();
                    continue;
                    
                }
                case 3:
                {
                    showAllStudentDetailsByBranch();
                    continue;
                    
                }
                case 4:
                {
                    showAllFacultyDetailsByBranch();
                    continue;
                    
                }
                case 5:
                {
                    updateStudentDetails();
                    continue;
                    
                }
                case 6:
                {
                    updateFacultyDetails();
                    continue;
                    
                }
                case 7:
                {
                    addNewFaculty();
                    continue;
                    
                }
                case 8:
                {
                    addNewStudent();
                    continue;
                    
                }
                case 9:
                {
                    addEvents();
                    continue;
                    
                }
                case 10:
                {
                    addUniversityUpdates();
                    continue;
                    
                }
                case 11 : 
                {
                    System.out.println("Logging out...");
                    return;
                }
                case 12 :
                {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default : System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewAllAdminDetails() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM admin";
            PreparedStatement stmt = conn.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") +
                        ", Email: " + rs.getString("email") + ", Mobile: " + rs.getString("mo_number"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add to Admin.java

private static void viewMyDetails() {
    try (Connection conn = DBConnection.getConnection()) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your email to view details: ");
        String email = scanner.nextLine();
        String query = "SELECT * FROM admin WHERE email = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, email);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") +
                    ", Email: " + rs.getString("email") + ", Mobile: " + rs.getString("mo_number"));
        } else {
            System.out.println("No details found for the given email.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

// Continue in Admin.java

private static void updateStudentDetails() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter Student Email to Update: ");
    String email = scanner.nextLine();
    
    // Check in which table the student is
    String tableName = findStudentTable(email);
    if (tableName == null) {
        System.out.println("Student not found.");
        return;
    }

    System.out.print("Enter new Name: ");
    String newName = scanner.nextLine();
    System.out.print("Enter new Mobile Number: ");
    String newMobile = scanner.nextLine();
    
    try (Connection conn = DBConnection.getConnection()) {
        String query = "UPDATE " + tableName + " SET name = ?, mo_number = ? WHERE email = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, newName);
        stmt.setString(2, newMobile);
        stmt.setString(3, email);
        int rowsUpdated = stmt.executeUpdate();

        if (rowsUpdated > 0) {
            System.out.println("Student details updated successfully.");
        } else {
            System.out.println("Error updating student details.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private static String findStudentTable(String email) {
    String[] tables = {"student_it", "student_ce", "student_cs", "student_cd"};
    try (Connection conn = DBConnection.getConnection()) {
        for (String table : tables) {
            String query = "SELECT * FROM " + table + " WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return table;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

private static void updateFacultyDetails() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter Faculty Email to Update: ");
    String email = scanner.nextLine();

    System.out.print("Enter new Name: ");
    String newName = scanner.nextLine();
    System.out.print("Enter new Mobile Number: ");
    String newMobile = scanner.nextLine();

    try (Connection conn = DBConnection.getConnection()) {
        String query = "UPDATE faculty SET name = ?, mo_number = ? WHERE email = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, newName);
        stmt.setString(2, newMobile);
        stmt.setString(3, email);
        int rowsUpdated = stmt.executeUpdate();

        if (rowsUpdated > 0) {
            System.out.println("Faculty details updated successfully.");
        } else {
            System.out.println("Error updating faculty details.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private static void addNewFaculty() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter New Faculty Name: ");
    String name = scanner.nextLine();
    System.out.print("Enter New Faculty Email: ");
    String email = scanner.nextLine();
    System.out.print("Enter New Faculty Mobile Number: ");
    String mobile = scanner.nextLine();
    System.out.print("Enter New Faculty Password: ");
    String password = scanner.nextLine();
    System.out.print("Enter New Faculty Department: ");
    String dept = scanner.nextLine();
    System.out.print("Enter New Faculty Role: ");
    String role = scanner.nextLine();

    try (Connection conn = DBConnection.getConnection()) {
        String query = "INSERT INTO faculty (name, email, mo_number, password, dept, rol) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, name);
        stmt.setString(2, email);
        stmt.setString(3, mobile);
        stmt.setString(4, password);
        stmt.setString(5, dept);
        stmt.setString(6, role);

        int rowsInserted = stmt.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("New faculty added successfully.");
        } else {
            System.out.println("Error adding new faculty.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private static void addNewStudent() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter New Student Name: ");
    String name = scanner.nextLine();
    System.out.print("Enter New Student Email: ");
    String email = scanner.nextLine();
    System.out.print("Enter New Student Mobile Number: ");
    String mobile = scanner.nextLine();
    System.out.print("Enter New Student Password: ");
    String password = scanner.nextLine();
    System.out.print("Enter New Student Department (it, ce, cs, cd): ");
    String dept = scanner.nextLine();

    String tableName = "student_" + dept;
    try (Connection conn = DBConnection.getConnection()) {
        String query = "INSERT INTO " + tableName + " (name, email, mo_number, password, dept, rol) VALUES (?, ?, ?, ?, ?, 'normal')";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, name);
        stmt.setString(2, email);
        stmt.setString(3, mobile);
        stmt.setString(4, password);
        stmt.setString(5, dept);

        int rowsInserted = stmt.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("New student added successfully.");
        } else {
            System.out.println("Error adding new student.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private static void addEvents() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter Event Description: ");
    String eventDescription = scanner.nextLine();

    try (Connection conn = DBConnection.getConnection()) {
        String query = "INSERT INTO events (description) VALUES (?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, eventDescription);

        int rowsInserted = stmt.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Event added successfully.");
        } else {
            System.out.println("Error adding event.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private static void addUniversityUpdates() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter University Update Message: ");
    String updateMessage = scanner.nextLine();

    try (Connection conn = DBConnection.getConnection()) {
        String query = "INSERT INTO university_updates (message) VALUES (?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, updateMessage);

        int rowsInserted = stmt.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("University update added successfully.");
        } else {
            System.out.println("Error adding university update.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private static void showAllStudentDetailsByBranch() {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Select Department to View Student Details:");
    System.out.println("1. IT");
    System.out.println("2. CE");
    System.out.println("3. CS");
    System.out.println("4. CD");
    System.out.print("Choose a department: ");

    int choice = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    String tableName;

    switch (choice) {
        case 1:
            tableName = "student_it";
            break;
        case 2:
            tableName = "student_ce";
            break;
        case 3:
            tableName = "student_cs";
            break;
        case 4:
            tableName = "student_cd";
            break;
        default:
            System.out.println("Invalid option. Returning to menu.");
            return; // Exit the method if an invalid option is selected
    }

    try (Connection conn = DBConnection.getConnection()) {
        String query = "SELECT * FROM " + tableName;
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        System.out.println("ID | Name | Email | Mobile Number | Role | Department | Password");
        System.out.println("--------------------------------------------------------------");

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String mobileNumber = rs.getString("mo_number");
            String role = rs.getString("rol");
            String department = rs.getString("dept");
            String password = rs.getString("password");

            System.out.println(id + " | " + name + " | " + email + " | " + mobileNumber + " | " + role + " | " + department + " | " + password);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private static void showAllFacultyDetailsByBranch() {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Select Department to View Faculty Details:");
    System.out.println("1. IT");
    System.out.println("2. CE");
    System.out.println("3. CS");
    System.out.println("4. CD");
    System.out.print("Choose a department: ");

    int choice = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    String department;

    switch (choice) {
        case 1:
            department = "it";
            break;
        case 2:
            department = "ce";
            break;
        case 3:
            department = "cs";
            break;
        case 4:
            department = "cd";
            break;
        default:
            System.out.println("Invalid option. Returning to menu.");
            return; // Exit the method if an invalid option is selected
    }

    try (Connection conn = DBConnection.getConnection()) {
        String query = "SELECT * FROM faculty WHERE dept = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, department);
        ResultSet rs = stmt.executeQuery();

        System.out.println("ID | Name | Email | Mobile Number | Role | Department | Password");
        System.out.println("--------------------------------------------------------------");

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String mobileNumber = rs.getString("mo_number");
            String role = rs.getString("rol");
            String dept = rs.getString("dept");
            String password = rs.getString("password");

            System.out.println(id + " | " + name + " | " + email + " | " + mobileNumber + " | " + role + " | " + dept + " | " + password);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    // Add methods for other admin functionalities...
}
