package com.basic.controller;

import com.basic.*;
import com.basic.model.Direction;
import com.basic.model.GameObjects;
import com.basic.model.Model;

import java.awt.*;

public class Controller implements EventListener {
    private View view;
    private Model model;
    private Input input;
    private Game game;

    public Controller(Game game) {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        this.game = game;
        view = new View(this);
        view.create(Game.WIDTH, Game.HEIGHT, Game.TITLE, Game.CLEAR_COLOR, Game.NUM_BUFFERS);
        //view.create(screenWidth, screenHeight, Game.TITLE, Game.CLEAR_COLOR, Game.NUM_BUFFERS);
        input = new Input();
        view.addInputListener(input);
        restart();
    }

    @Override
    public void move(Direction direction) {
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

    public Model getModel() {
        return model;
    }

    public GameObjects getGameObjects() {
        return model.getGameObjects();
    }

    public void removeInactiveObjects() {
        model.removeInactiveObjects();
    }

    public void isCollision() {
        model.isCollision();
    }

    public void gameWon() {
        view.gameWon();
        restart();
    }

    public void gameLost() {
        view.gameLost();
        restart();
    }

    public void gameDraw() {
        view.gameDraw();
        restart();
    }

    public void restart() {
        model = new Model(this);
    }

    public Game getGame() {
        return game;
    }
}
