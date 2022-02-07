package com.tergav17.exp1;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Experiment extends ApplicationAdapter {
	SpriteBatch batch;
	Texture[] boing = new Texture[19];
	Texture background;
	Texture scroller;

	float count = 0;
	int frame = 0;

	double x = 1000;
	double y = 500;
	double dx = 15;
	double dy = 0;
	double floor = 50;
	double scale = 0.5;

	int scrollX = 2200;


	@Override
	public void create () {
		batch = new SpriteBatch();
		for (int i = 0; i < 19; i++) boing[i] = new Texture("boing" + i + ".png");
		background = new Texture("background.jpg");
		scroller = new Texture("scroller.png");
	}

	@Override
	public void render () {

		count = count + 0.01F;
		frame++;

		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();

		/* draw background */

		batch.draw(background, 0, -300, 2200, 1080);


		/* end background */

		/* draw scroller */

		batch.draw(scroller, scrollX, 700);
		scrollX -= 5;
		if (scrollX < -4791) scrollX = 2220;

		/* end background * /

		/* begin bounce ops */

		// update physics
		x += dx;
		y += dy;

		// check constraints
		dy = dy - 0.3;
		if (y < floor) dy = Math.abs(dy);
		if (x < -512) {
			scale = 0.25 + (Math.random() * 0.75);
			dx = scale * 15;
			floor = 450 - 400 * scale;
		}
		if (x > 2220 + 256) {
			scale = 0.25 + (Math.random() * 0.75);
			dx = -scale * 15;
			floor = 450 - 400 * scale;

		}
		if (y > 1080 - 512) dy = -1;

		batch.draw(boing[frame % 19], (int) x, (int) y, (int) (256 * scale), (int) (256 * scale));

		/* end bounce ops */
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		for (int i = 0; i < 19; i++) boing[i].dispose();
	}
}
