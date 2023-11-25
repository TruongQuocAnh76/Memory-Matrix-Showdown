package view;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private Image background;
    private Image bottom;
    public BackgroundPanel(int width, int height, LayoutManager layout) {
        super(layout);
        this.background = new ImageIcon(getClass().getClassLoader().getResource("resource/images_game/background.png")).getImage();
        this.bottom = new ImageIcon(getClass().getClassLoader().getResource("resource/images_game/bottom.png")).getImage();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
        g.drawImage(bottom, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
