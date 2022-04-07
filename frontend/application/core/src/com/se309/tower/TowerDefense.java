package com.se309.tower;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.se309.render.ElementRenderer;
import com.se309.render.Orientation;
import com.se309.render.TextElement;
import com.se309.render.TextureElement;

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

		TextureElement text = new TextureElement(img, 0, 0);
		text.setOrientation(Orientation.TopLeft);
		renderer.addElement(text);

		text = new TextureElement(img, 0, 0);
		text.setOrientation(Orientation.TopMiddle);
		renderer.addElement(text);

		text = new TextureElement(img, 0, 0);
		text.setOrientation(Orientation.TopRight);
		renderer.addElement(text);

		text = new TextureElement(img, 0, 0);
		text.setOrientation(Orientation.MiddleRight);
		renderer.addElement(text);

		text = new TextureElement(img, 0, 0);
		text.setOrientation(Orientation.BottomRight);
		renderer.addElement(text);

		text = new TextureElement(img, 0, 0);
		text.setOrientation(Orientation.BottomMiddle);
		renderer.addElement(text);

		text = new TextureElement(img, 0, 0);
		text.setOrientation(Orientation.BottomLeft);
		renderer.addElement(text);

		text = new TextureElement(img, 0, 0);
		text.setOrientation(Orientation.MiddleLeft);
		renderer.addElement(text);

		TextElement textElement = new TextElement("Hello, world!", 0, 0);
		textElement.setOrientation(Orientation.Middle);
		renderer.addElement(textElement);

	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();

		renderer.render(batch);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
