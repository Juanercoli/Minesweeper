package view;

import java.awt.*;

/**
 * Enum with main colours
 * @author https://github.com/sergiocarp10
 */
public enum Colours {
    PASTEL_BLUE(32, 136, 203),
    PASTEL_PINK(232, 57, 95),
    WHITE(255, 255, 255),
    OBSCURE(41, 47, 54),
    GREEN(0, 130, 0),
    GOLD(255, 215, 0),
    SMOOTH_GRAY(211, 211, 211);

    private final Color color;

    Colours(int red, int green, int blue){
        this.color = new Color(red, green, blue);
    }

    /**
     * Lets you get the desired colour
     * @return the correspondent colour instance
     */
    public Color color() {
        return color;
    }
}
