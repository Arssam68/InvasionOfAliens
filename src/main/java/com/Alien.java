package com;

import com.model.Direction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.*;
import java.util.Observable;

import static com.model.Direction.LEFT;
import static com.model.Direction.RIGHT;

public class Alien extends Observable implements Runnable{
    private final int UPDATE_TIME = 25;
    private final int BOMB_CAPACITY = 100;
    public static final int WIDTH = 70;
    private static final int HEIGHT = 20;
    private static final int ALTITUDE = HEIGHT / 2;
    private static final String IMAGE_FILENAME = "/Alien.jpg";
    private static int currentNumber = 0;

    private int x;
    private int y;
    private int width;
    private int height;
    private int alt;
    private Point leftUpper;
    private Direction direction;
    private int speed;
    private int bombQuantity;
    private boolean isActive;
    private Thread thread;
    private BufferedImage buffer;
    private int[] bufferData;

    public Alien(int x, int speed, int alt) {
        width = WIDTH;
        height = HEIGHT;
        this.alt = alt;
        this.x = x;
        y = ALTITUDE * 2 * alt - height / 2;
        leftUpper = new Point(x - width / 2, y - height / 2);
        direction = (int) (Math.random() * 10) < 5 ? LEFT : RIGHT;
        this.speed = speed;
        bombQuantity = BOMB_CAPACITY;
        isActive = true;
        currentNumber++;
        thread = new Thread(this, "Alien: " + String.valueOf(currentNumber));

        BufferedImage bi = null;
        try {
            bi = ImageIO.read(getClass().getResource(IMAGE_FILENAME));
        } catch (IOException e) {
            System.out.println(String.format("Файл %s не найден", IMAGE_FILENAME));
        }
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int a = bi.getRGB(i, j);
                buffer.setRGB(i, j, a);
            }
        }
        bufferData = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();

        startAlienMove();
    }

    public void startAlienMove() {
        thread.start();
    }

    public boolean isActive() {
        return isActive;
    }

    public Thread getThread() {
        return thread;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public void run() {
        int speedChangePeriod = UPDATE_TIME * 4;
        int bombFlushPeriod = (int) (Math.random() * UPDATE_TIME * 3 + 40);
        while (true) {
            if (speedChangePeriod-- == 0) {
                speed = (int) (Math.random() * 4 + 1);
                speedChangePeriod = 100;
            }
            if (bombFlushPeriod-- == 0 && bombQuantity != 0) {
                setChanged();
                notifyObservers();
                if (--bombQuantity != 0)
                bombFlushPeriod = (int) (Math.random() * UPDATE_TIME * 6 + 40);
                else {
                    isActive = false;
                }
            }
            move();
            try {
                Thread.sleep(UPDATE_TIME);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public synchronized void move() {
        switch (direction) {
            case LEFT:
                x -= speed;
                if (x < width / 2) {
                    x = width / 2;
                    direction = RIGHT;
                }
                break;
            case RIGHT:
                x += speed;
                if (x > Game.WIDTH - width / 2 - 1) {
                    x = Game.WIDTH - width / 2 - 1;
                    direction = LEFT;
                }
        }
        leftUpper.setLocation(x - width / 2, y - height / 2);
    }

    public BufferedImage getBuffer() {
        return buffer;
    }

    public int[] getBufferData() {
        return bufferData;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Point getLeftUpper() {
        return leftUpper;
    }

    public int getAlt() {
        return alt;
    }

    public static int getCurrentNumber() {
        return currentNumber;
    }

    public static void setCurrentNumber(int currentNumber) {
        Alien.currentNumber = currentNumber;
    }
}
