package view;

import entity.*;
import java.awt.*;
import javax.swing.*;
import module.Countdown;
import module.InGameSpriteManager;
import module.ScoreManager;

@SuppressWarnings("all")
public class GamePanel extends JPanel implements Runnable {
  private final int fps = 60;
  JPanel symbolPanel = new JPanel();
  private JButton removeButton;
  private boolean isStartOfTheGame = true;
  private View view;
  private Gojo gojo = new Gojo();
  private Dragon dragon = new Dragon(100);
  private Image backgroundImage;
  private Countdown timer = new Countdown();
  public JLabel clockLabel =
      new JLabel() {
        @Override
        public void paintComponent(Graphics g) {
          super.paintComponent(g);
          Graphics2D g2 = (Graphics2D) g;
          g2.setFont(view.gameFont.deriveFont(Font.BOLD, 80));
          g2.drawString(timer.getTime() + "", View.GRID_WIDTH / 3, View.GRID_HEIGHT * 3/2);
        }
      };
  private InGameSpriteManager spriteManager = new InGameSpriteManager();
  private boolean isMemorizePhase = true;
  private boolean isCastingPhase = false;
  private JLabel symbolTable = new JLabel();
  private JPanel inputPanel = new JPanel();
  public int score = 0;

  private JLabel scoreLabel = new JLabel();
  private Thread thread = new Thread(this);

  public GamePanel(View view) {
    this.view = view;
    this.setLayout(null);

    addSymbolTable();

    this.backgroundImage =
        new ImageIcon(getClass().getClassLoader().getResource("resource/images/background.png"))
            .getImage();

    ImageIcon clock =
        new ImageIcon(getClass().getClassLoader().getResource("resource/images/clock.png"));
    clock = new ImageIcon(clock.getImage().getScaledInstance(View.GRID_WIDTH, View.GRID_HEIGHT * 2, Image.SCALE_SMOOTH));
    clockLabel.setIcon(clock);
    clockLabel.setBounds(0, View.GRID_HEIGHT * 14, View.GRID_WIDTH, View.GRID_HEIGHT * 2);
    this.add(clockLabel);

    JLabel exitButton = new JLabel();
    exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    exitButton.setName("back");
    ImageIcon exitIcon =
        new ImageIcon(getClass().getClassLoader().getResource("resource/images/exit_button.png"));
    exitIcon = new ImageIcon(exitIcon.getImage().getScaledInstance(View.GRID_WIDTH * 2, View.GRID_HEIGHT * 2, Image.SCALE_SMOOTH));
    exitButton.setIcon(exitIcon);
    exitButton.setBounds(14 * View.GRID_WIDTH, 14 * View.GRID_HEIGHT, View.GRID_WIDTH * 2, View.GRID_HEIGHT * 2);
    exitButton.addMouseListener(view.mouseController);
    this.add(exitButton);

    // Create and configure the remove button with the transparent image
    removeButton = new JButton();
    removeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    removeButton.setName("removeButton");

    // Load the transparent image
    ImageIcon removeIcon =
        new ImageIcon(getClass().getClassLoader().getResource("resource/images/remove_button.png"));

    // Ensure the image has a transparent background
    removeIcon =
        new ImageIcon(removeIcon.getImage().getScaledInstance(View.GRID_WIDTH * 2, View.GRID_HEIGHT * 2, Image.SCALE_SMOOTH));
    // Set the button to be transparent
    removeButton.setContentAreaFilled(false);
    removeButton.setBorderPainted(false);

    removeButton.setIcon(removeIcon);
    removeButton.setBounds(14 * View.GRID_WIDTH, 11 * View.GRID_HEIGHT, View.GRID_WIDTH * 2, View.GRID_HEIGHT * 2);
    removeButton.addMouseListener(view.mouseController);
    this.add(removeButton);

    scoreLabel.setFont(view.gameFont.deriveFont(Font.BOLD, 24));
    scoreLabel.setForeground(Color.BLACK);
    scoreLabel.setBounds(14 * View.GRID_WIDTH, 0, View.GRID_WIDTH, View.GRID_HEIGHT);
    this.add(scoreLabel);
  }

