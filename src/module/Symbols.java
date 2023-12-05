package module;

public class Symbols {
  private int index;

  public Symbols(int index) {
    this.index = index;
  }

  public String getImagePath() {
    return "images/symbol" + index + ".png";
  }
  @Override
  public String toString() {
    return "symbol" + index;
  }
}
