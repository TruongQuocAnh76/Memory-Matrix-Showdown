package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;
import javax.imageio.ImageIO;
import module.Symbols;
import view.View;


public class Dragon extends Entity {
  private final int X_COORDINATE = 11 * View.GRID_WIDTH;
  private final int Y_COORDINATE = 4 * View.GRID_HEIGHT;
  private final int WIDTH = 5 * View.GRID_WIDTH;
  private final int HEIGHT = 8 * View.GRID_HEIGHT;


  private Stack<Symbols> weakness = new Stack<>(); // store dragon weakness this turn

  public Dragon() {
    super(1);
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
      for (int i = 0; i < MAX_SPRITE_NUMBER; i++) {
        idleSprite[i] =
                ImageIO.read(
                        getClass()
                                .getClassLoader()
                                .getResourceAsStream("resource/images/dragon_idle" + (i + 1) + ".png"));
        attackSprite[i] =
                ImageIO.read(
                        getClass()
                                .getClassLoader()
                                .getResourceAsStream("resource/images/dragonattack" + (i + 1) + ".png"));
        hurtSprite[i] =
                ImageIO.read(
                        getClass()
                                .getClassLoader()
                                .getResourceAsStream("resource/images/dragonhurt" + (i + 1) + ".png"));

        idleSprite[i] = scaleImage(idleSprite[i], WIDTH, HEIGHT);
        attackSprite[i] = scaleImage(attackSprite[i], WIDTH, HEIGHT);
        hurtSprite[i] = scaleImage(hurtSprite[i], WIDTH, HEIGHT);

        scaleImage(idleSprite[i], WIDTH, HEIGHT);
        scaleImage(attackSprite[i], WIDTH, HEIGHT);
        scaleImage(hurtSprite[i], WIDTH, HEIGHT);
      }
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
        if (spriteTime == SPRITE_INTERVAL) {
          spriteNum++;
          spriteNum %= MAX_SPRITE_NUMBER;
        }
        break;
      case 2: // hurt
        currentSprite = hurtSprite[spriteNum];
        if (spriteTime == SPRITE_INTERVAL) {
          spriteNum++;
          spriteNum %= MAX_SPRITE_NUMBER;
        }
        break;
    }
    spriteTime++;
    spriteTime %= SPRITE_INTERVAL + 1;
    g2.drawImage(currentSprite, X_COORDINATE, Y_COORDINATE, null);
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
    this.setState(Entity.ATTACK);
    gojo.takeDamage();
  }

  /**
   * change state to hurt
   *
   * @param damage
   */
  public void takeDamage(int damage) {
    this.setState(Entity.HURT);
  }
}