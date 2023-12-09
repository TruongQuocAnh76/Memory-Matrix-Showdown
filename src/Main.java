import java.awt.*;
import javax.swing.*;

import module.Countdown;
import view.View;

public class Main {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      View view = new View();

      // Bật chế độ fullscreen
      view.setExtendedState(JFrame.MAXIMIZED_BOTH);


    });
  }
}

