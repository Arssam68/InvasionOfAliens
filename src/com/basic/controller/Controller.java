package com.basic.controller;

import com.basic.Alien;
import com.basic.View;
import com.basic.model.Direction;
import com.basic.model.GameObjects;
import com.basic.model.Model;

public class Controller implements EventListener
{
    private View view;
    private Model model;
    private Input input;

    public Controller()
    {
        view = new View(this);
        model = new Model();
        model.setEventListener(this);
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
