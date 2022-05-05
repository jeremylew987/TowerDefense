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
import com.se309.scene.GameScene;
import com.se309.scene.LobbyScene;
import com.se309.scene.SceneManager;
import com.se309.socket.Message;
import com.se309.socket.NetworkDataHandler;
import com.se309.socket.SocketClient;

import java.io.IOException;

/**
 * Main entry point to LibGDX project
 *
 * @author Gavin Tersteeg
 */
public class TowerDefense extends ApplicationAdapter {

	private SpriteBatch batch;

	private ResourceContext context;

	private GameTickHandler tickHandler;

	private GameLogicProcessor processor;

	private GameConfiguration config;


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
		processor = new GameLogicProcessor(context);

		tickHandler = new GameTickHandler(processor);

		this.config = config;


	}

	@Override
	public void create() {
		batch = new SpriteBatch();

		// Register all scenes
		LobbyScene lobby = new LobbyScene();
		context.getSceneManager().register("LOBBY", lobby);
		processor.setLobbyScene(lobby);

		GameScene game = new GameScene();
		context.getSceneManager().register("GAME", game);
		processor.setGameScene(game);

		// Display the initial scene
		context.getSceneManager().display("LOBBY");

		Gdx.input.setInputProcessor(new UniversalInputProcessor(context));

		// Set up the client connection stuff

		SocketClient client = new SocketClient("10.30.34.106", 25565);

		//SocketClient client = new SocketClient("10.30.35.148", 25565);
		NetworkDataHandler reader = new NetworkDataHandler(client, context);
		reader.start();

		// Ugh data output
		context.setDataOut(client.getDataOut());

		// Login to backend
		Message m = new Message("na", "na", config.getUserLoginToken());

		try {
			m.serialize().writeDelimitedTo(client.getDataOut());
			client.getDataOut().flush();
		} catch (IOException e) {
			System.out.println("Write failed!");
		}

	}

	@Override
	public void render() {

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
	public void dispose() {
		batch.dispose();

		context.getSceneManager().dispose();
	}
}
