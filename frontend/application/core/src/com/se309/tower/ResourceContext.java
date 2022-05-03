package com.se309.tower;

import com.se309.button.ButtonManager;
import com.se309.queue.GameEventQueue;
import com.se309.render.ElementRenderer;
import com.se309.render.RenderSettings;
import com.se309.scene.SceneManager;

public class ResourceContext {

    private ElementRenderer renderer;
    private RenderSettings renderSettings;
    private SceneManager sceneManager;
    private GameEventQueue eventQueue;
    private ButtonManager buttonManager;

    public ElementRenderer getRenderer() {
        return renderer;
    }

    public void setRenderer(ElementRenderer renderer) {
        this.renderer = renderer;
    }

    public RenderSettings getRenderSettings() {
        return renderSettings;
    }

    public void setRenderSettings(RenderSettings renderSettings) {
        this.renderSettings = renderSettings;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public GameEventQueue getEventQueue() {
        return eventQueue;
    }

    public void setEventQueue(GameEventQueue eventQueue) {
        this.eventQueue = eventQueue;
    }

    public ButtonManager getButtonManager() {
        return buttonManager;
    }

    public void setButtonManager(ButtonManager buttonManager) {
        this.buttonManager = buttonManager;
    }
}
