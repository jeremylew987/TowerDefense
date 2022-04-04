package com.se309.render;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * TextElement.java
 *
 * Allows for strings to be rendered as elements
 */

public class TextElement extends Element{

    private String text;

    private BitmapFont font;

    public TextElement(String text, int x, int y) {
        super(x, y);

        this.text = text;
        font = new BitmapFont();
        font.getData().setScale(4.0F);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public BitmapFont getFont() {
        return font;
    }

    public void setFont(BitmapFont font) {
        this.font = font;
    }
}
