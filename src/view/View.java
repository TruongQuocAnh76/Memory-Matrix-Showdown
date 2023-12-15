package view;

import java.awt.*;
import java.io.InputStream;
import java.util.List;
import javax.swing.*;

import module.ScoreManager;
import sound.SoundManager;

public class View extends JFrame {

  // grid bag layout constants
  public final int GRID_WIDTH = 200;
  public final int GRID_HEIGHT = 100;
  public final int MAX_GRID = 8; // 8x8
  public MouseController mouseController = new MouseController(this);
  public GamePanel gamePanel;
  public SoundManager soundManager = new SoundManager();
  private final CardLayout cardLayout = new CardLayout();
  private JPanel mainMenu;
  private JPanel endScreen;
  public Font gameFont;
  private JPanel highScoreScreen;
  private JLabel scoresLabel; // label showing the high scores

  private ScoreManager scoreManager = new ScoreManager();
  public View() {
    init();
  }

  private void init() {
    setTitle("Memory Matrix Showdown");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(cardLayout);
    this.setResizable(false);

    loadFont();

    mainMenuInit();
    gamePanelInit();
    endScreenInit();
    helpScreenInit();
    highScoreScreenInit();

    soundManager.playBackground();

    this.pack();
    this.setLocationRelativeTo(null);
    this.setVisible(true);
  }

