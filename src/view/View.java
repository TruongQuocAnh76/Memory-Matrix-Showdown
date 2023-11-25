package view;

import java.awt.*;
import javax.swing.*;

public class View extends JFrame {
  private CardLayout cardLayout = new CardLayout();
  public View() {
    init();
  }

  private void init() {
    setTitle("Memory Matrix Showdown");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);
    this.setSize(SwingConstants.CENTER, SwingConstants.CENTER);
    this.setLocationRelativeTo(null);
    this.setLayout(cardLayout);

    mainMenuInit();

    this.setVisible(true);
    this.pack();
  }

  private void mainMenuInit() {
    BackgroundPanel mainMenu =
        new BackgroundPanel(this.getWidth(), this.getHeight(), new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    JLabel title = getLabelButton("resource/images_game/nameGame.png", 800, 100);
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    mainMenu.add(title, gbc);

    gbc.gridwidth = 1;
    JLabel startButton = getLabelButton("resource/images_game/start.png", 500, 400);
    gbc.gridx = 1;
    gbc.gridy = 2;
    mainMenu.add(startButton, gbc);

    JLabel exitButton = getLabelButton("resource/images_game/exit.png", 500, 400);
    gbc.gridx = 2;
    gbc.gridy = 2;
    mainMenu.add(exitButton, gbc);

    JLabel highScoreButton = getLabelButton("resource/images_game/highScore.png", 500, 400);
    gbc.gridx = 1;
    gbc.gridy = 3;
    mainMenu.add(highScoreButton, gbc);

    JLabel helpButton = getLabelButton("resource/images_game/info.png", 500, 400);
    gbc.gridx = 2;
    gbc.gridy = 3;
    gbc.gridwidth = 1;
    mainMenu.add(helpButton, gbc);

    JLabel empty = new JLabel();
    empty.setOpaque(false);
    gbc.gridx = 3;
    gbc.gridy = 3;
    gbc.gridwidth = 1;
    mainMenu.add(empty, gbc);

    this.add(mainMenu);
  }
  private JLabel getLabelButton(String path, int width, int height) {
    JLabel label = new JLabel();
    label.setPreferredSize(new Dimension(width, height));
    ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(path));
    Image img = icon.getImage();
    Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    label.setIcon(new ImageIcon(newImg));
    return label;
  }
}
