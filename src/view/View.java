package view;

import java.awt.*;
import java.io.InputStream;
import java.util.List;
import javax.swing.*;
import module.ScoreManager;
import sound.SoundManager;

public class View extends JFrame {

  public static final int MAX_GRID = 16; // 16x16
  private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  public static final int GRID_WIDTH = screenSize.width / MAX_GRID;
  public static final int GRID_HEIGHT = screenSize.height / MAX_GRID;
  
  private final CardLayout cardLayout = new CardLayout();
  public MouseController mouseController = new MouseController(this);
  public GamePanel gamePanel;
  public SoundManager soundManager = new SoundManager();
  public Font gameFont;
  private JPanel mainMenu;
  private JPanel endScreen;
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

    this.setLayout(cardLayout);
    mainMenuInit();
    gamePanelInit();
    endScreenInit();
    helpScreenInit();
    highScoreScreenInit();

    soundManager.playBackground();

    //    this.pack();
    this.setSize(screenSize);
    this.setLocationRelativeTo(null);
    this.setVisible(true);
  }

  private void loadFont() {
    try {
      InputStream is =
          getClass().getClassLoader().getResourceAsStream("resource/fonts/game_font.ttf");
      gameFont = Font.createFont(Font.TRUETYPE_FONT, is);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void mainMenuInit() {
    mainMenu = new JPanel(null);

    JLabel background = new JLabel();
    background.setLayout(null);
    ImageIcon icon =
        new ImageIcon(getClass().getClassLoader().getResource("resource/images/background.png"));
    Image img =
        icon.getImage()
            .getScaledInstance(GRID_WIDTH * MAX_GRID, GRID_HEIGHT * MAX_GRID, Image.SCALE_SMOOTH);
    background.setIcon(new ImageIcon(img));
    background.setBounds(0, 0, GRID_WIDTH * MAX_GRID, GRID_HEIGHT * MAX_GRID);
    mainMenu.add(background);

    int buttonWidth = 2 * GRID_WIDTH;
    int buttonHeight = 2 * GRID_HEIGHT;

    JLabel title = new JLabel();
    icon = new ImageIcon(getClass().getClassLoader().getResource("resource/images/title.png"));
    img = icon.getImage().getScaledInstance(6 * GRID_WIDTH, 4 * GRID_HEIGHT, Image.SCALE_SMOOTH);
    title.setIcon(new ImageIcon(img));
    title.setBounds(5 * GRID_WIDTH, GRID_HEIGHT, 6 * GRID_WIDTH, GRID_HEIGHT * 4);
    background.add(title);

    addButton(
        background,
        "start",
        "resource/images/start_button.png",
        5 * GRID_WIDTH,
        6 * GRID_HEIGHT,
        buttonWidth,
        buttonHeight);
    addButton(
        background,
        "highScore",
        "resource/images/high_score_button.png",
        5 * GRID_WIDTH,
        12 * GRID_HEIGHT,
        buttonWidth,
        buttonHeight);
    addButton(
        background,
        "help",
        "resource/images/info_button.png",
        10 * GRID_WIDTH,
        12 * GRID_HEIGHT,
        buttonWidth,
        buttonHeight);
    addButton(
        background,
        "exit",
        "resource/images/exit_button.png",
        10 * GRID_WIDTH,
        6 * GRID_HEIGHT,
        buttonWidth,
        buttonHeight);

    this.add(mainMenu, "mainMenu");
  }

  private void addButton(
      JLabel container, String name, String imagePath, int x, int y, int width, int height) {
    JLabel button = new JLabel();
    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    button.setName(name);
    ImageIcon buttonIcon = new ImageIcon(getClass().getClassLoader().getResource(imagePath));
    buttonIcon =
        new ImageIcon(buttonIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    button.setIcon(buttonIcon);
    button.setBounds(x, y, width, height);
    button.addMouseListener(mouseController);
    container.add(button);
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
    JLabel text =
        new JLabel(
            "Final score: " + gamePanel.getScore(),
            SwingConstants
                .CENTER); // for some reason setting the text here make the frame pack in the proper
    // size
    text.setName("endScreen");
    text.addMouseListener(mouseController);
    text.setFont(gameFont.deriveFont(Font.BOLD, 80));
    text.setForeground(Color.WHITE);
    endScreen.add(text, BorderLayout.SOUTH);
    this.add(endScreen, "endScreen");
  }

  private void helpScreenInit() {
    JPanel helpScreen = new JPanel(null);
    helpScreen.setName("helpScreen");

    JLabel background = new JLabel();
    background.setLayout(null);
    background.setIcon(
        new ImageIcon(getClass().getClassLoader().getResource("resource/images/background.png")));
    background.setBounds(0, 0, GRID_WIDTH * MAX_GRID, GRID_HEIGHT * MAX_GRID);
    helpScreen.add(background);

    JLabel backButton = new JLabel();
    backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    backButton.setName("back");
    ImageIcon backIcon =
        new ImageIcon(getClass().getClassLoader().getResource("resource/images/exit_button.png"));
    backIcon =
        new ImageIcon(
            backIcon.getImage().getScaledInstance(GRID_WIDTH, GRID_HEIGHT, Image.SCALE_SMOOTH));
    backButton.setIcon(backIcon);
    backButton.setBounds(10 * GRID_WIDTH + 80, 3 * GRID_HEIGHT - 30, GRID_WIDTH, GRID_HEIGHT);
    backButton.addMouseListener(mouseController);
    background.add(backButton);

    JLabel helpBoard = new JLabel();
    helpBoard.setLayout(null);
    ImageIcon icon =
        new ImageIcon(getClass().getClassLoader().getResource("resource/images/help_board.png"));
    Image img =
        icon.getImage().getScaledInstance(8 * GRID_WIDTH, 11 * GRID_HEIGHT, Image.SCALE_SMOOTH);
    helpBoard.setIcon(new ImageIcon(img));
    helpBoard.setBounds(4 * GRID_WIDTH, 2 * GRID_HEIGHT, 8 * GRID_WIDTH, 11 * GRID_HEIGHT);
    background.add(helpBoard);

    this.add(helpScreen, "helpScreen");
  }

  private void highScoreScreenInit() {
    highScoreScreen = new JPanel(new BorderLayout());
    highScoreScreen.setPreferredSize(new Dimension(GRID_WIDTH * MAX_GRID, GRID_HEIGHT * MAX_GRID));
    JLabel background = new JLabel();
    background.setLayout(null);

    JLabel backButton = new JLabel();
    backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    backButton.setName("back");
    ImageIcon backIcon =
        new ImageIcon(getClass().getClassLoader().getResource("resource/images/exit_button.png"));
    backIcon =
        new ImageIcon(
            backIcon.getImage().getScaledInstance(GRID_WIDTH, GRID_HEIGHT, Image.SCALE_SMOOTH));
    backButton.setIcon(backIcon);
    backButton.setBounds(9 * GRID_WIDTH + 90, 2 * GRID_HEIGHT + 40, GRID_WIDTH, GRID_HEIGHT);
    backButton.addMouseListener(mouseController);
    background.add(backButton);

    background.setIcon(
        new ImageIcon(getClass().getClassLoader().getResource("resource/images/background.png")));
    highScoreScreen.add(background, BorderLayout.CENTER);

    JLabel image = new JLabel();
    image.setLayout(null);
    ImageIcon icon =
        new ImageIcon(getClass().getClassLoader().getResource("resource/images/table.png"));
    icon =
        new ImageIcon(
            icon.getImage()
                .getScaledInstance(6 * GRID_WIDTH, GRID_HEIGHT * 10, Image.SCALE_SMOOTH));
    image.setIcon(icon);
    image.setBounds(5 * GRID_WIDTH, 2 * GRID_HEIGHT, GRID_WIDTH * 6, GRID_HEIGHT * 10);
    background.add(image);

    JLabel titleText = new JLabel("High Scores", SwingConstants.CENTER);
    titleText.setFont(gameFont.deriveFont(Font.BOLD, 60));
    titleText.setBounds(GRID_WIDTH + 30, GRID_HEIGHT, GRID_WIDTH * 3, GRID_HEIGHT);
    image.add(titleText);

    scoresLabel = new JLabel();
    scoresLabel.setFont(gameFont.deriveFont(Font.PLAIN, 50));
    scoresLabel.setBounds(2 * GRID_WIDTH + 30, GRID_HEIGHT, 4 * GRID_WIDTH, 9 * GRID_HEIGHT);
    updateHighScoreScreen();
    image.add(scoresLabel, BorderLayout.CENTER);

    this.add(highScoreScreen, "highScore");
  }

  private void updateHighScoreScreen() {
    List<Integer> scores = scoreManager.getScores();
    String scoresText =
        String.format(
            "<html>"
                + "<ol>"
                + "<li> %d </li>"
                + "<li> %d </li>"
                + "<li> %d </li>"
                + "<li> %d </li>"
                + "<li> %d </li>"
                + "</ol>"
                + "</html>",
            scores.get(0), scores.get(1), scores.get(2), scores.get(3), scores.get(4));
    scoresLabel.setText(scoresText);
  }

  public void updateScore() {
    JLabel text = (JLabel) endScreen.getComponent(1); // 0 is the image label, 1 is the text label
    text.setText("Final score: " + gamePanel.getScore());
    scoreManager.addScore(gamePanel.getScore());
    updateHighScoreScreen();
  }

  public void changePanel(String panelName) {
    if (panelName.equals("gamePanel")) gamePanel.reset();

    cardLayout.show(this.getContentPane(), panelName);
  }

  public void exit() {
    System.exit(0);
  }
}
