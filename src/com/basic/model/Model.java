package com.basic.model;

import com.basic.*;
import com.basic.controller.Input;

import java.util.*;

public class Model implements Observer {
    private Input input;
    private GameObjects gameObjects;
    private Set<Alien> aliens = new HashSet<>();
    private Set<Man> men = new HashSet<>();
    private Set<Bomb> bombs = new HashSet<>();
    private Set<Rocket> rockets = new HashSet<>();
    private Launcher launcher;

    public Model() {
        gameObjects = new GameObjects(aliens, bombs, men, rockets, launcher);

        for (int i = 1; i <= Game.MAX_NUMBER_OF_ALIANS; i++) {
            Alien alien = new Alien((int) (Math.random() * (Game.WIDTH - Alien.WIDTH - 1) + Alien.WIDTH / 2), (int) (Math.random() * 2 + 1), i);
            alien.addObserver(this);
            aliens.add(alien);
        }

        for (int i = 1; i <= Game.MAX_NUMBER_OF_MEN; i++) {
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

        Iterator<Alien> aliensIterator = aliens.iterator();
        while (aliensIterator.hasNext()) {
            if (!aliensIterator.next().isActive()) aliensIterator.remove();
        }
        while (aliens.size() < Game.MAX_NUMBER_OF_ALIANS) {
            if (Alien.getCurrentNumber() <= Game.TOTAL_NUMBER_OF_ALIANS) {
                int i = 0;
                List<Integer> alienAlt = new ArrayList<>();
                for (Alien alienItem : aliens) {
                    alienAlt.add(alienItem.getAlt());
                }
                if (!alienAlt.contains(1)) i = 1;
                else if (!alienAlt.contains(2)) i = 2;
                else if (!alienAlt.contains(3)) i = 3;
                else if (!alienAlt.contains(4)) i = 4;
                else i = 5;
                Alien alien = new Alien((int) (Math.random() * (Game.WIDTH - Alien.WIDTH - 1) + Alien.WIDTH / 2), (int) (Math.random() * 2 + 1), i);
                alien.addObserver(this);
                aliens.add(alien);
            } else break;
        }

        Iterator<Man> menIterator = men.iterator();
        while (menIterator.hasNext()) {
            if (!menIterator.next().isActive()) menIterator.remove();
        }
        while (men.size() < Game.MAX_NUMBER_OF_MEN) {
            if (Man.getCurrentNumber() <= Game.TOTAL_NUMBER_OF_MEN) {
                men.add(new Man((int) (Math.random() * (Game.WIDTH - Man.WIDTH - 1) + Man.WIDTH / 2), (int) (Math.random() * 2 + 1)));
            } else break;
        }

        gameObjects.setRockets(rockets);
        gameObjects.setBombs(bombs);
        gameObjects.setAliens(aliens);
        gameObjects.setMen(men);
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
            if (launcher.getRocketQuantity() > 0) {
                rockets.add(new Rocket(launcher.getX(), launcher.getY() - launcher.getHeight() / 2 - Rocket.HEIGHT / 2));
                int rq = launcher.getRocketQuantity() - 1;
                launcher.setRocketQuantity(rq);
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

    public void isCollision() {
        GameObjects gameObjects = getGameObjects();
        for (Rocket rocket : gameObjects.getRockets()) {
            for (Alien alien : gameObjects.getAliens()) {
                if (rocket.getLeftUpper().getY() <= alien.getY() + alien.getHeight() / 2 &&
                        rocket.getLeftUpper().getX() + rocket.getWidth() >= alien.getLeftUpper().getX() &&
                        rocket.getLeftUpper().getX() <= alien.getLeftUpper().getX() + alien.getWidth()) {
                    alien.setActive(false);
                    alien.getThread().interrupt();
                    rocket.setActive(false);
                    rocket.getThread().interrupt();
                }
            }
            for (Bomb bomb : gameObjects.getBombs()) {
                if (rocket.getLeftUpper().getY() <= bomb.getY() + bomb.getHeight() / 2 &&
                        rocket.getLeftUpper().getX() + rocket.getWidth() >= bomb.getLeftUpper().getX() &&
                        rocket.getLeftUpper().getX() <= bomb.getLeftUpper().getX() + bomb.getWidth()) {
                    bomb.setActive(false);
                    bomb.getThread().interrupt();
                    rocket.setActive(false);
                    rocket.getThread().interrupt();
                }
            }
        }
        for (Bomb bomb : gameObjects.getBombs()) {
            if (bomb.getLeftUpper().getY() + bomb.getHeight() >= launcher.getY() - launcher.getHeight() / 2 &&
                    bomb.getLeftUpper().getX() + bomb.getWidth() >= launcher.getLeftUpper().getX() &&
                    bomb.getLeftUpper().getX() <= launcher.getLeftUpper().getX() + launcher.getWidth()) {
                bomb.setActive(false);
                bomb.getThread().interrupt();
                int rq = launcher.getRocketQuantity() - Launcher.LOST_ROCKETS;
                if (rq < 0) launcher.setRocketQuantity(0);
                else launcher.setRocketQuantity(rq);
            }
            for (Man man : gameObjects.getMen()) {
                if (bomb.getLeftUpper().getY() + bomb.getHeight() >= man.getY() - man.getHeight() / 2 &&
                        bomb.getLeftUpper().getX() + bomb.getWidth() >= man.getLeftUpper().getX() &&
                        bomb.getLeftUpper().getX() <= man.getLeftUpper().getX() + man.getWidth()) {
                    man.setActive(false);
                    man.getThread().interrupt();
                    bomb.setActive(false);
                    bomb.getThread().interrupt();
                }
            }
        }
    }

    @Override
    public synchronized void update(Observable o, Object arg) {
        if (o instanceof Alien) {
            bombFlush((Alien) o);
        }
    }
}
