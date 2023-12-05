package view;

import entity.Dragon;
import entity.Gojo;
import java.awt.*;
import javax.swing.*;
import module.Countdown;

@SuppressWarnings("all")
public class GamePanel extends JPanel {
  JPanel weaknessPanel = new JPanel();
  private boolean isStartOfTheGame = true;
  private View view;
  private Gojo gojo = new Gojo();
  private Dragon dragon = new Dragon(100);
  private Image backgroundImage;
  private Countdown timer = new Countdown();
  private boolean isMemorizePhase = true;
  private boolean isCastingPhase = false;
  private JLabel weaknessTable = new JLabel();
  private JPanel inputPanel = new JPanel();

  public GamePanel(View view) {
    this.view = view;
    this.setLayout(null);

    addSymbolTable();

    this.backgroundImage =
        new ImageIcon(
                getClass().getClassLoader().getResource("images/background.png"))
            .getImage();

    // TDOO: setting clock as icon makes the timer text goes under it, fix this
    ImageIcon clock = new ImageIcon(getClass().getClassLoader().getResource("images/clock.png"));
    clock = new ImageIcon(clock.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH));
    JLabel clockLabel = new JLabel();
    clockLabel.setIcon(clock);
    clockLabel.setBounds(-40, 800, 250, 250);
    this.add(clockLabel);
  }

  private void addSymbolTable() {
    // table to receive player input
    inputPanel.setOpaque(false);

    JLabel inputTable = new JLabel();
    ImageIcon icon =
        new ImageIcon(
            getClass().getClassLoader().getResource("images/symbol_table.png"));
    icon = new ImageIcon(icon.getImage().getScaledInstance(1500, 1500, Image.SCALE_SMOOTH));
    inputTable.setIcon(icon);
    inputPanel.setBounds(200, 250, 1500, 1300);
    inputPanel.add(inputTable);

    for (int i = 1; i <= 6; i++) {
      JLabel symbol = new JLabel();
      symbol.addMouseListener(view.mouseController);
      symbol.setName("symbol" + i);
      icon =
          new ImageIcon(
              getClass().getClassLoader().getResource("images/symbol" + i + ".png"));
      icon =
          new ImageIcon(
              icon.getImage()
                  .getScaledInstance(
                      icon.getIconWidth() / 3, icon.getIconHeight() / 3, Image.SCALE_SMOOTH));
      symbol.setIcon(icon);
      symbol.setBounds(30 + (i - 1) * 220, 450, icon.getIconWidth(), icon.getIconHeight());
      inputTable.add(symbol);
    }
    this.add(inputPanel);

    // table to display weakness
    weaknessTable.setLayout(null);

    weaknessPanel.setBackground(Color.BLACK);
    weaknessPanel.setBackground(
        new Color(
            0, 0, 0, 0)); // for some reason, setOpaque(false) doesn't work, so i use this instead
    weaknessPanel.setBounds(300, 0, 1200, 1200);
    ImageIcon weaknessIcon =
        new ImageIcon(
            getClass().getClassLoader().getResource("images/symbol_table.png"));
    weaknessIcon =
        new ImageIcon(weaknessIcon.getImage().getScaledInstance(1200, 1200, Image.SCALE_SMOOTH));
    weaknessTable.setIcon(weaknessIcon);
    weaknessPanel.add(weaknessTable);
    this.add(weaknessPanel);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    gojo.draw(g2);
    dragon.draw(g2);

    if (isMemorizePhase) {
      memorizePhase(g2);

    } else if (isCastingPhase) {
      castingPhase(g2);
    } else { // otherwise is attack phase (cause gojo needs to get sliced up for more than 2 sec
      attackPhase(g2);
    }
  }

  private void attackPhase(Graphics2D g2) {
    if (!timer.isCounting()) { // end of attack phase, back to memorize phase
      // stop counting
      timer.stopCounting();
      // transit to memorize phase
      isMemorizePhase = true;
      // change state of entities to idle
      gojo.setState(0);
      dragon.setState(0);

      memorizePhaseSetup();

    } else drawTime(g2);
  }

  private void memorizePhaseSetup() {
    // show weakness table
    weaknessTable.setVisible(true);
    weaknessPanel.setVisible(
        true); // as the panel is the parent of the table, it must be visible too

    inputPanel.setVisible(false); // hide input table

    dragon.revealWeakness();
    // adding weakness symbols to weakness table
    weaknessTable.removeAll(); // remove all previous symbols
    for (int i = 0; i < 5; i++) {
      JLabel symbol = new JLabel();
      symbol.setName("weakness" + i);
      ImageIcon icon =
          new ImageIcon(
              getClass().getClassLoader().getResource(dragon.getWeakness().get(i).getImagePath()));
      icon =
          new ImageIcon(
              icon.getImage()
                  .getScaledInstance(
                      icon.getIconWidth() / 4, icon.getIconHeight() / 4, Image.SCALE_SMOOTH));
      symbol.setIcon(icon);
      symbol.setBounds(30 + (i) * 220, 358, icon.getIconWidth(), icon.getIconHeight());
      weaknessTable.add(symbol);
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
    } else drawTime(g2);
  }

  private void attackPhaseSetup() {
    // hide input table
    inputPanel.setVisible(false);
    // starts attack phase countdown
    timer.countdown(Countdown.RESULT_TIME);
  }

  private void checkInput() {
    int correct = 0;

    // DIT CONMEEE NGU VCL WTF AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
    while(!gojo.getSpells().isEmpty())
      if(gojo.getSpells().pop().equals(dragon.getWeakness().pop())) correct++;

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
  }

  private void memorizePhase(Graphics2D g2) {
    if (isStartOfTheGame){
      isStartOfTheGame = false;
      memorizePhaseSetup();
    }
    if (!timer.isCounting()) { // memorize phase ends, remove weakness table, starts input phase
      // stop counting
      timer.stopCounting();
      // transit to casting phase
      isMemorizePhase = false;
      isCastingPhase = true;

      inputPhaseSetup();
    }
    // draw remaining time
    else drawTime(g2);
  }

  private void inputPhaseSetup() {
    // hide weakness table
    weaknessPanel.setVisible(false);
    weaknessTable.setVisible(false);
    // show input table
    inputPanel.setVisible(true);

    // starts input phase countdown
    timer.countdown(Countdown.INPUT_TIME);
  }

  private void drawTime(Graphics2D g2) {
    g2.setFont(new Font("Arial", Font.BOLD, 100));
    g2.drawString(timer.getTime() + "", 58, 960);
  }

  public void castSpell(String symbolName) {
    gojo.castSpell(symbolName);
  }
}
