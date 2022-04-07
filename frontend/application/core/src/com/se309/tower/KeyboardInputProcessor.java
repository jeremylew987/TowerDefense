package com.se309.tower;

import com.badlogic.gdx.InputProcessor;
import com.se309.render.TextElement;

public class KeyboardInputProcessor implements InputProcessor {

    private TextElement textbox;

    public KeyboardInputProcessor(TextElement element) {
        textbox = element;
    }

    public boolean keyDown(int keycode) {

        return false;
    }

    public boolean keyUp(int keycode) {
        return false;
    }

    public boolean keyTyped(char character) {
        System.out.println("Typed " + (int) character);

        textbox.setText(textbox.getText() + character);

        return false;
    }

    public boolean touchDown(int x, int y, int pointer, int button) {
        return false;
    }

    public boolean touchUp(int x, int y, int pointer, int button) {
        return false;
    }

    public boolean touchDragged(int x, int y, int pointer) {
        return false;
    }

    public boolean mouseMoved(int x, int y) {
        return false;
    }

    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}