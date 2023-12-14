package sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundManager {
  private Clip background, gojoHurt, dragonAttack, dragonIdle, gojoAttack, gojoIdle, gojoWin;

  public SoundManager() {
    try {
      AudioInputStream audioInputStream =
          AudioSystem.getAudioInputStream(
              getClass().getClassLoader().getResource("resource/sounds/background.wav"));
      background = AudioSystem.getClip();
      background.open(audioInputStream);

      AudioInputStream audioInputStream2 =
          AudioSystem.getAudioInputStream(
              getClass().getClassLoader().getResource("resource/sounds/gojo_hurt.wav"));
      gojoHurt = AudioSystem.getClip();
      gojoHurt.open(audioInputStream2);

      AudioInputStream audioInputStream3 =
          AudioSystem.getAudioInputStream(
              getClass().getClassLoader().getResource("resource/sounds/dragon_attack.wav"));
      dragonAttack = AudioSystem.getClip();
      dragonAttack.open(audioInputStream3);

      AudioInputStream audioInputStream4 =
          AudioSystem.getAudioInputStream(
              getClass().getClassLoader().getResource("resource/sounds/dragon_idle.wav"));
      dragonIdle = AudioSystem.getClip();
      dragonIdle.open(audioInputStream4);

      AudioInputStream audioInputStream5 =
          AudioSystem.getAudioInputStream(
              getClass().getClassLoader().getResource("resource/sounds/gojo_attack.wav"));
      gojoAttack = AudioSystem.getClip();
      gojoAttack.open(audioInputStream5);

      AudioInputStream audioInputStream6 =
          AudioSystem.getAudioInputStream(
              getClass().getClassLoader().getResource("resource/sounds/gojo_idle.wav"));
      gojoIdle = AudioSystem.getClip();
      gojoIdle.open(audioInputStream6);

      AudioInputStream audioInputStream7 =
          AudioSystem.getAudioInputStream(
              getClass().getClassLoader().getResource("resource/sounds/gojo_win.wav"));
      gojoWin = AudioSystem.getClip();
      gojoWin.open(audioInputStream7);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  private void restart(Clip clip) {
    clip.setFramePosition(0);
  }

  public void playBackground() {
    restart(background);
    background.loop(Clip.LOOP_CONTINUOUSLY);
  }

  public void playGojoHurt() {
    restart(gojoHurt);
    gojoHurt.start();
  }

  public void playDragonAttack() {
    restart(dragonAttack);
    dragonAttack.start();
  }

  public void playDragonIdle() {
    restart(dragonIdle);
    dragonIdle.start();
  }

  public void playGojoAttack() {
    restart(gojoAttack);
    gojoAttack.start();
  }

  public void playGojoIdle() {
    restart(gojoIdle);
    gojoIdle.start();
  }

  public void playGojoWin() {
    restart(gojoWin);
    gojoWin.start();
  }
  public void stopBackground() {
    if (background.isRunning()) {
      background.stop();
    }
  }
}
