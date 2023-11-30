package module;

import java.util.Stack;

public class Random {
  public static Stack<Symbols> randomSymbols() {
    Stack<Symbols> symbols = new Stack<>();
    int[] random = new int[3];
    for (int i = 0; i < 3; i++) {
      random[i] = (int) (Math.random() * 5 + 1);
      symbols.push(new Symbols(random[i]));
    }
    return symbols;
  }
}
