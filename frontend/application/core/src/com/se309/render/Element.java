package com.se309.render;

/**
 * Element.java
 *
 * This class is the top-level class for use in making render-able object
 */

public class Element {

    private int x;
    private int y;


    public Element(int x, int y) {
        this.x = x;
        this.y = y;
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
}
