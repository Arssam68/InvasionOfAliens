package com.basic;

import com.basic.model.Direction;
import com.basic.model.Movable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

import static com.basic.model.Direction.UP;

public class Rocket implements Runnable {
    private final int UPDATE_TIME = 25;
    public static final int WIDTH = 10;
    public static final int HEIGHT = 30;
    private static final String IMAGE_FILENAME = "src\\com\\basic\\resources\\Rocket.jpg";
    public static final int SPEED = 5;
    private static int currentNumber = 0;

    private int x;
    private int y;
    private int width;
    private int height;
    private boolean isActive;
    private Point leftUpper;
    private Direction direction;
    private int speed;
    private Thread thread;
    private String imageFilename;
    private BufferedImage buffer;
    private int[] bufferData;

    public Rocket(int x, int y) {
        this.x = x;
        this.y = y;
        width = WIDTH;
        height = HEIGHT;
        imageFilename = IMAGE_FILENAME;
        leftUpper = new Point(x - width / 2, y - height / 2);
        direction = UP;
        speed = SPEED;
        isActive = true;
        currentNumber++;
        thread = new Thread(this, "Rocket: " + String.valueOf(currentNumber));

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

        startRocketMove();
    }

    public void startRocketMove() {
        thread.start();
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Thread getThread() {
        return thread;
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
            case UP:
                y -= speed;
                if (y < height / 2) {
                    y = height / 2;
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

    public static int getCurrentNumber() {
        return currentNumber;
    }
}