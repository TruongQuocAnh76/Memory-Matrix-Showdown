package module;

public class Symbols {
  private int index;

  public Symbols(int index) {
    this.index = index;
  }

  public String getImagePath() {
    return "resource/images_game/symbols/symbol" + index + ".png";
  }
}
