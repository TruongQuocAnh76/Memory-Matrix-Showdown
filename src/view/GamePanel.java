package view;

import entity.Dragon;
import entity.Gojo;
import java.awt.*;
import java.util.Stack;
import javax.swing.*;
import module.Countdown;
import module.Symbols;

public class GamePanel extends JPanel {
  private Gojo gojo = new Gojo();
  private Dragon dragon = new Dragon(100);
  private Image backgroundImage;
  private Image clock;
  private Countdown timer = new Countdown();
  private boolean isMemorizePhase = true;
  private Stack<Symbols> weakness;

  public GamePanel() {
    this.setLayout(null);

    addSymbolTable();

    this.backgroundImage =
        new ImageIcon(
                getClass().getClassLoader().getResource("resource/images_game/background.png"))
            .getImage();
    this.clock =
        new ImageIcon(getClass().getClassLoader().getResource("resource/images_game/clock.png"))
            .getImage();
  }

  private void addSymbolTable() {
    JPanel symbolPanel = new JPanel();
    symbolPanel.setOpaque(false);

    JLabel symbolTable = new JLabel();
    symbolTable.setLayout(null);
    ImageIcon icon =
        new ImageIcon(
            getClass().getClassLoader().getResource("resource/images_game/symbol_table.png"));
    icon = new ImageIcon(icon.getImage().getScaledInstance(1500, 1500, Image.SCALE_SMOOTH));
    symbolTable.setIcon(icon);
    symbolPanel.setBounds(200, 400, 1500, 1300);
    symbolPanel.add(symbolTable);

    for (int i = 1; i <= 6; i++) {
      JLabel symbol = new JLabel();
      symbol.setName("symbol" + i);
      icon =
          new ImageIcon(
              getClass().getClassLoader().getResource("resource/images_game/symbol" + i + ".png"));
      icon =
          new ImageIcon(
              icon.getImage()
                  .getScaledInstance(
                      icon.getIconWidth() / 3, icon.getIconHeight() / 3, Image.SCALE_SMOOTH));
      symbol.setIcon(icon);
      symbol.setBounds(100 + (i - 1) * 200, 400, icon.getIconWidth(), icon.getIconHeight());
      symbolTable.add(symbol);
    }
    this.add(symbolPanel);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    gojo.draw(g2);
    dragon.draw(g2);
    g2.drawImage(clock, -40, 800, 250, 250, this);
    if (isMemorizePhase) displayWeakness(g2);
  }

  private void displayWeakness(Graphics2D g2) {
    if (timer.getTime() == 0) isMemorizePhase = false;

    if (timer.getTime() == 9) {
      timer.countdown();
      g2.setFont(new Font("Arial", Font.BOLD, 100));
    }
    g2.drawString(timer.getTime() + "", 58, 960);
  }
}
