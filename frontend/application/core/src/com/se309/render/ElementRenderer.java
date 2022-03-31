package com.se309.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * ElementRenderer.java
 *
 * This class stores displayed elements, and renders them as needed
 */

public class ElementRenderer {

    private ArrayList<Element> elements;

    public ElementRenderer() {
        elements = new ArrayList<>();
    }

    /**
     * Renders all of the stored elements into a batch
     * @param batch
     */
    public void render(SpriteBatch batch) {

        for (Element e : elements) {

        }

    }

    public void addElement(Element e) {
        elements.add(e);
    }

    public void removeElement(Element e) {
        elements.remove(e);
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public void setElements(ArrayList<Element> elements) {
        this.elements = elements;
    }
}
