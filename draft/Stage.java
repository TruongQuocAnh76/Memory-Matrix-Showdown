package draft;

import java.util.ArrayList;
import java.util.Objects;

public class Stage {
  private final int symbolShowTime = 5;
  private final int inputTime = 5;

  private int stageNumber = 1;
  private ArrayList<Integer> weakPoints = new ArrayList<Integer>();
  private ArrayList<Integer> attacks = new ArrayList<Integer>();
  private Gojo gojo = new Gojo();
  private Dragon dragon = new Dragon(stageNumber);

  public void game() {
    System.out.println("Memorize the symbols for " + symbolShowTime + " seconds");
    System.out.println(
        "Enter the symbols that appeared, separated by spaces, in " + inputTime + " seconds");

    while (true) {
      System.out.println("Stage " + stageNumber);
      System.out.println(
          "Gojo health " + gojo.getHealth() + " Dragon health " + dragon.getHealth());
      weakPoints = dragon.getWeakPoints(stageNumber);
      System.out.print("Weak Points: " + weakPoints);
      sleep(symbolShowTime);
      System.out.print("\r");
      attacks = gojo.getAttacks(stageNumber);

      if (!endStage()) break;
    }
  }

  private boolean endStage() {
    int correct = compare();
    if (correct == stageNumber) gojo.attack(dragon, correct);
    else if (correct == 0) dragon.attack(gojo);
    else {
      gojo.attack(dragon, correct);
      dragon.attack(gojo);
    }

    if (gojo.getHealth() <= 0) {
      System.out.println("You lost!");
      return false;
    } else if (dragon.getHealth() <= 0) {
      stageNumber++;
      dragon = new Dragon(stageNumber);
    }
    return true;
  }

  private int compare() {
    int correct = 0;
    for (int i = 0; i < stageNumber && i < attacks.size(); i++)
      if (Objects.equals(attacks.get(i), weakPoints.get(i))) correct++;
    return correct;
  }

  private void sleep(int sleepTime) {
    try {
      Thread.sleep(sleepTime * 1000);
    } catch (InterruptedException ignored) {
    }
  }
}
