package view;

import java.awt.*;
import javax.swing.*;

public class View extends JFrame implements Runnable {
  private final int fps = 15;
  private CardLayout cardLayout = new CardLayout();
  private MouseController mouseController = new MouseController(this);
  private Thread thread = new Thread(this);
  private JPanel mainMenu;
  private GamePanel gamePanel;

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
    this.setResizable(true);

    mainMenuInit();
    gamePanelInit();

    this.setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
    this.setVisible(true);
    this.pack();

    thread.start();
  }

  private void mainMenuInit() {
    mainMenu = new JPanel(new GridLayout());
    JLabel background = new JLabel();
    background.setLayout(new GridBagLayout());
    background.setIcon(
        new ImageIcon(
            getClass().getClassLoader().getResource("resource/images_game/background.png")));
    mainMenu.add(background);
    
    GridBagConstraints gbc = new GridBagConstraints();

    JLabel title = getLabelButton("title", "resource/images_game/game_name.png", 800, 100);
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    background.add(title, gbc);

    gbc.gridwidth = 1;
    JLabel startButton = getLabelButton("start", "resource/images_game/start_button.png", 500, 400);
    gbc.gridx = 1;
    gbc.gridy = 2;
    background.add(startButton, gbc);
    startButton.addMouseListener(mouseController);

    JLabel exitButton = getLabelButton("exit", "resource/images_game/exit_button.png", 500, 400);
    gbc.gridx = 2;
    gbc.gridy = 2;
    background.add(exitButton, gbc);
    exitButton.addMouseListener(mouseController);

    JLabel highScoreButton =
        getLabelButton("highscore", "resource/images_game/high_score_button.png", 500, 400);
    gbc.gridx = 1;
    gbc.gridy = 3;
    background.add(highScoreButton, gbc);

    JLabel helpButton = getLabelButton("help", "resource/images_game/info_button.png", 500, 400);
    gbc.gridx = 2;
    gbc.gridy = 3;
    gbc.gridwidth = 1;
    background.add(helpButton, gbc);

    JLabel empty = new JLabel();
    empty.setOpaque(false);
    gbc.gridx = 3;
    gbc.gridy = 3;
    gbc.gridwidth = 1;
    background.add(empty, gbc);

    this.add(mainMenu, "mainMenu");
  }

  private void gamePanelInit() {
    gamePanel = new GamePanel();
    this.add(gamePanel, "gamePanel");
  }

  private JLabel getLabelButton(String name, String path, int width, int height) {
    JLabel label = new JLabel();
    label.setName(name);
    label.setPreferredSize(new Dimension(width, height));
    ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(path));
    Image img = icon.getImage();
    Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    label.setIcon(new ImageIcon(newImg));
    return label;
  }

  public void changePanel(String panelName) {
    cardLayout.show(this.getContentPane(), panelName);
  }

  public void exit() {
    System.exit(0);
  }

  @Override
  public void run() {
    while (thread.isAlive()) {
      mainMenu.repaint();
      gamePanel.repaint();
      try {
        Thread.sleep(1000 / fps);
      } catch (Exception ignored) {
      }
    }
  }
}
