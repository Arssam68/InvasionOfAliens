package com.basic.model;

import com.basic.*;

import java.util.HashSet;
import java.util.Set;

public class GameObjects
{
    private Set<Alien> aliens;
    private Set<Bomb> bombs;
    private Set<Man> men;
    private Set<Rocket> rockets;
    private Launcher launcher;

    public Set<Alien> getAliens()
    {
        return aliens;
    }

    public Set<Bomb> getBombs()
    {
        return bombs;
    }

    public Set<Man> getMen()
{
    return men;
}

    public Set<Rocket> getRockets()
    {
        return rockets;
    }

    public Launcher getLauncher()
    {
        return launcher;
    }

    public GameObjects(Set<Alien> aliens, Set<Bomb> bombs, Set<Man> men, Set<Rocket> rockets, Launcher launcher)
    {
        this.aliens = aliens;
        this.bombs = bombs;
        this.men = men;
        this.rockets = rockets;
        this.launcher = launcher;
    }
}
