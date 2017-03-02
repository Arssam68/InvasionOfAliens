package com.basic;

import com.basic.model.Direction;
import com.basic.model.Movable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

import static com.basic.model.Direction.LEFT;
import static com.basic.model.Direction.RIGHT;

public class Alien implements Runnable, Movable{
    private final int BOMB_CAPACITY = 100;
    public static final int WIDTH = 70;
    public  static final int HEIGHT = 20;
    public  static final int ALTITUDE = HEIGHT / 2;
    public  static final String IMAGE_FILENAME = "src\\com\\basic\\resources\\ufo-spaceship-flying-saucer.jpg";

    private int x;
    private int y;
    private int width;
    private int height;
    private Point leftUpper;
    private Direction direction;
    private int speed;
    private int bombQuantity;
    private String imageFilename;
    private BufferedImage buffer;
    private int[] bufferData;

    public Alien(int x, int speed, int alt) {
        this.x = x;
        y = ALTITUDE * 2 * alt;
        width = WIDTH;
        height = HEIGHT;
        imageFilename = IMAGE_FILENAME;
        leftUpper = new Point(x - width / 2, y - height / 2);
        direction = (int) (Math.random() * 10) < 5 ? LEFT : RIGHT;
        this.speed = speed;
        bombQuantity = BOMB_CAPACITY;

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

        startAlienMove();
    }

    public void startAlienMove() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        int speedChangePeriod = 100;
        while (true) {
            if (speedChangePeriod-- == 0) {
                speed = (int) (Math.random() * 5);
                speedChangePeriod = 100;
            }
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
            case LEFT:
                x -= speed;
                if (x < width / 2) {
                    x = width / 2;
                    direction = RIGHT;
                }
                break;
            case RIGHT:
                x += speed;
                if (x > Main.FRAME_WIDTH - Alien.WIDTH / 2 - 1) {
                    x = Main.FRAME_WIDTH - Alien.WIDTH / 2 - 1;
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
}
