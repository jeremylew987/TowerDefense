package com.se309.game;

import com.badlogic.gdx.graphics.Texture;
import com.se309.render.TextureElement;

public class Enemy extends TextureElement {

    private int position;
    private int id;

    public Enemy(Texture texture, int x, int y, int id) {
        super(texture, x, y);

        position = 0;
        this.id = id;
    }

    public Enemy(Texture texture, int x, int y, int width, int height, int id) {
        super(texture, x, y, width, height);

        position = 0;
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
