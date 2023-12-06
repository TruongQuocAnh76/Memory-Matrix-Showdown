package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Stack;
import javax.imageio.ImageIO;
import module.Symbols;

public class Gojo extends Entity {
  private final int X_COORDINATE = -50;
  private final int Y_COORDINATE = 100;
  private final int WIDTH = 500;
  private final int HEIGHT = 750;
  private Stack<Symbols> spells = new Stack<>();

  public Gojo() {
    super(3);
    attack = 50;
    idleSprite = new BufferedImage[MAX_SPRITE_NUMBER];
    attackSprite = new BufferedImage[MAX_SPRITE_NUMBER];
    hurtSprite = new BufferedImage[MAX_SPRITE_NUMBER];
    loadSprites();
  }

  private void loadSprites() {
    try {
      idleSprite[0] =
          ImageIO.read(
              getClass()
                  .getClassLoader()
                  .getResourceAsStream("images/gojo_idle1.png"));
      idleSprite[1] =
          ImageIO.read(
              getClass()
                  .getClassLoader()
                  .getResourceAsStream("images/gojo_idle2.png"));
      idleSprite[2] =
          ImageIO.read(
              getClass()
                  .getClassLoader()
                  .getResourceAsStream("images/gojo_idle3.png"));
      attackSprite[0] =
          ImageIO.read(
              getClass()
                  .getClassLoader()
                  .getResourceAsStream("images/gojo_attack1.png"));
      attackSprite[1] =
          ImageIO.read(
              getClass()
                  .getClassLoader()
                  .getResourceAsStream("images/gojo_attack2.png"));
      attackSprite[2] =
          ImageIO.read(
              getClass()
                  .getClassLoader()
                  .getResourceAsStream("images/gojo_attack3.png"));
      hurtSprite[0] =
          ImageIO.read(
              getClass()
                  .getClassLoader()
                  .getResourceAsStream("images/gojo_hurt1.png"));
      hurtSprite[1] =
          ImageIO.read(
              getClass()
                  .getClassLoader()
                  .getResourceAsStream("images/gojo_hurt2.png"));
      hurtSprite[2] =
          ImageIO.read(
              getClass()
                  .getClassLoader()
                  .getResourceAsStream("images/gojo_hurt3.png"));
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

  public void draw(Graphics2D g2) {
    switch (state) {
      case 0: // idle
        currentSprite = idleSprite[spriteNum];
        if (spriteTime == SPRITE_INTERVAL) {
          spriteNum++;
          spriteNum %= MAX_SPRITE_NUMBER;
        }
        break;
        // TODO
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
    setState(2);
    health--;
  }

  /**
   * deal damage to dragon proportionate to number of correct spells
   *
   * @param correct
   * @param dragon
   */
  public void attack(int correct, Dragon dragon) {
    setState(1);
    dragon.takeDamage(correct * attack);
  }
}
