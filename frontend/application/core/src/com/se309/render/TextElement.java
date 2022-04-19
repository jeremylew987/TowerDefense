package com.se309.render;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Element for rendering graphical strings
 *
 * @author Gavin Tersteeg
 */
public class TextElement extends Element{

    private String text;

    private BitmapFont font;

    /**
     * Constructor for string
     * @param text String to render
     * @param x X position
     * @param y Y position
     */
    public TextElement(String text, int x, int y) {
        super(x, y);

        this.text = text;
        font = new BitmapFont();
        font.getData().setScale(4.0F);
    }

    /**
     * Returns the text to be rendered
     * @return Rendered text
     */
    public String getText() {
        return text;
    }

    /**
     * Changes what text to be rendered
     * @param text New text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the current font
     * @return Current font
     */
    public BitmapFont getFont() {
        return font;
    }

    /**
     * Changes the current font
     * @param font New font
     */
    public void setFont(BitmapFont font) {
        this.font = font;
    }
}
