package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;
import javax.imageio.ImageIO;
import module.Symbols;

public class Dragon extends Entity {
  private final int X_COORDINATE = 1200;
  private final int Y_COORDINATE = 50;
  private final int WIDTH = 800;
  private final int HEIGHT = 1000;
  private final int INITIAL_HEALTH = 900;

  private Stack<Symbols> weakness = new Stack<>(); // store dragon weakness this turn

  public Dragon(int health) {
    super(health);
    attack = 1;
    this.attackSprite = new BufferedImage[MAX_SPRITE_NUMBER];
    this.idleSprite = new BufferedImage[MAX_SPRITE_NUMBER];
    this.hurtSprite = new BufferedImage[MAX_SPRITE_NUMBER];
    loadSprites();
  }

  public Stack<Symbols> getWeakness() {
    return weakness;
  }

  private void loadSprites() {
    try {
      idleSprite[0] =
          ImageIO.read(
              getClass()
                  .getClassLoader()
                  .getResourceAsStream("images/dragon_idle1.png"));
      idleSprite[1] =
          ImageIO.read(
              getClass()
                  .getClassLoader()
                  .getResourceAsStream("images/dragon_idle2.png"));
      idleSprite[2] =
          ImageIO.read(
              getClass()
                  .getClassLoader()
                  .getResourceAsStream("images/dragon_idle3.png"));
      attackSprite[0] =
          ImageIO.read(
              getClass()
                  .getClassLoader()
                  .getResourceAsStream("images/dragon_attack1.png"));
      attackSprite[1] =
          ImageIO.read(
              getClass()
                  .getClassLoader()
                  .getResourceAsStream("images/dragon_attack2.png"));
      attackSprite[2] =
          ImageIO.read(
              getClass()
                  .getClassLoader()
                  .getResourceAsStream("images/dragon_attack3.png"));
      hurtSprite[0] =
          ImageIO.read(
              getClass()
                  .getClassLoader()
                  .getResourceAsStream("images/dragon_hurt1.png"));
      hurtSprite[1] =
          ImageIO.read(
              getClass()
                  .getClassLoader()
                  .getResourceAsStream("images/dragon_hurt2.png"));
      hurtSprite[2] =
          ImageIO.read(
              getClass()
                  .getClassLoader()
                  .getResourceAsStream("images/dragon_hurt3.png"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void draw(Graphics2D g2) {
    switch (state) {
      case 0: // idle
        currentSprite = idleSprite[spriteNum];
        if (spriteTime == SPRITE_INTERVAL) {
          spriteNum++;
          spriteNum %= MAX_SPRITE_NUMBER;
        }
        break;
        // add later
      case 1: // attack
        currentSprite = attackSprite[spriteNum];
        spriteNum++;
        spriteNum %= MAX_SPRITE_NUMBER;
        break;
      case 2: // hurt
        currentSprite = hurtSprite[spriteNum];
        spriteNum++;
        spriteNum %= MAX_SPRITE_NUMBER;
        break;
    }
    spriteTime++;
    spriteTime %= SPRITE_INTERVAL + 1;
    g2.drawImage(currentSprite, X_COORDINATE, Y_COORDINATE, WIDTH, HEIGHT, null);
  }

  public void setState(int state) {
    this.state = state;
  }

  /** generate random weakness (or symbols or spells targeting weakness) */
  public void revealWeakness() {
    weakness.clear(); // clear previous weakness
    for (int i = 0; i < 5; i++) weakness.push(new Symbols((int) (Math.random() * 6) + 1));
  }

  /**
   * attack, set state to attack
   *
   * @param gojo
   */
  public void attack(Gojo gojo) {
    this.setState(1);
    gojo.takeDamage();
  }

  /**
   * take damage and change sprite to hurt
   *
   * @param damage
   */
  public void takeDamage(int damage) {
    this.setState(2);
    this.health -= damage;
  }
}
