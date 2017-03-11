package com.model;

import com.*;

import java.util.HashSet;
import java.util.Set;

public class GameObjects {
    private Set<Alien> aliens;
    private Set<Bomb> bombs;
    private Set<Man> men;
    private Set<Rocket> rockets;
    private Launcher launcher;

    public void setAliens(Set<Alien> aliens) {
        this.aliens = aliens;
    }

    public void setBombs(Set<Bomb> bombs) {
        this.bombs = bombs;
    }

    public void setMen(Set<Man> men) {
        this.men = men;
    }

    public void setRockets(Set<Rocket> rockets) {
        this.rockets = rockets;
    }

    public void setLauncher(Launcher launcher) {
        this.launcher = launcher;
    }

    public synchronized Set<Alien> getAliens() {
        Set<Alien> aliensClone = new HashSet<>();
        aliensClone.addAll(aliens);
        return aliensClone;
    }

    public synchronized Set<Bomb> getBombs() {
        Set<Bomb> bombsClone = new HashSet<>();
        bombsClone.addAll(bombs);
        return bombsClone;
    }

    public synchronized Set<Man> getMen() {
        Set<Man> menClone = new HashSet<>();
        menClone.addAll(men);
        return menClone;
    }

    public synchronized Set<Rocket> getRockets() {
        Set<Rocket> rocketsClone = new HashSet<>();
        rocketsClone.addAll(rockets);
        return rocketsClone;
    }

    public Launcher getLauncher() {
        return launcher;
    }

    public GameObjects(Set<Alien> aliens, Set<Bomb> bombs, Set<Man> men, Set<Rocket> rockets, Launcher launcher) {
        this.aliens = aliens;
        this.bombs = bombs;
        this.men = men;
        this.rockets = rockets;
        this.launcher = launcher;
    }

    public GameObjects getGameObjects() {
        return this;
    }
}
