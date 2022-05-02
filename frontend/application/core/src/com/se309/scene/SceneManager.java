package com.se309.scene;

import com.se309.render.Element;
import com.se309.render.RenderSettings;
import com.se309.tower.ResourceContext;

import java.util.ArrayList;
import java.util.HashMap;

public class SceneManager {

    private HashMap<String, Scene> scenes;
    private SceneContext currentScene;

    private ResourceContext context;


    public SceneManager(ResourceContext context) {
        scenes = new HashMap<>();
        currentScene = new SceneContext();


        this.context = context;
    }

    public void register(String name, Scene scene) {
        scenes.put(name, scene);

        scene.initialize();
    }

    public Scene get(String name) {
        return scenes.get(name);
    }

    public void remove(String name) {
        scenes.remove(name);
    }

    public void display(String name) {
        // Remove all current scene elements
        for (Element e : currentScene.getElements()) {
            context.getRenderer().removeElement(e);
        }

        // Fetch the next scene
        if (name == null) return;

        Scene scene = scenes.get(name);

        currentScene = new SceneContext();

        if (scene != null) {
            scene.display(currentScene);
        }

        // Set the new render settings
        context.setRenderSettings(currentScene.getRenderSettings());

        // Add all current scene elements
        for (Element e : currentScene.getElements()) {
            context.getRenderer().addElement(e);
        }
    }

    public void dispose() {
        // Dispose all scenes
        for (Scene s : scenes.values()) {
            s.dispose();
        }
    }

}
