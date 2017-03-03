package com.basic.model;

import com.basic.*;

import java.util.HashSet;
import java.util.Set;

public class Model {
    private GameObjects gameObjects;

    {
        Set<Alien> aliens = new HashSet<>();
        for (int i = 1; i <= Main.NUMBER_OF_ALIANS; i++) {
            aliens.add(new Alien((int) (Math.random() * (Main.FRAME_WIDTH - Alien.WIDTH - 1) + Alien.WIDTH / 2), (int) (Math.random() * 2 + 1), i));
        }

        Set<Man> men = new HashSet<>();
        for (int i = 1; i <= Main.NUMBER_OF_MEN; i++) {
            men.add(new Man((int) (Math.random() * (Main.FRAME_WIDTH - Man.WIDTH - 1) + Man.WIDTH / 2), (int) (Math.random() * 2 + 1)));
        }

        Set<Bomb> bombs = new HashSet<>();
        bombs.add(new Bomb((int) (Math.random() * (Main.FRAME_WIDTH - Bomb.WIDTH - 1) + Bomb.WIDTH / 2), 100));

        Set<Rocket> rockets = new HashSet<>();
        rockets.add(new Rocket((int) (Math.random() * (Main.FRAME_WIDTH - Rocket.WIDTH - 1) + Rocket.WIDTH / 2), Main.FRAME_HEIGHT - Rocket.HEIGHT / 2));

        Launcher launcher = new Launcher((int) (Math.random() * (Main.FRAME_WIDTH - Launcher.WIDTH - 1) + Launcher.WIDTH / 2));

        gameObjects = new GameObjects(aliens, bombs, men, rockets, launcher);
    }

    public GameObjects getGameObjects() {
        return gameObjects;
    }
}
