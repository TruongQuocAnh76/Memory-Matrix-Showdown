package view;

import java.awt.*;
import javax.swing.*;

public class View extends JFrame implements Runnable {
  private final int fps = 30;
  private CardLayout cardLayout = new CardLayout();
  public MouseController mouseController = new MouseController(this);
  private Thread thread = new Thread(this);
  private JPanel mainMenu;
  public GamePanel gamePanel;

  public View() {
    init();
  }

  private void init() {
    setTitle("Memory Matrix Showdown");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
    this.setLayout(cardLayout);
    this.setResizable(true);
    this.setUndecorated(true);

    mainMenuInit();
    gamePanelInit();

    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
    gd.setFullScreenWindow(this);

    this.setMinimumSize(this.getSize());
    this.pack();
    this.setVisible(true);

    thread.start();
  }

  private void mainMenuInit() {
    mainMenu = new JPanel(new GridLayout());
    JLabel background = new JLabel();
    background.setLayout(new GridBagLayout());
    background.setIcon(
        new ImageIcon(
            getClass().getClassLoader().getResource("images/background.png")));
    mainMenu.add(background);
    
    GridBagConstraints gbc = new GridBagConstraints();

    JLabel title = getLabelButton("title", "images/game_name.png", 800, 100);
    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    background.add(title, gbc);

    gbc.gridwidth = 1;
    JLabel startButton = getLabelButton("start", "images/start_button.png", 500, 400);
    gbc.gridx = 1;
    gbc.gridy = 2;
    background.add(startButton, gbc);
    startButton.addMouseListener(mouseController);

    JLabel exitButton = getLabelButton("exit", "images/exit_button.png", 500, 400);
    gbc.gridx = 2;
    gbc.gridy = 2;
    background.add(exitButton, gbc);
    exitButton.addMouseListener(mouseController);

    JLabel highScoreButton =
        getLabelButton("highscore", "images/high_score_button.png", 500, 400);
    gbc.gridx = 1;
    gbc.gridy = 3;
    background.add(highScoreButton, gbc);

    JLabel helpButton = getLabelButton("help", "images/info_button.png", 500, 400);
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
    gamePanel = new GamePanel(this);
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
      // test draw time
      mainMenu.repaint();
      gamePanel.repaint();

      gamePanel.clockLabel.repaint();
      try {
        Thread.sleep(1000 / fps);
      } catch (Exception ignored) {
      }
    }
  }
}
