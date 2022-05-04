package com.se309.game;

import com.se309.scene.GameScene;
import com.se309.tower.ResourceContext;

public class GameLogic {

    private GameLogicProcessor processor;
    private ResourceContext context;
    private GameScene game;

    public GameLogic(GameLogicProcessor processor, ResourceContext context) {
        this.processor = processor;
        this.context = context;
    }

    public void setGameScene(GameScene game) {
        this.game = game;
    }

    public void handleGameLogic() {
    }
}
