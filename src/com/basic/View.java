package com.basic;

import com.basic.model.GameObject;
import com.basic.model.GameObjects;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class View extends JPanel {
    private static final Color BG_COLOR = Color.white;
    private BufferedImage buffer;
    private int[] bufferData;
    private Graphics bufferGrafics;

    private Controller controller;

    boolean isGameWon = false;
    boolean isGameLost = false;

    View(Controller controller) {
        setFocusable(true);
        this.controller = controller;
        setBackground(BG_COLOR);
        buffer = new BufferedImage(Main.FRAME_WIDTH, Main.FRAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        bufferData = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();
        bufferGrafics = buffer.getGraphics();
    }

    public void clear() {
        Arrays.fill(bufferData, BG_COLOR.getRGB());
    }

    public void swapBuffers() {
        Graphics g = getGraphics();
        g.drawImage(buffer, 0, 0, null);
    }

    public void render() {
        GameObjects gameObjects = getGameObjects();
        if (gameObjects == null) return;
        BufferedImage buffer = null;
        for (GameObject gameObject : gameObjects.getAll()) {
            if (gameObject instanceof Alien) {
                buffer = ((Alien) gameObject).getBuffer();
                gameObject.draw(buffer.createGraphics());
            }
        }
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.setColor(BG_COLOR);
        GameObjects gameObjects = getGameObjects();
        if (gameObjects == null) return;
        for (GameObject gameObject : gameObjects.getAll()) {
            gameObject.draw(graphics);
        }
    }

    public GameObjects getGameObjects() {
        return controller.getGameObjects();
    }
}
