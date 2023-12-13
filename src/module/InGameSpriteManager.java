package module;

import javax.swing.*;
import java.awt.*;

public class InGameSpriteManager {
    private static ImageIcon[] symbols = new ImageIcon[6];
    private ImageIcon table;
    public InGameSpriteManager() {
        for (int i = 1; i <= 6; i++)
            symbols[i - 1] = new ImageIcon(getClass().getClassLoader().getResource("resource/images/symbol" + i + ".png"));
        table = new ImageIcon(getClass().getClassLoader().getResource("resource/images/symbol_table.png"));
    }
    public void setSymbolSpriteSize(int width, int height) {
        for (int i = 0; i < 6; i++)
            symbols[i] = new ImageIcon(symbols[i].getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }

    /**
     * Get symbol sprite by symbol's index
     * @param index
     * @return
     */
    public ImageIcon getSymbolSprite(int index) {
        return symbols[index - 1];
    }
    public void setTableSpriteSize(int width, int height) {
        table = new ImageIcon(table.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }
    public ImageIcon getTableSprite() {
        return table;
    }
}
