package utils;

import java.sql.*;
import java.util.Scanner;

public class StudentLogin {

    public static String login() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        // Simulate password verification for simplicity
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        // Add your login authentication logic here
        // For demonstration, we assume login is always successful

        // Return email on successful login
        return email;
    }
}

