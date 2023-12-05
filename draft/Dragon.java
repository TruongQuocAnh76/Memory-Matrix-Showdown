package game;
import java.util.Stack;

public class Dragon {
    private final int INITIAL_HEALTH = 100;
    private int health;
    private Stack<Integer> weakPoints = new Stack<>();

    public Dragon(int stageNumber) {
        this.health = INITIAL_HEALTH * stageNumber;
    }

    public void attack(Gojo gojo) {
        System.out.println("Dragon attacks!");
        gojo.deductHealth();
    }

    public Stack<Integer> getWeakPoints(int stageNumber) {
        weakPoints.clear();
        for (int i = 0; i < stageNumber; i++) {
            weakPoints.push((int) (Math.random() * stageNumber + 1));
        }
        return weakPoints;
    }

    public int getHealth() {
        return health;
    }

    public void deductHealth(int damage) {
        health -= damage;
    }
}
