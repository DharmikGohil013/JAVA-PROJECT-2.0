package utils;

import java.util.Scanner;

public class StudentMenu {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Login to capture email
        String studentEmail = StudentLogin.login();

        boolean exit = false;
        while (!exit) {
            System.out.println("Student Menu:");
            System.out.println("1. View Personal Messages");
            System.out.println("2. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    viewPersonalMessages(studentEmail);
                    break;
                case 2:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void viewPersonalMessages(String studentEmail) {
        // Retrieve and display messages based on studentEmail
        // This code would be similar to the viewPersonalMessages method provided earlier
    }
}
