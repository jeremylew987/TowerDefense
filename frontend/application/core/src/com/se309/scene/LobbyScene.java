package com.se309.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.se309.render.Orientation;
import com.se309.render.TextElement;
import com.se309.render.TextureElement;

public class LobbyScene implements Scene{

    // Textures
    Texture menuTexture;

    // Fonts
    BitmapFont boldLarge;

    // Constants
    private static final int MENU_LEFT_PADDING = 50;
    private static final int MENU_TOP_PADDING = 50;


    @Override
    public void initialize() {
        // Init textures
        menuTexture = new Texture("textures/lobby_menu.png");

        // Init fonts
        FreeTypeFontGenerator boldGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/RobotoCondensed-Bold.ttf"));
        FreeTypeFontParameter boldParameter = new FreeTypeFontParameter();
        boldParameter.size = 64;
        boldParameter.borderColor = Color.BLACK;
        boldParameter.borderWidth = 2;

        boldLarge = boldGenerator.generateFont(boldParameter);

        boldGenerator.dispose();
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

        TextElement lobbySettingsLabel = new TextElement("Lobby Settings", 350, 35);
        lobbySettingsLabel.setOrientation(Orientation.TopRight);
        lobbySettingsLabel.setFont(boldLarge);
        scene.add(lobbySettingsLabel);
    }

    @Override
    public void dispose() {
        menuTexture.dispose();
    }
}
