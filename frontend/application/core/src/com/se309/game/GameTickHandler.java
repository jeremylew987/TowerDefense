package com.se309.game;

public class GameTickHandler {


    private static final int MS_PER_FRAME = 20;

    private GameLogicProcessor processor;

    private long timeSinceLastTick;
    private long timeDeficit;

    public GameTickHandler(GameLogicProcessor processor) {
        this.processor = processor;

        timeSinceLastTick = System.currentTimeMillis();
    }

    public void onRedraw() {

        long currentTime = System.currentTimeMillis();

        timeDeficit += currentTime - timeSinceLastTick;

        while (timeDeficit >= MS_PER_FRAME) {
            processor.tick();

            timeDeficit -= MS_PER_FRAME;
        }

        timeSinceLastTick = currentTime;

    }

}
