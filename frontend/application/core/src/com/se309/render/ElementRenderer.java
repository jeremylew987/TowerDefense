package com.se309.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.se309.game.Tower;

import java.util.ArrayList;

/**
 * This class stores displayed elements, and renders them as needed
 *
 * @author Gavin Tersteeg
 */

public class ElementRenderer {

    private ArrayList<Element> elements;

    private GlyphLayout layout;

    /**
     * Default constructor for ElementRenderer
     */
    public ElementRenderer() {
        elements = new ArrayList<>();

        // Layout for string width/height calculations
        layout = new GlyphLayout();
    }

    /**
     * Renders all of the stored elements into a batch
     * @param batch SpriteBatch for incoming sprites
     */
    public void render(SpriteBatch batch) {

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        int totalElements = elements.size();
        int renderedElements = 0;
        int currentLayer = 0;

        while (renderedElements < totalElements) {

            for (Element e : elements) {

                // If this isn't the correct layer to render the element on, just continue
                if (e.getLayer() != currentLayer) continue;
                else renderedElements++;

                // Do x,y calculations
                int x = 0, y = 0;

                // Preset width / height if relevant
                if (e instanceof TextElement) {
                    // TextElement width / height preset
                    TextElement es = (TextElement) e;

                    layout.setText(es.getFont(), ((TextElement) e).getText());
                    es.setWidth((int) layout.width);
                    layout.setText(es.getFont(), "A");
                    es.setHeight((int) layout.height);


                }


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


                if (e instanceof TextureElement) {
                    float rotation = 0F;

                    if (e instanceof Tower) {
                        rotation = ((Tower) e).getRotation();
                    }

                    // If it is a TextureElement...
                    TextureElement es = (TextureElement) e;
                    //batch.draw(es.texture, x, y, es.getWidth(), es.getHeight());
                    batch.draw(es.texture, x, y, x + es.getWidth() / 2, x + es.getHeight() / 2, es.getWidth(), es.getHeight(), 1F, 1F, rotation, 0, 0, es.texture.getWidth(), es.texture.getHeight(), false, false);

                    // Set actual positions
                    es.setXActual(x);
                    es.setYActual((height - e.getHeight()) - y);
                } else if (e instanceof TextElement) {
                    // If it is a TextElement...
                    TextElement es = (TextElement) e;

                    es.getFont().draw(batch, es.getText(), x, y);
                }

            }

            // Advance currently rendered layer
            currentLayer++;
        }

    }

    /**
     * Adds an element to be rendered
     * @param e Element to add
     */
    public void addElement(Element e) {
        elements.add(e);
    }

    /**
     * Removes an element from the render queue
     * @param e Element to remove
     */
    public void removeElement(Element e) {
        elements.remove(e);
    }

    /**
     * Returns a list of all the elements in the render queue
     * @return List of elements
     */
    public ArrayList<Element> getElements() {
        return elements;
    }
}
