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
  private boolean isDisplayingWeakness = true;
  private Stack<Symbols> weakness;

  public GamePanel() {
    this.setLayout(new BorderLayout());


    // Create a JPanel for the CENTER region
    JPanel centerPanel = new JPanel();
    centerPanel.setOpaque(false);
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
    JLabel placeHolder = new JLabel("placeHolder");
    placeHolder.setPreferredSize(new Dimension(200, 600));
    placeHolder.setOpaque(false);
    centerPanel.add(placeHolder);
    this.add(centerPanel, BorderLayout.CENTER);

    // Create a JPanel for the EAST region
    JPanel eastPanel = new JPanel();
    eastPanel.setOpaque(false);
    eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
    JLabel placeHolder2 = new JLabel("placeHolder2");
    placeHolder2.setPreferredSize(new Dimension(200, 600));
    placeHolder2.setOpaque(false);
    eastPanel.add(placeHolder2);
    this.add(eastPanel, BorderLayout.EAST);

    // Create a JPanel for the WEST region
    JPanel westPanel = new JPanel();
    westPanel.setOpaque(false);
    westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
    JLabel placeHolder3 = new JLabel("placeHolder3");
    placeHolder3.setPreferredSize(new Dimension(200, 600));
    placeHolder3.setOpaque(false);
    westPanel.add(placeHolder3);
    this.add(westPanel, BorderLayout.WEST);

    // Create a JPanel for the NORTH region
    JPanel northPanel = new JPanel();
    northPanel.setOpaque(false);
    northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
    JLabel placeHolder4 = new JLabel("placeHolder4");
    placeHolder4.setPreferredSize(new Dimension(200, 600));
    placeHolder4.setOpaque(false);
    northPanel.add(placeHolder4);
    this.add(northPanel, BorderLayout.NORTH);

    JLabel symbolTable = new JLabel();
    ImageIcon symbolTableImage = new ImageIcon(getClass().getClassLoader().getResource("resource/images_game/symbol_table.png"));
    symbolTableImage = new ImageIcon(symbolTableImage.getImage().getScaledInstance(1000, 900, Image.SCALE_DEFAULT));
    symbolTable.setIcon(symbolTableImage);
    symbolTable.setPreferredSize(new Dimension(600, 800));
    this.add(symbolTable, BorderLayout.SOUTH);

    this.backgroundImage =
        new ImageIcon(
                getClass().getClassLoader().getResource("resource/images_game/background.png"))
            .getImage();
    this.clock =
        new ImageIcon(getClass().getClassLoader().getResource("resource/images_game/clock.png"))
            .getImage();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    gojo.draw(g2);
    dragon.draw(g2);
    g2.drawImage(clock, -40, 800, 250, 250, this);
    if (isDisplayingWeakness && timer.getTime() > 0) {
      if (timer.getTime() == 0) isDisplayingWeakness = false;
      timer.countdown();
      g2.setFont(new Font("TimesRoman", Font.BOLD, 100));
      g2.drawString(timer.getTime() + "", 58, 960);
    }
  }
}
