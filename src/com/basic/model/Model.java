package com.basic.model;

import com.basic.Alien;
import com.basic.Main;
import com.basic.controller.EventListener;

import java.util.HashSet;
import java.util.Set;

public class Model {
    private EventListener eventListener;
    private GameObjects gameObjects;

    {
        Set<Alien> aliens = new HashSet<>();
        aliens.add(new Alien((int) (Math.random() * (Main.FRAME_WIDTH - Alien.WIDTH - 1) + Alien.WIDTH / 2), Alien.HEIGHT / 2, Alien.WIDTH, Alien.HEIGHT));
        gameObjects = new GameObjects(aliens, null, null, null, null);
    }

    public GameObjects getGameObjects() {
        return gameObjects;
    }

    public void setEventListener(EventListener eventListener)
    {
        this.eventListener = eventListener;
    }
}
