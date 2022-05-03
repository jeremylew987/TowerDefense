package com.se309.render;

/**
 * This class is the top-level class for use in making render-able object
 *
 * @author Gavin Tersteeg
 */

public class Element {

    private int x;
    private int y;

    private int xActual;
    private int yActual;

    private int width;
    private int height;

    private int layer;

    private Orientation orientation;

    /**
     * Default constructor for an element
     * @param x X position
     * @param y Y position
     */
    public Element(int x, int y) {
        this.x = x;
        this.y = y;

        xActual = 0;
        yActual = 0;

        orientation = Orientation.TopLeft;
        width = 100;
        height = 100;

        layer = 0;
    }

    /**
     * More complicated constructor for an element
     * @param x X position
     * @param y Y position
     * @param width Element width
     * @param height Element height
     */
    public Element(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;

        xActual = 0;
        yActual = 0;

        this.width = width;
        this.height = height;

        orientation = Orientation.TopLeft;

        layer = 0;
    }

    /**
     * Returns element X position
     * @return X position
     */
    public int getX() {
        return x;
    }

    /**
     * Sets element X position
     * @param x New X position
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns element Y position
     * @return Y position
     */
    public int getY() {
        return y;
    }

    /**
     * Sets element Y position
     * @param y New Y position
     */
    public void setY(int y) {
        this.y = y;
    }

    public int getXActual() {
        return xActual;
    }

    public void setXActual(int xActual) {
        this.xActual = xActual;
    }

    public int getYActual() {
        return yActual;
    }

    public void setYActual(int yActual) {
        this.yActual = yActual;
    }

    /**
     * Returns element width
     * @return Width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the element width
     * @param width New width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Returns element height
     * @return Height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the element height
     * @param height New height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets the element layer
     * @return Layer
     */
    public int getLayer() {
        return layer;
    }

    /**
     * Sets the element layer
     * @param layer New layer
     */
    public void setLayer(int layer) {
        this.layer = layer;
    }

    /**
     * Returns the element orientation
     * @return Orientation
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * sets the element orientation
     * @param orientation New orientation
     */
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }
}
