package com.se309.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.se309.render.Orientation;
import com.se309.render.TextureElement;

public class GameScene implements Scene {

    // Textures
    private Texture fieldTexture;
    private Texture menuTall;
    private Texture menuShort;

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

        // Generate tower menu
        TextureElement playerMenu = new TextureElement(menuShort, PLAYER_MENU_LEFT_PADDING, PLAYER_MENU_TOP_PADDING, 350, 450);
        playerMenu.setOrientation(Orientation.TopRight);
        scene.add(playerMenu);
    }

    @Override
    public void dispose() {
        fieldTexture.dispose();
        menuTall.dispose();
        menuShort.dispose();
    }
}
