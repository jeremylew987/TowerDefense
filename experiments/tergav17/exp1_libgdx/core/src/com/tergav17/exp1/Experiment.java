package com.tergav17.exp1;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Experiment extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img[] = new Texture[19];

	float count = 0;
	int frame = 0;

	double x = 1000;
	double y = 500;
	double dx = 15;
	double dy = 0;


	@Override
	public void create () {
		batch = new SpriteBatch();
		for (int i = 0; i < 19; i++) img[i] = new Texture("boing" + i + ".png");

	}

	@Override
	public void render () {

		count = count + 0.01F;
		frame++;

		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();

		/* begin bounce ops */

		// update physics
		x += dx;
		y += dy;

		// check constraints
		dy = dy - 0.3;
		if (y < 0.0) dy = Math.abs(dy);
		if (x < 0.0) dx = Math.abs(dx);
		if (x > 2220 - 256) dx = -Math.abs(dx);
		if (y > 1080 - 512) dy = -1;

		batch.draw(img[frame % 19], (int) x, (int) y, 256, 256);

		/* end bounce ops */
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		for (int i = 0; i < 19; i++) img[i].dispose();
	}
}
