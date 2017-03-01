package com.basic.model;

import com.basic.Alien;
import com.basic.controller.EventListener;

import java.util.HashSet;
import java.util.Set;

public class Model {
    private EventListener eventListener;
    private GameObjects gameObjects;

    {
        Set<Alien> aliens = new HashSet<>();
        aliens.add(new Alien(10, 10, 70, 20, true));
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
