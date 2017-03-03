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

public class Rocket implements Runnable, Movable {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 40;
    private static final String IMAGE_FILENAME = "src\\com\\basic\\resources\\rocket.jpg";
    public static final int SPEED = 5;

    private int x;
    private int y;
    private int width;
    private int height;
    private Point leftUpper;
    private Direction direction;
    private int speed;
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

        startRocetMove();
    }

    public void startRocetMove() {
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        while (true) {
            move();
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    @Override
    public synchronized void move() {
        switch (direction) {
            case UP:
                y -= speed;
                if (y < height / 2) {
                    y = height / 2;
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