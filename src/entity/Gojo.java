package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Gojo extends Entity {
  public Gojo() {
    super(3);
    idleSprite = new BufferedImage[3];
    attackSprite = new BufferedImage[2];
    hurtSprite = new BufferedImage[2];
    loadSprites();
  }

  private void loadSprites() {
    try {
      idleSprite[0] =
          ImageIO.read(
              getClass()
                  .getClassLoader()
                  .getResourceAsStream("resource/images_game/gojo_idle1.png"));
      idleSprite[1] =
          ImageIO.read(
              getClass()
                  .getClassLoader()
                  .getResourceAsStream("resource/images_game/gojo_idle2.png"));
      idleSprite[2] =
          ImageIO.read(
              getClass()
                  .getClassLoader()
                  .getResourceAsStream("resource/images_game/gojo_idle3.png"));
      attackSprite[0] =
          ImageIO.read(
              getClass()
                  .getClassLoader()
                  .getResourceAsStream("resource/images_game/gojo_attack1.png"));
      attackSprite[1] =
          ImageIO.read(
              getClass()
                  .getClassLoader()
                  .getResourceAsStream("resource/images_game/gojo_attack2.png"));
      hurtSprite[0] =
          ImageIO.read(
              getClass()
                  .getClassLoader()
                  .getResourceAsStream("resource/images_game/gojo_hurt1.png"));
      hurtSprite[1] =
          ImageIO.read(
              getClass()
                  .getClassLoader()
                  .getResourceAsStream("resource/images_game/gojo_hurt2.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void draw(Graphics2D g2) {
    switch (state) {
      case 0: // idle
        currentSprite = idleSprite[spriteNum];
        if (spriteTime == SPRITE_INTERVAL) {
          spriteNum++;
          spriteNum %= 3;
        }
        break;
        // add later
      case 1: // attack
        currentSprite = attackSprite[spriteNum];
        break;
      case 2: // hurt
        currentSprite = hurtSprite[spriteNum];
        break;
    }
    spriteTime++;
    spriteTime %= SPRITE_INTERVAL + 1;
    g2.drawImage(currentSprite, -50, 10, 500, 750, null);
  }
}
