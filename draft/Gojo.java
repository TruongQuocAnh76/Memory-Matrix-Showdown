package draft;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Gojo {
  private int health = 3;
  private int attack = 100;

  private ArrayList<Integer> attacks = new ArrayList<Integer>();

  public ArrayList<Integer> getAttacks(int stageNumber) {
    attacks.clear();
    ExecutorService executor = java.util.concurrent.Executors.newSingleThreadExecutor();
    Future<?> future =
        executor.submit(
            () -> {
              System.out.print("Casts spells: ");
              Scanner sc = new Scanner(System.in);
              for (int i = 0; i < stageNumber; i++) {
                  int spell = sc.nextInt();
                  attacks.add(spell);
              }
            });
    try {
      future.get(5, java.util.concurrent.TimeUnit.SECONDS);
    } catch (Exception ignored) {}
    return attacks;
  }

  public void deductHealth() {
    health -= 1;
  }

  public int getHealth() {
    return health;
  }

  public void attack(Dragon dragon, int correct) {
    System.out.println("Gojo attacks!");
    dragon.deductHealth(attack * correct);
  }
}