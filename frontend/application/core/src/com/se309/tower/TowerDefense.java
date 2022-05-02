package com.se309.tower;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.se309.render.ElementRenderer;
import com.se309.render.Orientation;
import com.se309.render.RenderSettings;
import com.se309.render.TextElement;
import com.se309.scene.LobbyScene;
import com.se309.scene.SceneManager;
import com.se309.socket.NetworkDataHandler;
import com.se309.socket.SocketClient;

import jdk.internal.loader.Resource;

/**
 * Main entry point to LibGDX project
 *
 * @author Gavin Tersteeg
 */
public class TowerDefense extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	ResourceContext context;


	/**
	 * Default constructor for TowerDefense activity
	 * @param config Game configuration object
	 */
	public TowerDefense(GameConfiguration config) {

		// Create Resource Context
		context = new ResourceContext();

		// Populate Resource Context
		ElementRenderer renderer = new ElementRenderer();
		context.setRenderer(renderer);

		RenderSettings renderSettings = new RenderSettings();
		context.setRenderSettings(renderSettings);

		SceneManager sceneManager = new SceneManager(context);
		context.setSceneManager(sceneManager);

	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		// Register all scenes
		context.getSceneManager().register("LOBBY", new LobbyScene());

		// Display the initial scene
		context.getSceneManager().display("LOBBY");

		Gdx.input.setInputProcessor(new KeyboardInputProcessor());

	}

	@Override
	public void render () {
		ScreenUtils.clear(context.getRenderSettings().getRed(), context.getRenderSettings().getGreen(), context.getRenderSettings().getBlue(), context.getRenderSettings().getAlpha());
		batch.begin();

		context.getRenderer().render(batch);

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
