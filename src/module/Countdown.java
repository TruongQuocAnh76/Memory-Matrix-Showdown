package module;

public class Countdown implements Runnable {
  public static final int INPUT_TIME = 9;
  public static final int ATTACK_TIME = 1;
  private int time;
  private Thread thread;

  public int getTime() {
    return time;
  }

  public void countdown(int time) {
    thread = new Thread(this);
    this.time = time;
    thread.start();
  }

  public boolean isCounting() {
    return time > 0;
  }

  public void stopCounting() {
    time = 0;
    thread.interrupt();
  }

  @Override
  public void run() {
    while (time > 0) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        return;
      }
      time--;
    }
  }
}
