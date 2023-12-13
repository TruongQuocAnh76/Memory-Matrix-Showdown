package view;

import entity.*;
import java.awt.*;
import javax.swing.*;
import module.Countdown;
import module.InGameSpriteManager;
import module.Symbols;

@SuppressWarnings("all")
public class GamePanel extends JPanel {
  private JButton removeButton;
  private boolean isStartOfTheGame = true;
  private View view;
  private Gojo gojo = new Gojo();
  private Dragon dragon = new Dragon(100);
  private Image backgroundImage;
  private Countdown timer = new Countdown();
  private InGameSpriteManager spriteManager = new InGameSpriteManager();
  public JLabel clockLabel =
          new JLabel() {
            @Override
            public void paintComponent(Graphics g) {
              super.paintComponent(g);
              Graphics2D g2 = (Graphics2D) g;
              g2.setFont(new Font("Arial", Font.BOLD, 80));
              g2.drawString(timer.getTime() + "", 78, 128);
            }
          };
  private boolean isMemorizePhase = true;
  private boolean isCastingPhase = false;
  // table to display weakness and user's input
  JPanel symbolPanel = new JPanel();
  private JLabel symbolTable = new JLabel();

  private JPanel inputPanel = new JPanel();

  private final int DEFAULT_SYMBOL_SIZE = 1024;

