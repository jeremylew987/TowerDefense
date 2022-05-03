package com.se309.game;

import com.se309.button.ButtonManager;
import com.se309.queue.ButtonEvent;
import com.se309.queue.GameEvent;
import com.se309.tower.ResourceContext;

public class GameLogicProcessor {

    private ResourceContext context;

    public GameLogicProcessor(ResourceContext context) {
        this.context = context;
    }

    int count = 0;

    public void tick() {
        context.getButtonManager().process();

        GameEvent e;

        while ((e = context.getEventQueue().dequeue()) != null) {
            if (e instanceof ButtonEvent) {
                ButtonEvent be = (ButtonEvent) e;

                System.out.println("Button Event: " + be.getSignal());
            }
        };
    }

}
