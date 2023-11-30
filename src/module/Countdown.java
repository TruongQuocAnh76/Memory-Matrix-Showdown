package module;

public class Countdown implements Runnable {
  private final int maxTime = 9;
  private int time;
  private Thread thread = new Thread(this);

  public Countdown() {
    time = maxTime;
  }

  public int getTime() {
    return time;
  }

  public void countdown() {
    if (!thread.isAlive()) thread.start();
  }

  @Override
  public void run() {
    time = maxTime;
    while (time > 0) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      time--;
    }
  }
}
