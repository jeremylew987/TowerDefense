package com.se309.button;

import com.badlogic.gdx.graphics.Texture;
import com.se309.render.TextureElement;

public class Button extends TextureElement {

    private int signal;

    public Button(Texture texture, int x, int y, int signal) {
        super(texture, x, y);

        this.signal = signal;
    }

    public Button(Texture texture, int x, int y, int width, int height, int signal) {
        super(texture, x, y, width, height);

        this.signal = signal;
    }

    public int getSignal() {
        return signal;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }
}
