package draft;

import java.util.ArrayList;

public class Dragon {
  private final int INITIAL_HEALTH = 100;
  private int health;
  private ArrayList<Integer> weakPoints = new ArrayList<Integer>();

  public Dragon(int stageNumber) {
    this.health = INITIAL_HEALTH * stageNumber;
  }

  public void attack(Gojo gojo) {
    System.out.println("Dragon attacks!");
    gojo.deductHealth();
  }
  public ArrayList<Integer> getWeakPoints(int stageNumber) {
    weakPoints.clear();
    for (int i = 0; i < stageNumber; i++) weakPoints.add((int) (Math.random() * stageNumber + 1));
    return weakPoints;
  }

  public int getHealth() {
    return health;
  }

  public void deductHealth(int damage) {
    health -= damage;
  }
}
