package com.basic.model;

import com.basic.Alien;
import com.basic.Main;

import java.util.HashSet;
import java.util.Set;

public class Model {
    private GameObjects gameObjects;

    {
        Set<Alien> aliens = new HashSet<>();
        for (int i = 1; i <= Main.NUMBER_OF_ALIANS; i++) {
            aliens.add(new Alien((int) (Math.random() * (Main.FRAME_WIDTH - Alien.WIDTH - 1) + Alien.WIDTH / 2), (int) (Math.random() * 2 + 1), i));
        }

        gameObjects = new GameObjects(aliens, null, null, null, null);
    }

    public GameObjects getGameObjects() {
        return gameObjects;
    }
}
