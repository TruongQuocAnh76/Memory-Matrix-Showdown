package module;

public class Symbols {
  private int index;

  public Symbols(int index) {
    this.index = index;
  }

  public int getIndex() {
    return index;
  }

  @Override
  public boolean equals(Object obj) {
    Symbols symbol = (Symbols) obj;
    return this.index == symbol.index;
  }

  @Override
  public String toString() {
    return "symbol" + index;
  }
}