  public void removeLastSymbol() {
    if (!gojo.getSpells().isEmpty()) {
      gojo.getSpells().pop(); // Remove the last symbol from the stack
      // Remove the last added symbol from the symbolTable
      int lastSymbolIndex = symbolTable.getComponentCount() - 1;
      if (lastSymbolIndex >= 0) {
        symbolTable.remove(lastSymbolIndex);
        //        symbolTable.revalidate();
        //        symbolTable.repaint();
      }
    }
  }

  private void addSymbolTable() {
    // table to receive player input
    inputPanel.setOpaque(false);

    JLabel inputTable = new JLabel();
    inputTable.setLayout(null);
    spriteManager.setTableSpriteSize(8 * View.GRID_WIDTH, 2 * View.GRID_HEIGHT);
    inputTable.setIcon(spriteManager.getTableSprite());
    inputPanel.setBounds(4 * View.GRID_WIDTH, 13 * View.GRID_HEIGHT, 8 * View.GRID_WIDTH, 2 * View.GRID_HEIGHT);
    inputPanel.add(inputTable);

    spriteManager.setSymbolSpriteSize(View.GRID_WIDTH, View.GRID_HEIGHT);
    for (int i = 1; i <= 6; i++) {
      JLabel symbol = new JLabel();
      symbol.addMouseListener(view.mouseController);
      symbol.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      symbol.setName("symbol" + i);
      symbol.setIcon(spriteManager.getSymbolSprite(i));
      symbol.setBounds(View.GRID_WIDTH / 2 + (View.GRID_WIDTH * (i - 1) + View.GRID_WIDTH / 5 * (i - 1)), View.GRID_HEIGHT / 2, View.GRID_WIDTH, View.GRID_HEIGHT);
      inputTable.add(symbol);
    }
    this.add(inputPanel);

    // table to display weakness
    symbolTable.setLayout(null);

    symbolPanel.setBackground(
        new Color(
            0, 0, 0, 0)); // for some reason, setOpaque(false) doesn't work, so i use this instead
    symbolPanel.setBounds(4 * View.GRID_WIDTH, 6 * View.GRID_HEIGHT, 7 * View.GRID_WIDTH, 2 * View.GRID_HEIGHT);
    spriteManager.setTableSpriteSize(7 * View.GRID_WIDTH, 2 * View.GRID_HEIGHT);
    symbolTable.setIcon(spriteManager.getTableSprite());
    symbolPanel.add(symbolTable);
    this.add(symbolPanel);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    gojo.draw(g2);
    dragon.draw(g2);

    g2.setColor(Color.WHITE);
    g2.setFont(new Font("Arial", Font.BOLD, 24));
    g2.drawString("Score: " + score, 1100, 50);
  }

  private void attackPhase() {
    if (!timer.isCounting()) { // end of attack phase, back to memorize phase
      // stop counting
      timer.stopCounting();
      // transit to memorize phase
      isMemorizePhase = true;
      // change state of entities to idle
      gojo.setState(Entity.IDLE);
      dragon.setState(Entity.IDLE);

      // check if game is over
      if (gojo.getHealth() == 0) {
        view.updateScore();
        view.changePanel("endScreen");
        stop();
      }


      memorizePhaseSetup();
    }
  }

  private void memorizePhaseSetup() {
    // show weakness table
    symbolTable.setVisible(true);
    symbolPanel.setVisible(true); // as the panel is the parent of the table, it must be visible too

    inputPanel.setVisible(false); // hide input table

    dragon.revealWeakness();
    // adding weakness symbols to weakness table
    symbolTable.removeAll(); // remove all previous symbols
    spriteManager.setSymbolSpriteSize(View.GRID_WIDTH, View.GRID_HEIGHT);
    for (int i = 0; i < 5; i++) {
      JLabel symbol = new JLabel();
      symbol.addMouseListener(view.mouseController);
      symbol.setName("weakness" + i);
      symbol.setIcon(spriteManager.getSymbolSprite(dragon.getWeakness().get(i).getIndex()));
      symbol.setBounds(View.GRID_WIDTH / 2 + (View.GRID_WIDTH * i + View.GRID_WIDTH / 5 * i), View.GRID_HEIGHT / 2, View.GRID_WIDTH, View.GRID_HEIGHT);
      symbolTable.add(symbol);
    }
    // starts memorize phase countdown
    timer.countdown(Countdown.MEMORIZE_TIME);
  }

