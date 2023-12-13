package view;

import java.awt.*;
import javax.swing.*;


public class View extends JFrame implements Runnable {

  private final int fps = 30;
  public MouseController mouseController = new MouseController(this);
  public GamePanel gamePanel;
  private CardLayout cardLayout = new CardLayout();
  private Thread thread = new Thread(this);
  private JPanel mainMenu;
  private JPanel endScreen;
  // grid bag layout constants
  public final int GRID_WIDTH = 200;
  public final int GRID_HEIGHT = 100;
  public final int MAX_GRID = 8; // 8x8

  public View() {
    init();
  }

  private void init() {
    setTitle("Memory Matrix Showdown");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(cardLayout);
    this.setResizable(false);

    mainMenuInit();
    gamePanelInit();
    endScreenInit();

    this.pack();
    this.setLocationRelativeTo(null);
    this.setVisible(true);

    thread.start();
  }

  private void mainMenuInit() {
    mainMenu = new JPanel(new GridLayout());
    mainMenu.setPreferredSize(new Dimension(GRID_WIDTH * MAX_GRID, GRID_HEIGHT * MAX_GRID));
    JLabel background = new JLabel();
    background.setLayout(new GridBagLayout());
    background.setIcon(
            new ImageIcon(
                    getClass().getClassLoader().getResource("resource/images/background.png")));
    mainMenu.add(background);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.BOTH;

    JLabel title = getLabelButton("title", "resource/images/game_name.png", GRID_WIDTH * 4, GRID_HEIGHT * 2);
    gbc.gridx = 3;
    gbc.gridy = 1;
    gbc.gridwidth = 3;
    gbc.gridheight = 2;
    background.add(title, gbc);

    addButton("start", "resource/images/start_button.png", GRID_WIDTH * 2, GRID_HEIGHT * 4, 2, 3, mouseController, background);
    addButton("exit", "resource/images/exit_button.png", GRID_WIDTH * 2, GRID_HEIGHT * 4, 4, 3, mouseController, background);
    addButton("highscore", "resource/images/high_score_button.png", GRID_WIDTH * 2, GRID_HEIGHT * 4, 2, 5, mouseController, background);
    addButton("help", "resource/images/info_button.png", GRID_WIDTH * 2, GRID_HEIGHT * 4, 4, 5, mouseController, background);

    this.add(mainMenu, "mainMenu");
  }
  private void addButton(String name, String path, int width, int height, int gridx, int gridy, MouseController mouseController, JLabel background) {
    JLabel button = getLabelButton(name, path, width, height);
    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = gridx;
    gbc.gridy = gridy;
    gbc.gridwidth = 2;
    gbc.gridheight = 2;
    background.add(button, gbc);
    button.addMouseListener(mouseController);
  }

  private void gamePanelInit() {
    gamePanel = new GamePanel(this);
    this.add(gamePanel, "gamePanel");
  }
  private void endScreenInit() {
    endScreen = new JPanel(new BorderLayout());
    JLabel image = new JLabel();
    image.setName("dmfu");
    image.addMouseListener(mouseController);
    ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resource/images/gojo_dead.png"));
    icon = new ImageIcon(icon.getImage().getScaledInstance(GRID_WIDTH * MAX_GRID, GRID_HEIGHT * MAX_GRID, Image.SCALE_SMOOTH));
    image.setIcon(icon);
    endScreen.add(image, BorderLayout.CENTER);
    JLabel text = new JLabel("Datte kimi, suyoi mo", SwingConstants.CENTER);
    text.setName("text");
    text.addMouseListener(mouseController);
    text.setFont(new Font("Arial", Font.BOLD, 80));
    endScreen.add(text, BorderLayout.SOUTH);
    this.add(endScreen, "endScreen");
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
  public void showHelpImage() {
    ImageIcon helpImageIcon = new ImageIcon(getClass().getClassLoader().getResource("resource/images/board.png"));
    Image helpImage = helpImageIcon.getImage();
    Image newHelpImage = helpImage.getScaledInstance(800, 800, Image.SCALE_SMOOTH);

    // Tạo một JFrame mới để hiển thị ảnh
    JFrame helpFrame = new JFrame("Help");
    JLabel helpLabel = new JLabel(new ImageIcon(newHelpImage));
    helpFrame.getContentPane().add(helpLabel);
    helpFrame.pack();
    helpFrame.setLocationRelativeTo(null);
    helpFrame.setVisible(true);
  }
  public void changePanel(String panelName) {
    if(panelName.equals("gamePanel")) gamePanel.reset();

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