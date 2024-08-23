package utils;
// EGovernanceSystem.java
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class EGovernanceSystem {

    public static void main(String[] args) {
        displayLogo();
        showOpeningPage();
        clearConsole();
        showMainMenu();
    }

    private static void displayLogo() {
        System.out.println("Displaying logo...");
        try {
            Thread.sleep(3000); // Display logo for 3 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void showOpeningPage() {
        System.out.println("Opening page......");
        try {
            Thread.sleep(2000); // Show "opening page..." for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void showMainMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Main Menu:");
            System.out.println("1. Admin");
            System.out.println("2. Faculty");
            System.out.println("3. Student");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 : Admin.login();
                case 2 : Faculty.login();
                case 3 : Student.login();
                case 4 : {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default : System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
