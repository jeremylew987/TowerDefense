package com.se309.input;

import com.badlogic.gdx.InputProcessor;
import com.se309.queue.TouchDownEvent;
import com.se309.queue.TouchEvent;
import com.se309.queue.TouchUpEvent;
import com.se309.render.TextElement;
import com.se309.socket.Message;
import com.se309.socket.SocketClient;
import com.se309.tower.ResourceContext;

import java.io.IOException;

/**
 * Keyboard input processor for data entry in LibGDX.
 *
 * @author Gavin Tersteeg
 */
public class UniversalInputProcessor implements InputProcessor {

    private ResourceContext context;

    /**
     * Default constructor for keyboard input processor
     */
    public UniversalInputProcessor(ResourceContext context) {
        this.context = context;
    }

    public boolean keyDown(int keycode) { return false; }

    public boolean keyUp(int keycode) {
        return false;
    }

    public boolean keyTyped(char character) { return false; }

    public boolean touchDown(int x, int y, int pointer, int button) {
        context.getButtonManager().addInputEvent(new TouchDownEvent(x, y));

        return true;
    }

    public boolean touchUp(int x, int y, int pointer, int button) {
        context.getButtonManager().addInputEvent(new TouchUpEvent(x, y));

        return true;
    }

    public boolean touchDragged(int x, int y, int pointer) {
        context.getButtonManager().addInputEvent(new TouchEvent(x, y));

        return true;
    }

    public boolean mouseMoved(int x, int y) {
        return false;
    }

    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}