package com.basic;

import com.basic.controller.EventListener;
import com.basic.model.GameObject;
import com.basic.model.GameObjects;

import javax.swing.*;
import java.awt.*;

public class View extends JPanel {
    private static final Color BG_COLOR = Color.white;

    private Controller controller;

    boolean isGameWon = false;
    boolean isGameLost = false;

    View(Controller controller) {
        setFocusable(true);
        this.controller = controller;
        this.setBackground(BG_COLOR);
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.setColor(BG_COLOR);
        GameObjects gameObjects = getGameObjects();
        if (gameObjects == null) return;
        for (GameObject gameObject : gameObjects.getAll()) {
            gameObject.draw(graphics);
        }
    }

    public GameObjects getGameObjects() {
        return controller.getGameObjects();
    }
}
