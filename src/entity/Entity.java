package entity;
import java.awt.image.BufferedImage;

public class Entity {
  int health, attack, spriteNum, spriteTime = 1;
  int state = 0; // 0 = idle, 1 = attack, 2 = hurt
  final int SPRITE_INTERVAL = 3;
  BufferedImage[] idleSprite, attackSprite, hurtSprite;
  BufferedImage currentSprite;
  final int MAX_SPRITE_NUMBER= 3;
  public static final int IDLE = 0;
  public static final int ATTACK = 1;
  public static final int HURT = 2;

  public Entity(int health) {
    this.health = health;
  }
}

