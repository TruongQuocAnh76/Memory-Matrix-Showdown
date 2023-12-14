package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Stack;
import javax.imageio.ImageIO;
import javax.swing.*;
import module.Symbols;

public class Gojo extends Entity {
  private final int X_COORDINATE = 50;
  private final int Y_COORDINATE = 110;
  private final int WIDTH = 500;
  private final int HEIGHT = 600;
  private BufferedImage heartFullSprite;
  private BufferedImage heartNullSprite;
  private Stack<Symbols> spells = new Stack<>();

  public Gojo() {
    super(1);
    attack = 50;
    idleSprite = new BufferedImage[MAX_SPRITE_NUMBER];
    attackSprite = new BufferedImage[MAX_SPRITE_NUMBER];
    hurtSprite = new BufferedImage[MAX_SPRITE_NUMBER];
    loadSprites();
  }

  private void loadSprites() {
    try {
      heartFullSprite =
          ImageIO.read(
              getClass().getClassLoader().getResourceAsStream("resource/images/heart_full.png"));
      heartNullSprite =
          ImageIO.read(
              getClass().getClassLoader().getResourceAsStream("resource/images/heart_null.png"));
      heartFullSprite = scaleImage(heartFullSprite, 200, 200);
      heartNullSprite = scaleImage(heartNullSprite, 200, 200);
      for (int i = 0; i < MAX_SPRITE_NUMBER; i++) {
        idleSprite[i] =
            ImageIO.read(
                getClass()
                    .getClassLoader()
                    .getResourceAsStream("resource/images/gojo_idle" + (i + 1) + ".png"));
        attackSprite[i] =
            ImageIO.read(
                getClass()
                    .getClassLoader()
                    .getResourceAsStream("resource/images/gojo_attack" + (i + 1) + ".png"));
        hurtSprite[i] =
            ImageIO.read(
                getClass()
                    .getClassLoader()
                    .getResourceAsStream("resource/images/gojo_hurt" + (i + 1) + ".png"));

        idleSprite[i] = scaleImage(idleSprite[i], WIDTH, HEIGHT);
        attackSprite[i] = scaleImage(attackSprite[i], WIDTH, HEIGHT);
        hurtSprite[i] = scaleImage(hurtSprite[i], WIDTH, HEIGHT);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Stack<Symbols> getSpells() {
    return spells;
  }

  public void setState(int state) {
    this.state = state;
  }

  public int getHealth() {
    return health;
  }

  public void draw(Graphics2D g2) {
    // draw gojo
    switch (state) {
      case 0: // idle
        currentSprite = idleSprite[spriteNum];
        if (spriteTime == SPRITE_INTERVAL) {
          spriteNum++;
          spriteNum %= MAX_SPRITE_NUMBER;
        }
        break;
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
    // g2.drawImage(currentSprite, X_COORDINATE, Y_COORDINATE, WIDTH, HEIGHT, null);
    // draw health bar
    int i = 1;
    while (i <= health) {
      g2.drawImage(heartFullSprite, 150 * i - 150, 0, null);
      //      g2.drawImage(heartFullSprite, 150 * i - 150, 0, 200, 200, null);
      i++;
    }
    while (i <= 3) {
      g2.drawImage(heartNullSprite, 150 * i - 150, 0, null);
      //      g2.drawImage(heartNullSprite, 150 * i - 150, 0, 200, 200, null);
      i++;
    }
  }

  /**
   * adding spells (or spells or what ever it identify) to the stack of spells
   *
   * @param symbolName
   */
  public void castSpell(String symbolName) {
    spells.push(new Symbols(Integer.parseInt(symbolName.substring(6))));
  }

  /** receive 1 damage from dragon, also change sprite from idle to being split in half */
  public void takeDamage() {
    setState(Entity.HURT);
    health--;
  }

  /**
   * deal damage to dragon proportionate to number of correct spells
   *
   * @param correct
   * @param dragon
   */
  public void attack(int correct, Dragon dragon) {
    setState(Entity.ATTACK);
    dragon.takeDamage(correct * attack);
  }
}
