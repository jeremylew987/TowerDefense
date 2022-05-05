package com.se309.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.se309.button.Button;
import com.se309.render.Orientation;
import com.se309.render.TextElement;
import com.se309.render.TextureElement;

public class GameScene implements Scene {

    // Textures
    private Texture fieldTexture;
    private Texture menuTall;
    private Texture menuShort;

    private Texture tower1ButtonTexture;
    private Texture tower2ButtonTexture;
    private Texture tower3ButtonTexture;

    public Texture dotTexture;
    public Texture enemyTexture;

    public Texture towerTexture;
    public Texture towerAlphaTexture;

    // Fonts
    public BitmapFont boldLarge;
    public BitmapFont regularLarge;
    public BitmapFont regularMedium;
    public BitmapFont regularMediumGold;
    public BitmapFont regularMediumGreen;
    public BitmapFont regularMediumRed;

    // Values
    public TextElement balanceValue;
    public TextElement roundValue;
    public TextElement liveValue;

    // Constants
    private static final int TOWER_MENU_LEFT_PADDING = 30;
    private static final int TOWER_MENU_TOP_PADDING = 30;

    private static final int PLAYER_MENU_LEFT_PADDING = 30;
    private static final int PLAYER_MENU_TOP_PADDING = 30;

    @Override
    public void initialize() {
        // Init textures
        fieldTexture = new Texture("textures/game_field.png");
        menuTall = new Texture("textures/game_menutall.png");
        menuShort = new Texture("textures/game_menushort.png");
        dotTexture = new Texture("textures/dot.png");

        towerTexture = new Texture("textures/game_tower.png");
        towerAlphaTexture = new Texture("textures/game_toweralpha.png");

        tower1ButtonTexture = new Texture("textures/game_tower1button.png");
        tower2ButtonTexture = new Texture("textures/game_tower2button.png");
        tower3ButtonTexture = new Texture("textures/game_tower3button.png");

        enemyTexture = new Texture("textures/enemy.png");

        // Init fonts
        FreeTypeFontGenerator boldGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/RobotoCondensed-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter boldParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        boldParameter.size = 64;
        boldParameter.borderColor = Color.BLACK;
        boldParameter.borderWidth = 2;

        FreeTypeFontGenerator regularGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/RobotoCondensed-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter regularParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        regularParameter.size = 60;

        boldLarge = boldGenerator.generateFont(boldParameter);
        regularLarge = regularGenerator.generateFont(regularParameter);

        regularParameter.size = 56;
        regularMedium = regularGenerator.generateFont(regularParameter);

        regularParameter.color = Color.GOLD;
        regularMediumGold = regularGenerator.generateFont(regularParameter);

        regularParameter.color = Color.GREEN;
        regularMediumGreen = regularGenerator.generateFont(regularParameter);

        regularParameter.color = Color.RED;
        regularMediumRed = regularGenerator.generateFont(regularParameter);

        boldGenerator.dispose();
        regularGenerator.dispose();
    }

    @Override
    public void display(SceneContext scene) {
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        // Generate field
        TextureElement field = new TextureElement(fieldTexture, 0, 0);
        field.setOrientation(Orientation.Middle);
        scene.add(field);

        // Generate tower menu
        TextureElement towerMenu = new TextureElement(menuTall, TOWER_MENU_LEFT_PADDING, TOWER_MENU_TOP_PADDING, 350, height - TOWER_MENU_TOP_PADDING * 2);
        towerMenu.setOrientation(Orientation.TopLeft);
        scene.add(towerMenu);

        // Generate player menu
        TextureElement playerMenu = new TextureElement(menuShort, PLAYER_MENU_LEFT_PADDING, PLAYER_MENU_TOP_PADDING, 350, 450);
        playerMenu.setOrientation(Orientation.TopRight);
        scene.add(playerMenu);

        int xOffset = -900;

        // Generate tower menu items
        TextElement statusLabel = new TextElement("Status", xOffset, 25);
        statusLabel.setOrientation(Orientation.TopMiddle);
        statusLabel.setFont(boldLarge);
        scene.add(statusLabel);

        TextElement balanceLabel = new TextElement("Balance", xOffset, 130);
        balanceLabel.setOrientation(Orientation.TopMiddle);
        balanceLabel.setFont(regularMedium);
        scene.add(balanceLabel);

        balanceValue = new TextElement("$0", xOffset, 190);
        balanceValue.setOrientation(Orientation.TopMiddle);
        balanceValue.setFont(regularMediumGold);
        scene.add(balanceValue);

        TextElement roundLabel = new TextElement("Round", xOffset, 270);
        roundLabel.setOrientation(Orientation.TopMiddle);
        roundLabel.setFont(regularMedium);
        scene.add(roundLabel);

        roundValue = new TextElement("1", xOffset, 330);
        roundValue.setOrientation(Orientation.TopMiddle);
        roundValue.setFont(regularMediumGreen);
        scene.add(roundValue);

        TextElement liveLabel = new TextElement("Lives", xOffset, 410);
        liveLabel.setOrientation(Orientation.TopMiddle);
        liveLabel.setFont(regularMedium);
        scene.add(liveLabel);

        liveValue = new TextElement("50", xOffset, 470);
        liveValue.setOrientation(Orientation.TopMiddle);
        liveValue.setFont(regularMediumRed);
        scene.add(liveValue);

        TextElement towersLabel = new TextElement("Towers", xOffset, 550);
        towersLabel.setOrientation(Orientation.TopMiddle);
        towersLabel.setFont(boldLarge);
        scene.add(towersLabel);

        Button tower1Button = new Button(tower1ButtonTexture, xOffset, 680, 300, 90, 10);
        tower1Button.setOrientation(Orientation.TopMiddle);
        scene.add(tower1Button);

        Button tower2Button = new Button(tower2ButtonTexture, xOffset, 780, 300, 90, 10);
        tower2Button.setOrientation(Orientation.TopMiddle);
        scene.add(tower2Button);

        Button tower3Button = new Button(tower3ButtonTexture, xOffset, 880, 300, 90, 10);
        tower3Button.setOrientation(Orientation.TopMiddle);
        scene.add(tower3Button);
    }

    @Override
    public void dispose() {
        fieldTexture.dispose();
        menuTall.dispose();
        menuShort.dispose();
        dotTexture.dispose();

        tower1ButtonTexture.dispose();
        tower2ButtonTexture.dispose();
        tower3ButtonTexture.dispose();

        enemyTexture.dispose();

        towerTexture.dispose();
        towerAlphaTexture.dispose();
    }
}
