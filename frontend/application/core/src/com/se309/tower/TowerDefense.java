package com.se309.tower;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.se309.render.ElementRenderer;
import com.se309.render.Orientation;
import com.se309.render.TextElement;
import com.se309.render.TextureElement;
import com.se309.socket.SocketClient;

/**
 * TowerDefense.java
 *
 * Main entry point to LibGDX project
 */

public class TowerDefense extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	ElementRenderer renderer;

	public TowerDefense(GameConfiguration config) {
		// Create Element Renderer
		 renderer = new ElementRenderer();
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		TextElement textElement = new TextElement("Frontend/Backend Communication Test", 0, 0);
		textElement.setOrientation(Orientation.TopLeft);
		renderer.addElement(textElement);

		TextElement inputElement = new TextElement(">", 0, 70);
		inputElement.setOrientation(Orientation.TopLeft);
		renderer.addElement(inputElement);

		TextElement outputElement = new TextElement("Chat:\n", 1000, 70);
		outputElement.setOrientation(Orientation.TopLeft);
		renderer.addElement(outputElement);

		SocketClient client = new SocketClient("10.30.35.71", 25565);

		MessageReader reader = new MessageReader(client, outputElement);
		reader.start();

		Gdx.input.setInputProcessor(new KeyboardInputProcessor(inputElement, client));

	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();

		renderer.render(batch);

		batch.end();

		if (Gdx.input.justTouched()) {
			Gdx.input.setOnscreenKeyboardVisible(true);
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
