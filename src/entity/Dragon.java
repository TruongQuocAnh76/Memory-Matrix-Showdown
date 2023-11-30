package entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Dragon extends Entity{
    private final int INITIAL_HEALTH = 100;
    private final int attack = 1;
    public Dragon(int health) {
        super(health);
        this.attackSprite = new BufferedImage[3];
        this.idleSprite = new BufferedImage[3];
        this.hurtSprite = new BufferedImage[3];
        loadSprites();
    }

    private void loadSprites() {
        try {
            idleSprite[0] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("resource/images_game/dragon_idle1.png"));
            idleSprite[1] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("resource/images_game/dragon_idle2.png"));
            idleSprite[2] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("resource/images_game/dragon_idle3.png"));
            attackSprite[0] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("resource/images_game/dragon_attack1.png"));
            attackSprite[1] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("resource/images_game/dragon_attack2.png"));
            attackSprite[2] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("resource/images_game/dragon_attack3.png"));
            hurtSprite[0] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("resource/images_game/dragon_hurt1.png"));
            hurtSprite[1] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("resource/images_game/dragon_hurt2.png"));
            hurtSprite[2] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("resource/images_game/dragon_hurt3.png"));
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
        g2.drawImage(currentSprite, 1500, 50, 500, 600, null);
    }
}
