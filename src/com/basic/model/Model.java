package com.basic.model;

import com.basic.*;
//import com.basic.controller.Controller;
//import com.basic.controller.EventListener;
import com.basic.controller.Input;

import java.util.*;

public class Model implements Observer {
    //private Controller controller;
    private Input input;

    private GameObjects gameObjects;
    //private EventListener eventListener;
    private Set<Alien> aliens = new HashSet<>();
    private Set<Man> men = new HashSet<>();
    private Set<Bomb> bombs = new HashSet<>();
    private Set<Rocket> rockets = new HashSet<>();
    private Launcher launcher;

    public Model(/*Controller controller*/) {
        //this.controller = controller;
        //input = controller.getInput();
        gameObjects = new GameObjects(aliens, bombs, men, rockets,launcher);

        for (int i = 1; i <= Game.NUMBER_OF_ALIANS; i++) {
            Alien alien = new Alien((int) (Math.random() * (Game.WIDTH - Alien.WIDTH - 1) + Alien.WIDTH / 2), (int) (Math.random() * 2 + 1), i);
            alien.addObserver(this);
            aliens.add(alien);
        }

        for (int i = 1; i <= Game.NUMBER_OF_MEN; i++) {
            men.add(new Man((int) (Math.random() * (Game.WIDTH - Man.WIDTH - 1) + Man.WIDTH / 2), (int) (Math.random() * 2 + 1)));
        }

        launcher = new Launcher((int) (Math.random() * (Game.WIDTH - Launcher.WIDTH - 1) + Launcher.WIDTH / 2));

        gameObjects.setAliens(aliens);
        gameObjects.setMen(men);
        gameObjects.setLauncher(launcher);
    }

    public GameObjects getGameObjects() {
        return gameObjects.getGameObjects();
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

        gameObjects.setRockets(rockets);
        gameObjects.setBombs(bombs);
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

        if (launcher.isReady()) {
            if (Rocket.getCurrentNumber() < Game.MAX_NUMBER_OF_ROCKETS) {
                rockets.add(new Rocket(launcher.getX(), launcher.getY() - launcher.getHeight() / 2 - Rocket.HEIGHT / 2));
                launcher.setReady(false);
                gameObjects.setRockets(rockets);
            }
        } else {
            int launchInterval = launcher.getLaunchInterval();
            if (launchInterval == 0) {
                launcher.setLaunchInterval(launcher.LAUNCH_INTERVAL);
                launcher.setReady(true);
            } else {
                launcher.setLaunchInterval(--launchInterval);
            }
        }
    }

    public void checkLauncherReady() {
        launcher = gameObjects.getLauncher();

        if (!launcher.isReady()) {
            int launchInterval = launcher.getLaunchInterval();
            if (launchInterval == 0) {
                launcher.setLaunchInterval(launcher.LAUNCH_INTERVAL);
                launcher.setReady(true);
            } else {
                launcher.setLaunchInterval(--launchInterval);
            }
        }
    }

    public void bombFlush(Alien alien) {
        bombs = gameObjects.getBombs();
        aliens = gameObjects.getAliens();

        bombs.add(new Bomb(alien.getX(), alien.getY() + alien.getHeight() / 2 + Bomb.HEIGHT / 2));
        gameObjects.setBombs(bombs);
    }

    @Override
    public synchronized void update(Observable o, Object arg) {
        if (o instanceof Alien) {
            bombFlush((Alien) o);
        }
    }
}
