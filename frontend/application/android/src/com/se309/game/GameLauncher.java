package com.se309.game;

import android.content.SharedPreferences;
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


		final SharedPreferences mPrefs = getSharedPreferences("test",MODE_PRIVATE);
		String temp = mPrefs.getString("token","1cb8af81-92d6-4abc-baf6-8348529577ca");
		gameConfig.setUserLoginToken(temp);
		// Jump into LibGDX mode
		initialize(new TowerDefense(gameConfig), config);
	}
}
