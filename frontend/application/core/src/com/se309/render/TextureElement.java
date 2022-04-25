package com.se309.render;

import com.badlogic.gdx.graphics.Texture;

/**
 * Element represented by texture
 *
 * @author Gavin Tersteeg
 */
public class TextureElement extends Element{

    Texture texture;

    /**
     * Simple texture element constructor
     * @param texture Texture
     * @param x X position
     * @param y Y position
     */
    public TextureElement(Texture texture, int x, int y) {
        super(x, y, texture.getWidth(), texture.getHeight());

        this.texture = texture;
    }

    /**
     * Advances texture element constructor
     * @param texture Texture
     * @param x X position
     * @param y Y position
     * @param width Width
     * @param height Height
     */
    public TextureElement(Texture texture, int x, int y, int width, int height) {
        super(x, y, width, height);

        this.texture = texture;
    }

    /**
     * Returns the current texture to render
     * @return Current texture
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * Sets a new texture to render
     * @param texture New texture
     */
    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
