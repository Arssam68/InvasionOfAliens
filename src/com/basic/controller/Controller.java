package com.basic.controller;

import com.basic.*;
import com.basic.model.Direction;
import com.basic.model.GameObjects;
import com.basic.model.Model;

import java.util.HashSet;
import java.util.Set;

public class Controller implements EventListener
{
    private View view;
    private Model model;
    private Input input;

    public Controller()
    {
        view = new View(this);
        view.create(Game.WIDTH, Game.HEIGHT, Game.TITLE, Game.CLEAR_COLOR, Game.NUM_BUFFERS);
        model = new Model();
        input = new Input();
        view.addInputListener(input);
    }

    @Override
    public void move(Direction direction)
    {
        model.move(direction);
    }

    @Override
    public void launch() {
        model.launch();
    }

    @Override
    public void bombFlush(Alien alien) {
        model.bombFlush(alien);
    }

    public void checkLauncherReady() {
        model.checkLauncherReady();
    }

    public View getView() {
        return view;
    }

    public Input getInput() {
        return input;
    }

    public GameObjects getGameObjects() {
        return model.getGameObjects();
    }

    public void removeInactiveObjects() {
        model.removeInactiveObjects();
    }
}
