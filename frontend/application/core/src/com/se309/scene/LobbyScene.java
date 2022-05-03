package com.se309.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.se309.button.Button;
import com.se309.render.Orientation;
import com.se309.render.TextElement;
import com.se309.render.TextureElement;

public class LobbyScene implements Scene{

    // Textures
    Texture menuTexture;
    Texture startButtonTexture;
    Texture startSkirtTexture;
    Texture mapIconTexture;
    Texture backTexture;
    Texture forwardTexture;

    // Fonts
    BitmapFont boldLarge;
    BitmapFont regularLarge;


    // Constants
    private static final int MENU_LEFT_PADDING = 50;
    private static final int MENU_TOP_PADDING = 50;

    @Override
    public void initialize() {
        // Init textures
        menuTexture = new Texture("textures/lobby_menu.png");
        startButtonTexture = new Texture("textures/lobby_startbutton.png");
        startSkirtTexture = new Texture("textures/lobby_startskirt.png");
        mapIconTexture = new Texture("textures/lobby_mapicon.png");
        backTexture = new Texture("textures/lobby_backbutton.png");
        forwardTexture = new Texture("textures/lobby_forwardbutton.png");

        // Init fonts
        FreeTypeFontGenerator boldGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/RobotoCondensed-Bold.ttf"));
        FreeTypeFontParameter boldParameter = new FreeTypeFontParameter();
        boldParameter.size = 64;
        boldParameter.borderColor = Color.BLACK;
        boldParameter.borderWidth = 2;

        FreeTypeFontGenerator regularGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/RobotoCondensed-Regular.ttf"));
        FreeTypeFontParameter regularParameter = new FreeTypeFontParameter();
        regularParameter.size = 60;

        boldLarge = boldGenerator.generateFont(boldParameter);
        regularLarge = regularGenerator.generateFont(regularParameter);

        boldGenerator.dispose();
        regularGenerator.dispose();
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

        int codeOffset = (int) -(width * 0.21);

        // Game code label

        TextElement gameCodeLabel = new TextElement("Game Code: 0001", codeOffset, 35);
        gameCodeLabel.setOrientation(Orientation.TopMiddle);
        gameCodeLabel.setFont(boldLarge);
        scene.add(gameCodeLabel);

        int settingsOffset = (int) (width * 0.26);

        // Lobby settings label

        TextElement lobbySettingsLabel = new TextElement("Lobby Settings", settingsOffset, 35);
        lobbySettingsLabel.setOrientation(Orientation.TopMiddle);
        lobbySettingsLabel.setFont(boldLarge);
        scene.add(lobbySettingsLabel);

        // Map selection

        TextureElement mapIcon = new TextureElement(mapIconTexture, settingsOffset, 200, 300, 300);
        mapIcon.setOrientation(Orientation.TopMiddle);
        scene.add(mapIcon);

        Button mapBackButton = new Button(backTexture, settingsOffset - 250, 280, 100, 100, 6);
        mapBackButton.setOrientation(Orientation.TopMiddle);
        scene.add(mapBackButton);

        Button mapForwardButton = new Button(forwardTexture, settingsOffset + 250, 280, 100, 100, 7);
        mapForwardButton.setOrientation(Orientation.TopMiddle);
        scene.add(mapForwardButton);

        // Misc lobby settings

        TextElement playerCountLabel = new TextElement("Max Players: 4", settingsOffset, 490);
        playerCountLabel.setOrientation(Orientation.TopMiddle);
        playerCountLabel.setFont(regularLarge);
        scene.add(playerCountLabel);

        Button countBackButton = new Button(backTexture, settingsOffset - 250, 500, 100, 100, 2);
        countBackButton.setOrientation(Orientation.TopMiddle);
        scene.add(countBackButton);

        Button countForwardButton = new Button(forwardTexture, settingsOffset + 250, 500, 100, 100, 3);
        countForwardButton.setOrientation(Orientation.TopMiddle);
        scene.add(countForwardButton);

        TextElement difficultyLabel = new TextElement("Normal", settingsOffset, 620);
        difficultyLabel.setOrientation(Orientation.TopMiddle);
        difficultyLabel.setFont(regularLarge);
        scene.add(difficultyLabel);

        Button difficultyBackButton = new Button(backTexture, settingsOffset - 250, 630, 100, 100, 4);
        difficultyBackButton.setOrientation(Orientation.TopMiddle);
        scene.add(difficultyBackButton);

        Button difficultyForwardButton = new Button(forwardTexture, settingsOffset + 250, 630, 100, 100, 5);
        difficultyForwardButton.setOrientation(Orientation.TopMiddle);
        scene.add(difficultyForwardButton);

        // Start button stuff

        TextureElement startSkirt = new TextureElement(startSkirtTexture, settingsOffset, 75, 700, 200);
        startSkirt.setOrientation(Orientation.BottomMiddle);
        scene.add(startSkirt);

        Button startButton = new Button(startButtonTexture, settingsOffset, 90, 670, 170, 1);
        startButton.setOrientation(Orientation.BottomMiddle);
        scene.add(startButton);
    }

    @Override
    public void dispose() {
        menuTexture.dispose();
        startSkirtTexture.dispose();
        startButtonTexture.dispose();
        mapIconTexture.dispose();
    }
}
