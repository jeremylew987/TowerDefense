package com.se309.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.se309.render.Orientation;
import com.se309.render.TextElement;
import com.se309.render.TextureElement;

public class LobbyScene implements Scene{

    // Textures
    Texture menuTexture;

    // Constants
    private static final int MENU_LEFT_PADDING = 50;
    private static final int MENU_TOP_PADDING = 50;


    @Override
    public void initialize() {
        menuTexture = new Texture("lobby_menu.png");
    }

    @Override
    public void display(SceneContext scene) {

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        // Set the background
        scene.getRenderSettings().setRed(0.1372F);
        scene.getRenderSettings().setGreen(0.2352F);
        scene.getRenderSettings().setBlue(0.3294F);

        TextureElement menuElement = new TextureElement(menuTexture, MENU_LEFT_PADDING, MENU_TOP_PADDING, width - (MENU_LEFT_PADDING * 2), height - (MENU_TOP_PADDING * 2));
        menuElement.setOrientation(Orientation.TopLeft);
        scene.add(menuElement);
    }
}
