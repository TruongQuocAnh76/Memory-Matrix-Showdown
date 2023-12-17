package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;
import javax.imageio.ImageIO;
import module.Symbols;


public class Dragon extends Entity {
  private final int X_COORDINATE = 800;
  private final int Y_COORDINATE = 100;
  private final int WIDTH = 800;
  private final int HEIGHT = 700;


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
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void draw(Graphics2D g2d) {
        if (state == IDLE) {
            currentSprite = idleSprite[spriteNum]; 
            if (spriteTime == SPRITE_INTERVAL) { 
                spriteNum++; 
                spriteTime = 0; 
                if (spriteNum == MAX_SPRITE_NUMBER) {
                    spriteNum = 0; 
                }
            }
        } else if (state == ATTACK) {
            currentSprite = attackSprite[spriteNum]; 
            if (spriteTime == SPRITE_INTERVAL) { 
                spriteNum++; 
                spriteTime = 0;
                if (spriteNum == MAX_SPRITE_NUMBER) {
                    spriteNum = 0; 
                }
            }
        } else if (state == HURT) { 
            currentSprite = hurtSprite[spriteNum]; 
            if (spriteTime == SPRITE_INTERVAL) { 
                spriteNum++; 
                spriteTime = 0; 
                if (spriteNum == MAX_SPRITE_NUMBER) {
                    spriteNum = 0; 
                }
            }
        }

        spriteTime++; 
        if (spriteTime == SPRITE_INTERVAL) {
            spriteNum++;
            spriteTime = 0; 
            if (spriteNum == MAX_SPRITE_NUMBER) { 
                spriteNum = 0;
            }
        }
        g2d.drawImage(currentSprite, X_COORDINATE, Y_COORDINATE, WIDTH, HEIGHT, null); // vẽ sprite hiện tại
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
   * take damage and change sprite to hurt
   *
   * @param damage
   */
  public void takeDamage(int damage) {
    this.setState(Entity.HURT);
    this.health -= damage;
  }
}
