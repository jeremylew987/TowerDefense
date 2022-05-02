package com.se309.tower;

import com.badlogic.gdx.InputProcessor;
import com.se309.render.TextElement;
import com.se309.socket.Message;
import com.se309.socket.SocketClient;

import java.io.IOException;

/**
 * Keyboard input processor for data entry in LibGDX.
 *
 * @author Gavin Tersteeg
 */
public class KeyboardInputProcessor implements InputProcessor {

    /**
     * Default constructor for keyboard input processor
     */
    public KeyboardInputProcessor() {
        /*
        this.textbox = element;
        this.client = client;

        Message m = new Message("na", "na", "1cb8af81-92d6-4abc-baf6-8348529577ca");

        try {
            m.serialize().writeDelimitedTo(client.getDataOut());
            client.getDataOut().flush();
        } catch (IOException e) {
            System.out.println("Write failed!");
        }
        */

    }

    public boolean keyDown(int keycode) {

        return false;
    }

    public boolean keyUp(int keycode) {
        return false;
    }

    public boolean keyTyped(char character) {

        /*
        if (character == 10) {

            Message m = new Message("user", "chat", textbox.getText().substring(1));

            try {
                System.out.println(textbox.getText());

                m.serialize().writeDelimitedTo(client.getDataOut());
                client.getDataOut().flush();
            } catch (IOException e) {
                System.out.println("Write failed!");
            }

            textbox.setText(">");

        } else {
            if (character >= 0x20 && character <= 0x7E)
                textbox.setText(textbox.getText() + character);

        }

         */
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