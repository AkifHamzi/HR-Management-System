package HRManagementApp.gui;

import javax.swing.*;
import java.awt.*;

/**
 * The main JFrame of the application.
 * It uses a CardLayout to switch between different panels (Login, Dashboards, etc.).
 * It also features a custom background panel.
 */
public class MainFrame extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    public MainFrame() {
        setTitle("Colombo Institute of Studies - HR Management");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Custom panel for background image
        BackgroundPanel backgroundPanel = new BackgroundPanel("background.jpg");
        setContentPane(backgroundPanel);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setOpaque(false); // Make the main panel transparent

        // Add mainPanel to the content pane
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Adds a panel to the CardLayout.
     * @param panel The JPanel to add.
     * @param name  The name to identify the panel.
     */
    public void addPanel(JPanel panel, String name) {
        mainPanel.add(panel, name);
    }

    /**
     * Switches the view to the specified panel.
     * @param name The name of the panel to show.
     */
    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }
}