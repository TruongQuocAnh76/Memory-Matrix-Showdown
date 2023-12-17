package view;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private Image background;
    private Image bottom;

    public BackgroundPanel() {
        this.setLayout(new FlowLayout()); // Default layout manager
        loadImages();
    }

    private void loadImages() {
        try {
            // Load background image
            background = new ImageIcon(getClass().getClassLoader().getResource("resource/images_game/background.png")).getImage();

            // Load bottom image
            bottom = new ImageIcon(getClass().getClassLoader().getResource("resource/images_game/bottom.png")).getImage();
        } catch (Exception e) {
            // Handle image loading errors (e.g., log or display an error message)
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (background != null && bottom != null) {
            // Draw background image
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);

            // Draw bottom image
            g.drawImage(bottom, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
