package view;

import java.awt.*;
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
    JComponent component = (JComponent) e.getSource();
    switch (component.getName()) {
      case "start":
        view.changePanel("gamePanel");
        view.gamePanel.start();
        view.soundManager.stopBackground();
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
        view.showHelpImage();
        break;
      case "back":
        view.changePanel("mainMenu");
        view.gamePanel.stop();
        break;
      case "symbol1":
      case "symbol2":
      case "symbol3":
      case "symbol4":
      case "symbol5":
      case "symbol6":
        this.view.gamePanel.castSpell(component.getName());
        break;
      default: // from end screen to main menu
        this.view.changePanel("mainMenu");
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
