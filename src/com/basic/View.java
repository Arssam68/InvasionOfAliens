package com.basic;

import com.basic.model.GameObjects;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class View extends JPanel {
    private BufferedImage buffer;
    private int[] bufferData;
    private Graphics bufferGrafics;

    private Controller controller;

    View(Controller controller) {
        setFocusable(true);
        this.controller = controller;
        buffer = new BufferedImage(Main.FRAME_WIDTH, Main.FRAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
        bufferData = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();
        bufferGrafics = buffer.getGraphics();
    }

    public void clear() {
        Arrays.fill(bufferData, Main.BG_COLOR.getRGB());
    }

    public void render() {
        clear();

        GameObjects gameObjects = getGameObjects();
        if (gameObjects == null) return;

        for (Alien alien : gameObjects.getAliens()) {
            BufferedImage buf = alien.getBuffer();
            buffer.setRGB(
                    (int) alien.getLeftUpper().getX(),
                    (int) alien.getLeftUpper().getY(),
                    alien.getWidth(), alien.getHeight(),
                    alien.getBufferData(), 0, buf.getWidth());
        }
        for (Man man : gameObjects.getMen()) {
            BufferedImage buf = man.getBuffer();
            buffer.setRGB(
                    (int) man.getLeftUpper().getX(),
                    (int) man.getLeftUpper().getY(),
                    man.getWidth(), man.getHeight(),
                    man.getBufferData(), 0, buf.getWidth());
        }
        for (Bomb bomb : gameObjects.getBombs()) {
            BufferedImage buf = bomb.getBuffer();
            buffer.setRGB(
                    (int) bomb.getLeftUpper().getX(),
                    (int) bomb.getLeftUpper().getY(),
                    bomb.getWidth(), bomb.getHeight(),
                    bomb.getBufferData(), 0, buf.getWidth());
        }
        for (Rocket rocket : gameObjects.getRockets()) {
            BufferedImage buf = rocket.getBuffer();
            buffer.setRGB(
                    (int) rocket.getLeftUpper().getX(),
                    (int) rocket.getLeftUpper().getY(),
                    rocket.getWidth(), rocket.getHeight(),
                    rocket.getBufferData(), 0, buf.getWidth());
        }
        Launcher launcher = gameObjects.getLauncher();
        BufferedImage buf = launcher.getBuffer();
        buffer.setRGB(
                (int) launcher.getLeftUpper().getX(),
                (int) launcher.getLeftUpper().getY(),
                launcher.getWidth(), launcher.getHeight(),
                launcher.getBufferData(), 0, buf.getWidth());

        getGraphics().drawImage(buffer, 0, 0, this);
    }

    public GameObjects getGameObjects() {
        return controller.getGameObjects();
    }
}
