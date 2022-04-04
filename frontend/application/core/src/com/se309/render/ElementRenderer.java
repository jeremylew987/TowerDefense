package com.se309.render;

import com.badlogic.gdx.Gdx;
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

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        for (Element e : elements) {

            // Do x,y calculations
            int x = 0, y = 0;

            // Preset width / height if relevent
            

            // Calculate X orientations
            if (e.getOrientation() == Orientation.TopLeft || e.getOrientation() == Orientation.MiddleLeft || e.getOrientation() == Orientation.BottomLeft) {
                x = e.getX();
            } else if (e.getOrientation() == Orientation.TopRight || e.getOrientation() == Orientation.MiddleRight || e.getOrientation() == Orientation.BottomRight) {
                x = (width - e.getWidth()) - e.getX();
            } else {
                x = (width / 2 - e.getWidth() / 2) + e.getX();
            }

            // Calculate Y orientations
            if (e.getOrientation() == Orientation.TopLeft || e.getOrientation() == Orientation.TopMiddle || e.getOrientation() == Orientation.TopRight) {
                y = (height - e.getHeight()) - e.getY();
            } else if (e.getOrientation() == Orientation.BottomRight || e.getOrientation() == Orientation.BottomMiddle || e.getOrientation() == Orientation.BottomLeft) {
                y = e.getY();
            } else {
                y = (height / 2 - e.getHeight() / 2) - e.getY();
            }

            // If it is a TextureElement...
            if (e instanceof TextureElement) {
                TextureElement es = (TextureElement) e;
                batch.draw(es.texture, x, y, es.getWidth(), es.getHeight());
            }


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
