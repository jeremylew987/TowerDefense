package com.se309.game;

import com.badlogic.gdx.graphics.Texture;
import com.se309.render.TextureElement;

public class Tower extends TextureElement {

    int id;
    float rotation;


    public Tower(Texture texture, int x, int y, int id) {
        super(texture, x, y);

        this.id = id;
        this.rotation = 0;
    }

    public Tower(Texture texture, int x, int y, int width, int height, int id) {
        super(texture, x, y, width, height);

        this.id = id;
        this.rotation = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
