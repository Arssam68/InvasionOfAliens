package com.basic;

import com.basic.model.Model;

import javax.swing.*;

public class Main {
    public static final int FRAME_WIDTH = 1024;
    public static final int FRAME_HEIGHT = 768;

    public static void main(String[] args) {
        Controller controller = new Controller();
        JFrame game = new JFrame();

        game.setTitle("Вторжение инопланетян");
        game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        game.setResizable(false);
        game.add(controller.getView());
        game.setLocationRelativeTo(null);
        game.setVisible(true);

        controller.startRenderTimer(50);
    }
}