  public GamePanel(View view) {
    this.view = view;
    this.setLayout(null);

    addSymbolTable();

    this.backgroundImage =
            new ImageIcon(getClass().getClassLoader().getResource("resource/images/background.png")).getImage();

    ImageIcon clock = new ImageIcon(getClass().getClassLoader().getResource("resource/images/clock.png"));
    clock = new ImageIcon(clock.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
    clockLabel.setIcon(clock);
    clockLabel.setBounds(-20, 700, 200, 200);
    this.add(clockLabel);

    JLabel exitButton = new JLabel();
    exitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    exitButton.setName("back");
    ImageIcon exitIcon =
            new ImageIcon(getClass().getClassLoader().getResource("resource/images/exit_button.png"));
    exitIcon = new ImageIcon(exitIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH));
    exitButton.setIcon(exitIcon);
    exitButton.setBounds(1350, 700, 300, 300);
    exitButton.addMouseListener(view.mouseController);
    this.add(exitButton);

    // Create and configure the remove button with the transparent image
    removeButton = new JButton();
    removeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    removeButton.setName("remove");

    // Load the transparent image
    ImageIcon removeIcon = new ImageIcon(
            getClass().getClassLoader().getResource("resource/images/remove_button.png"));

    // Ensure the image has a transparent background
    removeIcon = new ImageIcon(removeIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
    // Set the button to be transparent
    removeButton.setContentAreaFilled(false);
    removeButton.setBorderPainted(false);

    removeButton.setIcon(removeIcon);
    removeButton.setBounds(1400, 600, 200, 200);
    removeButton.addActionListener(e -> removeLastSymbol()); // Add action listener
    this.add(removeButton);
  }
  private void removeLastSymbol() {
    if (!gojo.getSpells().isEmpty()) {
      gojo.getSpells().pop();  // Remove the last symbol from the stack
      // Remove the last added symbol from the symbolTable
      int lastSymbolIndex = symbolTable.getComponentCount() - 1;
      if (lastSymbolIndex >= 0) {
        symbolTable.remove(lastSymbolIndex);
        symbolTable.revalidate();
        symbolTable.repaint();
      }
    }
  }
  private void addSymbolTable() {
    // table to receive player input
    inputPanel.setOpaque(false);

    JLabel inputTable = new JLabel();
    spriteManager.setTableSpriteSize(1200, 1000);
    inputTable.setIcon(spriteManager.getTableSprite());
    inputPanel.setBounds(200, 350, 1200, 1000);
    inputPanel.add(inputTable);

    spriteManager.setSymbolSpriteSize(DEFAULT_SYMBOL_SIZE / 4, DEFAULT_SYMBOL_SIZE / 4);
    for (int i = 1; i <= 6; i++) {
      JLabel symbol = new JLabel();
      symbol.addMouseListener(view.mouseController);
      symbol.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      symbol.setName("symbol" + i);
      symbol.setIcon(spriteManager.getSymbolSprite(i));
      symbol.setBounds(50 + (i - 1) * 170, 280, DEFAULT_SYMBOL_SIZE / 4, DEFAULT_SYMBOL_SIZE / 4);
      inputTable.add(symbol);
    }
    this.add(inputPanel);


    // table to display weakness
    symbolTable.setLayout(null);

    symbolPanel.setBackground(
            new Color(
                    0, 0, 0, 0)); // for some reason, setOpaque(false) doesn't work, so i use this instead
    symbolPanel.setBounds(300, 150, 1000, 800);
    spriteManager.setTableSpriteSize(1000, 800);
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

    if (isMemorizePhase) memorizePhase(g2);
    else if (isCastingPhase) castingPhase(g2);
    else // otherwise is attack phase (cause gojo needs to get sliced up for more than 2 sec
      attackPhase(g2);
  }

  private void attackPhase(Graphics2D g2) {
    if (!timer.isCounting()) { // end of attack phase, back to memorize phase
      // stop counting
      timer.stopCounting();
      // transit to memorize phase
      isMemorizePhase = true;
      // change state of entities to idle
      gojo.setState(Entity.IDLE);
      dragon.setState(Entity.IDLE);

      memorizePhaseSetup();
    }
  }

  private void memorizePhaseSetup() {
    // show weakness table
    symbolTable.setVisible(true);
    symbolPanel.setVisible(
            true); // as the panel is the parent of the table, it must be visible too

    inputPanel.setVisible(false); // hide input table

    dragon.revealWeakness();
    // adding weakness symbols to weakness table
    symbolTable.removeAll(); // remove all previous symbols
    spriteManager.setSymbolSpriteSize(DEFAULT_SYMBOL_SIZE / 5, DEFAULT_SYMBOL_SIZE / 5);
    for (int i = 0; i < 5; i++) {
      JLabel symbol = new JLabel();
      symbol.addMouseListener(view.mouseController);
      symbol.setName("weakness" + i);
      symbol.setIcon(spriteManager.getSymbolSprite(dragon.getWeakness().get(i).getIndex()));
      symbol.setBounds(50 + (i * 170), 225, DEFAULT_SYMBOL_SIZE / 5, DEFAULT_SYMBOL_SIZE / 5);
      symbolTable.add(symbol);
    }
    // starts memorize phase countdown
    timer.countdown(Countdown.MEMORIZE_TIME);
  }

  private void castingPhase(Graphics2D g2) {
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
  }

  private void checkInput() {
    int correct = 0;

    while (!gojo.getSpells().isEmpty()) {
      if (gojo.getSpells().pop().equals(dragon.getWeakness().pop())) correct++;
    }

    switch (correct) {
      case 0:
        dragon.attack(gojo);
        break;
      case 5:
        gojo.attack(correct, dragon);
        break;
      default:
        gojo.attack(correct, dragon);
        dragon.attack(gojo);
    }

    if(gojo.getHealth() == 0) view.changePanel("endScreen");
  }

  private void memorizePhase(Graphics2D g2) {
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
    // draw remaining time
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
    spriteManager.setSymbolSpriteSize(DEFAULT_SYMBOL_SIZE / 5, DEFAULT_SYMBOL_SIZE / 5);
    JLabel symbol = new JLabel();
    symbol.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    symbol.setName("input" + symbolName);
    symbol.setIcon(spriteManager.getSymbolSprite(Integer.parseInt(symbolName.substring(6))));
    symbol.setBounds(50 + (gojo.getSpells().size() * 170), 225, DEFAULT_SYMBOL_SIZE / 5, DEFAULT_SYMBOL_SIZE / 5);
    symbolTable.add(symbol);

    gojo.castSpell(symbolName);
  }
  /**
   * reset the game (when user start the game after pressing the back button)
   */
  public void reset() {
    isStartOfTheGame = true;
    gojo = new Gojo();
    dragon = new Dragon(100);
    timer = new Countdown();
    isMemorizePhase = true;
    isCastingPhase = false;
    symbolPanel.setVisible(false);
    inputPanel.setVisible(false);
  }
}