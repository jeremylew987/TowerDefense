package com.se309.tower;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.se309.render.ElementRenderer;
import com.se309.render.TextureElement;

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

		renderer.addElement(new TextureElement(img, 0, 0));

	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		//batch.draw(img, 0, 0);
		renderer.render(batch);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
