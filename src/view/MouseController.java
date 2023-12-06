package view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class MouseController implements MouseListener {
  private View view;

  public MouseController(View view) {
    this.view = view;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    JLabel panel = (JLabel) e.getSource();
    switch (panel.getName()) {
      case "start":
        view.changePanel("gamePanel");
        break;
      case "exit":
        view.exit();
        break;
      case "highScore":
        // TODO
        System.out.println("highScore");
        break;
      case "help":
        // TODO
        System.out.println("help");
        break;
      case "back":
        view.changePanel("mainMenu");
        break;
      case "symbol1":
      case "symbol2":
      case "symbol3":
      case "symbol4":
      case "symbol5":
      case "symbol6":
        this.view.gamePanel.castSpell(panel.getName());
        break;
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {}

  @Override
  public void mouseReleased(MouseEvent e) {}

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}
}
