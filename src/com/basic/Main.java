package com.basic;

import com.basic.model.Model;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        JFrame game = new JFrame();

        game.setTitle("Вторжение инопланетян");
        game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game.setSize(1024, 768);
        game.setResizable(false);
        game.add(controller.getView());
        game.setLocationRelativeTo(null);
        game.setVisible(true);
    }
}
