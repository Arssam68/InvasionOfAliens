package com.basic;

import com.basic.model.Direction;
import com.basic.model.GameObjects;
import com.basic.model.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Controller implements EventListener
{
    private View view;
    private Model model;

    public Controller()
    {
        view = new View(this);
        model = new Model();
        model.setEventListener(this);
        view.setEventListener(this);
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

    View getView() {
        return view;
    }

    public GameObjects getGameObjects() {
        return model.getGameObjects();
    }

    void startRenderTimer() {
        Timer timer = new Timer(1000 / Game.FPS, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                removeInactiveObjects();
                view.render();
            }
        });
        timer.setRepeats(true);
        timer.start();
    }

    private void removeInactiveObjects() {
        model.removeInactiveObjects();
    }
}
