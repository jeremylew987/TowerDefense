package com.se309.scene;

import com.se309.render.Element;
import com.se309.render.RenderSettings;

import java.util.ArrayList;

public class SceneContext {

    private ArrayList<Element> elements;

    private RenderSettings renderSettings;

    public SceneContext() {
        elements = new ArrayList<>();
        renderSettings = new RenderSettings();
    }

    public void add(Element e) {
        elements.add(e);
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public void setElements(ArrayList<Element> elements) {
        this.elements = elements;
    }

    public RenderSettings getRenderSettings() {
        return renderSettings;
    }

    public void setRenderSettings(RenderSettings renderSettings) {
        this.renderSettings = renderSettings;
    }
}
