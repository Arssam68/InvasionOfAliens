package com.basic;

import com.basic.model.GameObject;
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

    public void swapBuffers() {
        getGraphics().drawImage(buffer, 0, 0, this);
    }

    public void render() {
        GameObjects gameObjects = getGameObjects();
        if (gameObjects == null) return;

        for (GameObject gameObject : gameObjects.getAll()) {
            if (gameObject instanceof Alien) {
                BufferedImage buf = ((Alien) gameObject).getBuffer();
                buffer.setRGB(
                        (int) ((Alien) gameObject).getLeftUpper().getX(),
                        (int) ((Alien) gameObject).getLeftUpper().getY(),
                        buf.getWidth(),
                        buf.getHeight(),
                        ((Alien) gameObject).getBufferData(), 0, buf.getWidth());
            }
        }
    }

    /*@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }*/

    /*@Override
    public void paint (Graphics graphics) {
        render();
        /*GameObjects gameObjects = getGameObjects();
        if (gameObjects == null) return;
        for (GameObject gameObject : gameObjects.getAll()) {
            gameObject.draw(graphics);
        }
    }*/

    public GameObjects getGameObjects() {
        return controller.getGameObjects();
    }
}
