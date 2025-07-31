package HRManagementApp.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * A custom JPanel that draws a background image with a black tint.
 */
public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String fileName) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) {
                System.err.println("Background image not found: " + fileName);
                backgroundImage = null;
            } else {
                backgroundImage = ImageIO.read(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
            backgroundImage = null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Draw background image
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);

            // Add semi-transparent black overlay (tint)
            Graphics2D g2d = (Graphics2D) g.create();
            Color blackTint = new Color(0, 0, 0, 120); // 120 is alpha (0-255), higher is darker
            g2d.setColor(blackTint);
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
            g2d.dispose();
        }
    }
}
