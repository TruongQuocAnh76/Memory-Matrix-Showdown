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
       // Loại bỏ các đối tượng trang trí (title bar, border, etc.)

      // Thực hiện một số công việc sau khi tạo đối tượng View (nếu cần)
      // Ví dụ: tạo đối tượng Countdown và bắt đầu đếm ngược
      Countdown countdown = new Countdown();
      countdown.countdown(10); // Bắt đầu đếm ngược từ 10 giây

      // Các dòng mã khác tại đây nếu có thêm tính năng hoặc tác vụ cần thực hiện
    });
  }
}

