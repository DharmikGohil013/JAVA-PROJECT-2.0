package utils;
import javax.swing.*;
import java.awt.*;

public class LogoScreen extends JFrame {
    
    public LogoScreen() {
        // Set up the frame
        setTitle("Logo Screen");
        setSize(600, 400); // Size of the window
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true); // Remove window decorations

        // Create a custom panel to draw the logo
        LogoPanel logoPanel = new LogoPanel();
        add(logoPanel);

        // Display the window
        setVisible(true);
    }
    
    private class LogoPanel extends JPanel {
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            // Set background color
            setBackground(Color.WHITE);
            
            // Draw the text in the center of the panel
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setFont(new Font("Arial", Font.BOLD, 36));
            g2d.setColor(Color.BLUE);
            
            String text = "GDGS E Governance";
            FontMetrics fm = g2d.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(text)) / 2;
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            
            g2d.drawString(text, x, y);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LogoScreen logoScreen = new LogoScreen();
            
            // Timer to close the logo screen after 3 seconds
            Timer timer = new Timer(3000, e -> {
                logoScreen.dispose(); // Close the logo screen
                // Open the main application window here
                // For example: new MainApplicationWindow().setVisible(true);
            });
            timer.setRepeats(false); // Ensure it only fires once
            timer.start();
        });
    }
}