  private void loadFont() {
    try {
      InputStream is = getClass().getClassLoader().getResourceAsStream("resource/fonts/game_font.ttf");
      gameFont = Font.createFont(Font.TRUETYPE_FONT, is);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void mainMenuInit() {
    mainMenu = new JPanel(new GridLayout());
    mainMenu.setPreferredSize(new Dimension(GRID_WIDTH * MAX_GRID, GRID_HEIGHT * MAX_GRID));
    JLabel background = new JLabel();
    background.setLayout(new GridBagLayout());
    background.setIcon(
            new ImageIcon(getClass().getClassLoader().getResource("resource/images/background.png")));
    mainMenu.add(background);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.BOTH;

    JLabel title =
            getLabelButton("title", "resource/images/game_name.png", GRID_WIDTH * 4, GRID_HEIGHT * 2);
    gbc.gridx = 3;
    gbc.gridy = 1;
    gbc.gridwidth = 3;
    gbc.gridheight = 2;
    background.add(title, gbc);

    addButton(
            "start",
            "resource/images/start_button.png",
            GRID_WIDTH * 2,
            GRID_HEIGHT * 4,
            2,
            3,
            mouseController,
            background);
    addButton(
            "exit",
            "resource/images/exit_button.png",
            GRID_WIDTH * 2,
            GRID_HEIGHT * 4,
            4,
            3,
            mouseController,
            background);
    addButton(
            "highScore",
            "resource/images/high_score_button.png",
            GRID_WIDTH * 2,
            GRID_HEIGHT * 4,
            2,
            5,
            mouseController,
            background);
    addButton(
            "help",
            "resource/images/info_button.png",
            GRID_WIDTH * 2,
            GRID_HEIGHT * 4,
            4,
            5,
            mouseController,
            background);
//    addButton(
//            "mute",
//            "resource/images/mute_sound.png",
//            GRID_WIDTH * 2,
//            GRID_HEIGHT * 4,
//            3,
//            7,
//            mouseController,
//            background);

    this.add(mainMenu, "mainMenu");
  }

  private void addButton(
          String name,
          String path,
          int width,
          int height,
          int gridx,
          int gridy,
          MouseController mouseController,
          JLabel background) {
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
    endScreen.setName("endScreen");
    endScreen.setBackground(Color.BLACK);
    endScreen.setName("endScreen");
    JLabel image = new JLabel();
    image.setName("endScreen");
    image.addMouseListener(mouseController);
    ImageIcon icon =
            new ImageIcon(getClass().getClassLoader().getResource("resource/images/game_over.png"));
    icon =
            new ImageIcon(
                    icon.getImage()
                            .getScaledInstance(
                                    GRID_WIDTH * MAX_GRID, GRID_HEIGHT * MAX_GRID, Image.SCALE_SMOOTH));
    image.setIcon(icon);
    endScreen.add(image, BorderLayout.CENTER);
    JLabel text = new JLabel("Final score: " + gamePanel.getScore(), SwingConstants.CENTER); // for some reason setting the text here make the frame pack in the proper size
    text.setName("endScreen");
    text.addMouseListener(mouseController);
    text.setFont(gameFont.deriveFont(Font.BOLD, 80));
    text.setForeground(Color.WHITE);
    endScreen.add(text, BorderLayout.SOUTH);
    this.add(endScreen, "endScreen");
  }

  private void helpScreenInit() {
    JPanel helpScreen = new JPanel(new BorderLayout());
    helpScreen.setPreferredSize(new Dimension(GRID_WIDTH * MAX_GRID, GRID_HEIGHT * MAX_GRID));
    helpScreen.setOpaque(false);

    JLabel background = new JLabel();
    background.setLayout(null);

    JLabel exitButton = new JLabel();
    exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    exitButton.setName("back");
    ImageIcon exitIcon =
            new ImageIcon(getClass().getClassLoader().getResource("resource/images/exit_button.png"));
    exitIcon = new ImageIcon(exitIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH));
    exitButton.setIcon(exitIcon);
    exitButton.setBounds(1050, 100, 300, 300);
    exitButton.addMouseListener(mouseController);
    background.add(exitButton);

    background.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resource/images/background.png")));
    helpScreen.add(background, BorderLayout.CENTER);

    JLabel image = new JLabel();
    ImageIcon icon =
            new ImageIcon(getClass().getClassLoader().getResource("resource/images/board.png"));
    icon =
            new ImageIcon(
                    icon.getImage()
                            .getScaledInstance(
                                    GRID_WIDTH * 8, GRID_HEIGHT * 10, Image.SCALE_SMOOTH));
    image.setIcon(icon);
    image.setBounds(0, -GRID_HEIGHT, GRID_WIDTH * 8, GRID_HEIGHT * 10);
    background.add(image);

    this.add(helpScreen, "helpScreen");
  }
  private void highScoreScreenInit() {
    highScoreScreen = new JPanel(new BorderLayout());
    highScoreScreen.setPreferredSize(new Dimension(GRID_WIDTH * MAX_GRID, GRID_HEIGHT * MAX_GRID));
    JLabel background = new JLabel();
    background.setLayout(null);

    JLabel exitButton = new JLabel();
    exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    exitButton.setName("back");
    ImageIcon exitIcon =
            new ImageIcon(getClass().getClassLoader().getResource("resource/images/exit_button.png"));
    exitIcon = new ImageIcon(exitIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH));
    exitButton.setIcon(exitIcon);
    exitButton.setBounds(945, 140, 300, 300);
    exitButton.addMouseListener(mouseController);
    background.add(exitButton);

    background.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resource/images/background.png")));
    highScoreScreen.add(background, BorderLayout.CENTER);

    JLabel image = new JLabel();
    image.setLayout(null);
    ImageIcon icon =
            new ImageIcon(getClass().getClassLoader().getResource("resource/images/table.png"));
    icon =
            new ImageIcon(
                    icon.getImage()
                            .getScaledInstance(
                                    GRID_WIDTH * 6, GRID_HEIGHT * 8, Image.SCALE_SMOOTH));
    image.setIcon(icon);
    image.setBounds(200, 0, GRID_WIDTH * 6, GRID_HEIGHT * 8);
    background.add(image);

    List<Integer> scores = scoreManager.getScores();
    JLabel titleText = new JLabel("High Scores", SwingConstants.CENTER);
    titleText.setFont(gameFont.deriveFont(Font.BOLD, 60));
    titleText.setBounds(340, 80, 400,400);
    image.add(titleText);

    scoresLabel = new JLabel();
    scoresLabel.setFont(gameFont.deriveFont(Font.PLAIN, 50));
    scoresLabel.setBounds(250, 230, 400, 400);
    updateHighScoreScreen();
    image.add(scoresLabel, BorderLayout.CENTER);

    this.add(highScoreScreen, "highScore");
  }

  private void updateHighScoreScreen() {
    List<Integer> scores = scoreManager.getScores();
    String scoresText = String.format(
            "<html>" +
                    "<ol>" +
                    "<li> %d </li>" +
                    "<li> %d </li>" +
                    "<li> %d </li>" +
                    "<li> %d </li>" +
                    "<li> %d </li>" +
                    "</ol>" +
                    "</html>", scores.get(0), scores.get(1), scores.get(2), scores.get(3), scores.get(4));
    scoresLabel.setText(scoresText);
  }
  public void updateScore() {
    JLabel text = (JLabel) endScreen.getComponent(1); // 0 is the image label, 1 is the text label
    text.setText("Final score: " + gamePanel.getScore());
    scoreManager.addScore(gamePanel.getScore());
    updateHighScoreScreen();
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
    if (panelName.equals("gamePanel")) gamePanel.reset();

    cardLayout.show(this.getContentPane(), panelName);
  }

  public void exit() {
    System.exit(0);
  }
}