  private void castingPhase() {
    if (!timer.isCounting()
        || gojo.getSpells().size() == 5) { // if time's up or player has done inputting
      // transit to attack phase, stop counting if there are time remaining (the moon is not red)
      isCastingPhase = false;
      timer.stopCounting();

      checkInput();

      attackPhaseSetup();
    }
  }

  private void attackPhaseSetup() {
    // hide input table and symbol table
    inputPanel.setVisible(false);
    symbolPanel.setVisible(false);
    symbolTable.setVisible(false);
    // starts attack phase countdown
    timer.countdown(Countdown.RESULT_TIME);
    updateScoreLabel();
  }

  private void checkInput() {
    int correct = 0;

    while (!gojo.getSpells().isEmpty()) {
      if (gojo.getSpells().pop().equals(dragon.getWeakness().pop())) correct++;
    }

    switch (correct) {
      case 0:
        dragon.attack(gojo);
        view.soundManager.playDragonAttack();
        view.soundManager.playGojoHurt();
        break;
      case 5:
        gojo.attack(correct, dragon);
        view.soundManager.playGojoAttack();
        break;
      default:
        gojo.attack(correct, dragon);
        view.soundManager.playGojoAttack();
        dragon.attack(gojo);
        view.soundManager.playDragonAttack();
        view.soundManager.playGojoHurt();
    }

    score += correct * 100;
  }

  private void updateScoreLabel() {
    // Cập nhật hiển thị điểm số trên label
    scoreLabel.setText("Score: " + score);
  }

  private void memorizePhase() {
    if (isStartOfTheGame) {
      isStartOfTheGame = false;
      memorizePhaseSetup();
    }
    if (!timer.isCounting()) { // memorize phase ends, remove weakness table, starts input phase
      // stop counting
      timer.stopCounting();
      // transit to casting phase
      isMemorizePhase = false;
      isCastingPhase = true;

      castingPhaseSetup();
    }
  }

  private void castingPhaseSetup() {
    // show input table
    inputPanel.setVisible(true);
    // clear symbol table for player's input
    symbolTable.removeAll();
    // starts input phase countdown
    timer.countdown(Countdown.INPUT_TIME);
  }

  public void castSpell(String symbolName) {
    // add inputted symbol to table
    spriteManager.setSymbolSpriteSize(View.GRID_WIDTH, View.GRID_HEIGHT);
    JLabel symbol = new JLabel();
    symbol.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    symbol.setName("input" + symbolName);
    symbol.setIcon(spriteManager.getSymbolSprite(Integer.parseInt(symbolName.substring(6))));
    symbol.setBounds(View.GRID_WIDTH / 2 + (View.GRID_WIDTH * gojo.getSpells().size() + View.GRID_WIDTH / 5 * gojo.getSpells().size()), View.GRID_HEIGHT / 2, View.GRID_WIDTH, View.GRID_HEIGHT);
    symbolTable.add(symbol);

    gojo.castSpell(symbolName);
  }

  /** reset the game (when user start the game after pressing the back button) */
  public void reset() {
    isStartOfTheGame = true;
    thread = new Thread(this);
    gojo = new Gojo();
    dragon = new Dragon(100);
    timer = new Countdown();
    isMemorizePhase = true;
    isCastingPhase = false;
    symbolPanel.setVisible(false);
    inputPanel.setVisible(false);
  }

  public void start() {
    thread.start();
  }

  /** exit game, stop the game thread and */
  public void stop() {
    thread.interrupt();
  }

  @Override
  public void run() {
    while (!thread.isInterrupted()) {
      long startTime = System.currentTimeMillis();
      if (isMemorizePhase) memorizePhase();
      else if (isCastingPhase) castingPhase();
      else attackPhase();

      int rand = (int) (Math.random() * 10000);
      if (rand == 69) view.soundManager.playGojoIdle();
      else if (rand == 420) view.soundManager.playDragonIdle();

      long endTime = System.currentTimeMillis();

      long sleepTime = 1000 / fps - (endTime - startTime) * 1000;
      try {
        Thread.sleep(sleepTime > 0 ? sleepTime : 0);
      } catch (InterruptedException e) {
        return;
      }

      startTime = System.nanoTime();
      repaint();
      endTime = System.nanoTime();
      System.out.println(endTime - startTime);
    }
  }

  public int getScore() {
    return score;
  }
}
