package com.basic;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static final Color BG_COLOR = Color.white;
    public static final int FRAME_WIDTH = 1024;
    public static final int FRAME_HEIGHT = 768;
    public static final int FPS = 25;

    public static final int NUMBER_OF_ALIANS = 5;
    public static final int MAX_NUMBER_OF_ALIANS = 20;

    public static void main(String[] args) {
        Controller controller = new Controller();
        JFrame game = new JFrame();
        View view = controller.getView();

        game.setTitle("Вторжение инопланетян");
        game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        game.setResizable(false);
        game.add(view);
        game.setLocationRelativeTo(null);
        game.setBackground(BG_COLOR);
        game.setVisible(true);

        controller.startRenderTimer();
    }
}
