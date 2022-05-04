package com.se309.button;

import com.se309.queue.ButtonDownEvent;
import com.se309.queue.ButtonEvent;
import com.se309.queue.TouchDownEvent;
import com.se309.queue.TouchEvent;
import com.se309.queue.TouchUpEvent;
import com.se309.render.Element;
import com.se309.render.Orientation;
import com.se309.tower.ResourceContext;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ButtonManager {

    private static final int BUTTON_BULGE = 5;

    private ConcurrentLinkedQueue<TouchEvent> inputQueue;
    private ResourceContext context;

    private Button focusedButton;

    public ButtonManager(ResourceContext context) {
        this.context = context;

        inputQueue = new ConcurrentLinkedQueue<>();

        focusedButton = null;
    }

    public void addInputEvent(TouchEvent t) {
        inputQueue.add(t);
    }

    public void process() {
        TouchEvent t;

        while (!inputQueue.isEmpty() && (t = inputQueue.remove()) != null) {

            // Search for button
            Button touchedButton = null;

            if (t instanceof TouchDownEvent || t instanceof TouchUpEvent) {
                for (Element e : context.getRenderer().getElements()) {
                    if (e instanceof Button) {
                        Button b = (Button) e;

                        if (b.getXActual() <= t.getX() && b.getXActual() + b.getWidth() >= t.getX() && b.getYActual() <= t.getY() && b.getYActual() + b.getHeight() >= t.getY() ) {
                            touchedButton = b;
                        }
                    }
                }
            }

            if (touchedButton != null && t instanceof TouchDownEvent) {
                if (focusedButton != null) unFocusButton(focusedButton);

                focusButton(touchedButton);

                context.getEventQueue().queue(new ButtonDownEvent(touchedButton.getSignal()));
            }

            if (focusedButton != null && t instanceof TouchUpEvent) {
                if (touchedButton == focusedButton) {
                    context.getEventQueue().queue(new ButtonEvent(touchedButton.getSignal()));
                }

                unFocusButton(focusedButton);
            }

            // Add to the event pipelined when done
            context.getEventQueue().queue(t);

            //System.out.println("Touch: " + t.getX() + ", " + t.getY());
        }
    }

    private void unFocusButton(Button b) {
        focusedButton = null;

        if (b.getOrientation() != Orientation.BottomMiddle && b.getOrientation() != Orientation.Middle && b.getOrientation() != Orientation.TopMiddle) b.setX(b.getX() + BUTTON_BULGE);
        if (b.getOrientation() != Orientation.MiddleLeft && b.getOrientation() != Orientation.Middle && b.getOrientation() != Orientation.MiddleRight) b.setY(b.getY() + BUTTON_BULGE);
        b.setWidth(b.getWidth() - BUTTON_BULGE * 2);
        b.setHeight(b.getHeight() - BUTTON_BULGE * 2);
    }

    private void focusButton(Button b) {
        focusedButton = b;

        if (b.getOrientation() != Orientation.BottomMiddle && b.getOrientation() != Orientation.Middle && b.getOrientation() != Orientation.TopMiddle) b.setX(b.getX() - BUTTON_BULGE);
        if (b.getOrientation() != Orientation.MiddleLeft && b.getOrientation() != Orientation.Middle && b.getOrientation() != Orientation.MiddleRight) b.setY(b.getY() - BUTTON_BULGE);
        b.setWidth(b.getWidth() + BUTTON_BULGE * 2);
        b.setHeight(b.getHeight() + BUTTON_BULGE * 2);
    }

}
