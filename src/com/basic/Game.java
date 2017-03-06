package com.basic;

import com.basic.controller.Controller;
import com.basic.controller.Input;
import com.basic.model.Direction;
import com.basic.utils.Time;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Game implements Runnable {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final String TITLE = "Вторжение инопланетян";
    public static final int CLEAR_COLOR = Color.white.getRGB();
    public static final int NUM_BUFFERS = 3;

    private Controller controller;
    private View view;
    private Input input;

    public static final float UPDATE_RATE = 60.0f;
    public static final float UPDATE_INTERVAL = Time.SECOND / UPDATE_RATE;
    public static final long IDLE_TIME = 1;

    private boolean isRunning;
    private Thread gameThread;

    public static final int NUMBER_OF_ALIANS = 5;
    public static final int MAX_NUMBER_OF_ALIANS = 20;
    public static final int NUMBER_OF_MEN = 5;
    public static final int MAX_NUMBER_OF_MEN = 20;
    public static final int MAX_NUMBER_OF_ROCKETS = 100;

    public Game() {
        isRunning = false;
        controller = new Controller();
        view = controller.getView();
        input = controller.getInput();
    }

    public synchronized void start() {
        if (!isRunning) {
            isRunning = true;
            gameThread = new Thread(this, "Game thread");
            gameThread.start();
        }
    }

    public synchronized void stop() {
        if (!isRunning) {
            isRunning = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        if (input.getKey(KeyEvent.VK_UP)) {
            controller.launch();
        } else controller.checkLauncherReady();
        if (input.getKey(KeyEvent.VK_LEFT)) {
            controller.move(Direction.LEFT);
        }
        if (input.getKey(KeyEvent.VK_RIGHT)) {
            controller.move(Direction.RIGHT);
        }
    }

    private void render() {
        controller.removeInactiveObjects();
        view.render();
    }

    private void cleanUp (){
        view.destroy();
    }

    @Override
    public void run() {
        int fps = 0;
        int upd = 0;
        int updl = 0;

        long count = 0;

        float delta = 0f;
        long lastTime = Time.get();

        while (isRunning) {
            long now = Time.get();
            long elapsedTime = now - lastTime;
            lastTime = now;

            count += elapsedTime;

            boolean isRender = false;
            delta += (elapsedTime / UPDATE_INTERVAL);
            while (delta > 1) {
                update();
                upd++;
                delta--;
                if (isRender) {
                    updl++;
                } else {
                    isRender = true;
                }
            }
            if (isRender) {
                render();
                fps++;
            } else {
                try {
                    Thread.sleep(IDLE_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (count >= Time.SECOND) {
                view.setTitle(TITLE + " || FPS: " + fps + " | UPD: " + upd + " | UPDL: " + updl);
                fps = 0;
                upd = 0;
                updl = 0;
                count = 0;
            }
        }
    }
}
