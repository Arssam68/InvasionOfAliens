package com.basic;

import com.basic.model.GameObjects;
import com.basic.model.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Controller
{
    private View view;
    private Model model;

    public Controller()
    {
        view = new View(this);
        model = new Model();
    }

    View getView() {
        return view;
    }

    public GameObjects getGameObjects() {
        return model.getGameObjects();
    }

    void startRenderTimer() {
        Timer timer = new Timer(1000 / Main.FPS, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                view.render();
            }
        });
        timer.setRepeats(true);
        timer.start();
    }
}
