package com.basic;

import com.basic.model.Direction;
import com.basic.model.GameObject;
import com.basic.model.GameObjects;
import com.basic.model.Model;

public class Controller implements com.basic.controller.EventListener
{
    private View view;
    private Model model;

    public Controller()
    {
        view = new View(this);
        model = new Model();
        model.setEventListener(this);
    }

    View getView() {
        return view;
    }

    @Override
    public void move(Direction direction, GameObject gameObject) {
        view.repaint();
    }

    public GameObjects getGameObjects() {
        return model.getGameObjects();
    }
}
