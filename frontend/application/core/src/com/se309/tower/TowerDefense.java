package com.se309.tower;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.se309.button.ButtonManager;
import com.se309.game.GameLogicProcessor;
import com.se309.game.GameTickHandler;
import com.se309.input.UniversalInputProcessor;
import com.se309.queue.GameEventQueue;
import com.se309.render.ElementRenderer;
import com.se309.render.RenderSettings;
import com.se309.scene.LobbyScene;
import com.se309.scene.SceneManager;

/**
 * Main entry point to LibGDX project
 *
 * @author Gavin Tersteeg
 */
public class TowerDefense extends ApplicationAdapter {

	private SpriteBatch batch;

	private ResourceContext context;

	private GameTickHandler tickHandler;


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

		ButtonManager buttonManager = new ButtonManager(context);
		context.setButtonManager(buttonManager);

		GameEventQueue eventQueue = new GameEventQueue();
		context.setEventQueue(eventQueue);

		// Set Up Game Logic Stuff
		GameLogicProcessor processor = new GameLogicProcessor(context);

		tickHandler = new GameTickHandler(processor);


	}

	@Override
	public void create () {
		batch = new SpriteBatch();

		// Register all scenes
		context.getSceneManager().register("LOBBY", new LobbyScene());

		// Display the initial scene
		context.getSceneManager().display("LOBBY");

		Gdx.input.setInputProcessor(new UniversalInputProcessor(context));

	}

	@Override
	public void render () {

		tickHandler.onRedraw();

		ScreenUtils.clear(context.getRenderSettings().getRed(), context.getRenderSettings().getGreen(), context.getRenderSettings().getBlue(), context.getRenderSettings().getAlpha());
		batch.begin();

		context.getRenderer().render(batch);

		batch.end();

		/*if (Gdx.input.justTouched()) {
			Gdx.input.setOnscreenKeyboardVisible(true);
		}*/
	}
	
	@Override
	public void dispose () {
		batch.dispose();

		context.getSceneManager().dispose();
	}
}
