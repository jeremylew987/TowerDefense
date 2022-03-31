package com.se309.render;

import com.badlogic.gdx.graphics.Texture;

/**
 * TextureElement.java
 *
 * Element represented by texture
 */

public class TextureElement extends Element{

    Texture texture;

    public TextureElement(Texture texture, int x, int y) {
        super(x, y, texture.getWidth(), texture.getHeight());

        this.texture = texture;
    }

    public TextureElement(Texture texture, int x, int y, int width, int height) {
        super(x, y, width, height);

        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
