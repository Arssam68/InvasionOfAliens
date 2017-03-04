package com.basic.model;

import com.basic.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Model {
    private GameObjects gameObjects;
    private EventListener eventListener;
    Set<Alien> aliens = new HashSet<>();
    Set<Man> men = new HashSet<>();
    Set<Bomb> bombs = new HashSet<>();
    Set<Rocket> rockets =new HashSet<>();
    Launcher launcher;

    public Model() {

        for (int i = 1; i <= Game.NUMBER_OF_ALIANS; i++) {
            aliens.add(new Alien((int) (Math.random() * (Game.FRAME_WIDTH - Alien.WIDTH - 1) + Alien.WIDTH / 2), (int) (Math.random() * 2 + 1), i));
        }

        for (int i = 1; i <= Game.NUMBER_OF_MEN; i++) {
            men.add(new Man((int) (Math.random() * (Game.FRAME_WIDTH - Man.WIDTH - 1) + Man.WIDTH / 2), (int) (Math.random() * 2 + 1)));
        }

        launcher = new Launcher((int) (Math.random() * (Game.FRAME_WIDTH - Launcher.WIDTH - 1) + Launcher.WIDTH / 2));

        gameObjects = new GameObjects(aliens, bombs, men, rockets, launcher);
    }

    public GameObjects getGameObjects() {
        return gameObjects;
    }

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void removeInactiveObjects() {
        if (gameObjects == null) return;
        aliens = gameObjects.getAliens();
        bombs = gameObjects.getBombs();
        men = gameObjects.getMen();
        rockets = gameObjects.getRockets();
        launcher = gameObjects.getLauncher();
        Iterator<Rocket> rocketsIterator = rockets.iterator();
        while (rocketsIterator.hasNext()) {
            if (!rocketsIterator.next().isActive()) rocketsIterator.remove();
        }
        Iterator<Bomb> bombsIterator = bombs.iterator();
        while (bombsIterator.hasNext()) {
            if (!bombsIterator.next().isActive()) bombsIterator.remove();
        }

        gameObjects = new GameObjects(aliens, bombs, men, rockets, launcher);
    }

    public void move(Direction direction) {
        launcher = gameObjects.getLauncher();

        switch (direction) {
            case RIGHT: {
                launcher.move(direction);
                break;
            }
            case LEFT: {
                launcher.move(direction);
                break;
            }
        }
    }

    public void launch() {
        rockets = gameObjects.getRockets();
        launcher = gameObjects.getLauncher();

        if (Rocket.getCurrentNumber() < Game.MAX_NUMBER_OF_ROCKETS)
            rockets.add(new Rocket(launcher.getX(), launcher.getY()));

        gameObjects = new GameObjects(aliens, bombs, men, rockets, launcher);
    }

    public void bombFlush(Alien alien) {
        bombs = gameObjects.getBombs();
        aliens = gameObjects.getAliens();

        if (alien.getBombQuantity() != 0)
            bombs.add(new Bomb(alien.getX(), alien.getY()));
        gameObjects = new GameObjects(aliens, bombs, men, rockets, launcher);
    }
}
