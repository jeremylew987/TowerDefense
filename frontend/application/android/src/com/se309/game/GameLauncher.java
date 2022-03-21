package com.se309.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.se309.tower.GameConfiguration;
import com.se309.tower.TowerDefense;

/**
 * GameLauncher.java
 *
 * This class handles the launching of the LibGDX activity environment
 */

public class GameLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		// Set up the configuration object
		GameConfiguration gameConfig = new GameConfiguration(GameArguments.getSocketServerAddress(), GameArguments.getSocketServerPort());


		// Jump into LibGDX mode
		initialize(new TowerDefense(gameConfig), config);
	}
}
