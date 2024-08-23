package utils;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        while (true) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("\nMain Menu:");
            System.out.println("1. Admin Login");
            System.out.println("2. Faculty Login");
            System.out.println("3. Student Login");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 : adminLogin();continue;
                case 2 : facultyLogin();continue;
                case 3 :studentLogin();continue;
                case 4 : {
                    System.out.println("Exiting program...");
                    System.exit(0); // Terminates the program
                }
                default : System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void adminLogin() {
        // Handle Admin login logic
        Admin.login();
    }

    private static void facultyLogin() {
        // Handle Faculty login logic
        Faculty.login();
    }

    private static void studentLogin() {
        // Handle Student login logic
        Student.login();
    }
}
