package com.se309.render;

public class RenderSettings {

    private float red;
    private float green;
    private float blue;
    private float alpha;

    public RenderSettings() {
        this.red = 0;
        this.green = 0;
        this.blue = 0;
        this.alpha = 1;
    }

    public float getRed() {
        return red;
    }

    public void setRed(float red) {
        this.red = red;
    }

    public float getGreen() {
        return green;
    }

    public void setGreen(float green) {
        this.green = green;
    }

    public float getBlue() {
        return blue;
    }

    public void setBlue(float blue) {
        this.blue = blue;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
}
