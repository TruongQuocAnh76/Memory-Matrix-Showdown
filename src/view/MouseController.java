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
        if (panel.getName().equals("start")) {
            view.changePanel("gamePanel");
      System.out.println("start");
        } else if (panel.getName().equals("exit")) {
            view.exit();
      System.out.println("exit");
        } else if (panel.getName().equals("highScore")) {
            System.out.println("highScore");
        } else if (panel.getName().equals("help")) {
            System.out.println("help");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}