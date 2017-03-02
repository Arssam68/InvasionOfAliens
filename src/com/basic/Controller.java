package com.basic;

import com.basic.controller.EventListener;
import com.basic.model.Direction;
import com.basic.model.GameObject;
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

    void startRenderTimer(int fps) {
        Timer timer = new Timer(1000 / fps, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                view.clear();
                view.render();
                view.swapBuffers();
            }
        });
        timer.setRepeats(true);
        timer.start();
    }
}
