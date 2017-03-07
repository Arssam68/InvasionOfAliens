package com.basic;

import com.basic.model.Direction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Set;

import static com.basic.model.Direction.DOWN;

public class Bomb implements Runnable {
    private final int UPDATE_TIME = 25;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 20;
    private static final String IMAGE_FILENAME = "src\\com\\basic\\resources\\Aviabomb.jpg";
    public static final int SPEED = 3;
    private static int currentNumber = 0;

    private int x;
    private int y;
    private int width;
    private int height;
    private boolean isActive;
    private Point leftUpper;
    private Direction direction;
    private int speed;
    private String imageFilename;
    private BufferedImage buffer;
    private int[] bufferData;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
        width = WIDTH;
        height = HEIGHT;
        imageFilename = IMAGE_FILENAME;
        leftUpper = new Point(x - width / 2, y - height / 2);
        direction = DOWN;
        speed = SPEED;
        isActive = true;
        currentNumber++;

        BufferedImage bi = null;
        try {
            bi = ImageIO.read(new File(imageFilename));
        } catch (IOException e) {
            System.out.println(String.format("Файл %s не найден", imageFilename));
        }
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int a = bi.getRGB(i, j);
                buffer.setRGB(i, j, a);
            }
        }
        bufferData = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();

        startBombMove();
    }

    public boolean isActive() {
        return isActive;
    }

    public void startBombMove() {
        Thread t = new Thread(this, " Bomb: " + String.valueOf(currentNumber));
        t.start();
    }

    @Override
    public void run() {
        while (true) {
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
            case DOWN:
                y += speed;
                if (y > Game.HEIGHT - height / 2) {
                    y = Game.HEIGHT - height / 2;
                    isActive = false;
                    Thread.currentThread().interrupt();
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
}