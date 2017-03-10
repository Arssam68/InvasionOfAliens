package com.basic;

import com.basic.controller.Controller;
import com.basic.controller.EventListener;
import com.basic.controller.Input;
import com.basic.model.Direction;
import com.basic.model.GameObjects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class View extends JPanel {
    public static final int BG_COLOR = Game.CLEAR_COLOR;

    private boolean isCreated = false;
    private JFrame window;
    private Canvas content;

    private BufferedImage buffer;
    private int[] bufferData;
    private Graphics bufferGrafics;
    private int clearColor;

    private BufferStrategy bufferStrategy;

    private Controller controller;

    public View(Controller controller) {
        this.controller = controller;
    }

    public void create(int width, int height, String title, int _clearColor, int numBuffers) {
        if (isCreated) return;

        window = new JFrame(title);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        content = new Canvas();

        Dimension size = new Dimension(width, height);
        content.setPreferredSize(size);

        window.setResizable(false);
        window.getContentPane().add(content);
        //JLabel fpsLable = new JLabel("Test");
        //fpsLable.setLocation(0, 768);
        //window.getContentPane().add(fpsLable);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        bufferData = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();
        bufferGrafics = buffer.getGraphics();
        ((Graphics2D) buffer.getGraphics()).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        clearColor = _clearColor;

        content.createBufferStrategy(numBuffers);
        bufferStrategy = content.getBufferStrategy();
        isCreated = true;
    }

    public void clear() {
        Arrays.fill(bufferData, BG_COLOR);
    }

    public void swapBuffers() {
        Graphics g = bufferStrategy.getDrawGraphics();
        g.drawImage(buffer, 0, 0, null);
        bufferStrategy.show();
    }

    public Graphics2D getGraphics() {
        return (Graphics2D) bufferGrafics;
    }

    public void destroy() {
        if (isCreated) window.dispose();
    }

    public void setTitle(String title) {
        window.setTitle(title);
    }

    public void render() {
        clear();

        GameObjects gameObjects = controller.getGameObjects();
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

        swapBuffers();
    }

    public GameObjects getGameObjects() {
        return controller.getGameObjects();
    }

    public void addInputListener(Input inputListener) {
        window.add(inputListener);
    }

    public void gameWon() {
        JOptionPane.showMessageDialog(this, "Земляне победили! :-)");
    }

    public void gameLost() {
        JOptionPane.showMessageDialog(this, "Инопланетяне победили :-(");
    }

    public void gameDraw() {
        JOptionPane.showMessageDialog(this, "Боевая ничья :-|");
    }
}
