package com.se309.render;

/**
 * Element.java
 *
 * This class is the top-level class for use in making render-able object
 */

public class Element {

    private int x;
    private int y;

    private int width;
    private int height;

    private Orientation orientation;

    public Element(int x, int y) {
        this.x = x;
        this.y = y;

        orientation = Orientation.TopLeft;
        width = 100;
        height = 100;
    }

    public Element(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        orientation = Orientation.TopLeft;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }
}
