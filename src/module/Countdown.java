package module;

public class Countdown implements Runnable {
  private int time;
  private Thread thread;
  public static final int MEMORIZE_TIME = 5;
  public static final int INPUT_TIME = 9;
  public static final int RESULT_TIME = 1;
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
    time = -1;
    thread.interrupt();
  }

  @Override
    public void run() {
        try {
            while (time > 0) {
                Thread.sleep(1000);
                time--;
            }
        } catch (InterruptedException e) {
            return;
        }
    }
}
