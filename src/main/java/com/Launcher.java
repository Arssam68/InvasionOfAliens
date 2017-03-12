package com;

import com.model.Direction;
import com.model.Movable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.EventListener;

public class Launcher implements Movable, EventListener {
    private final int MAX_ROCKET_CAPACITY = 100;
    public static final int WIDTH = 80;
    private static final int HEIGHT = 10;
    private static final String IMAGE_FILENAME = "/Launcher.jpg";
    private static final int SPEED = 6;
    public static int LAUNCH_INTERVAL = (int) Game.UPDATE_RATE;
    public static final int LOST_ROCKETS = 10;

    private int x;
    private int y;
    private int width;
    private int height;
    private Point leftUpper;
    private boolean isReady;
    private Direction direction;
    private int speed;
    private int rocketQuantity;
    private BufferedImage buffer;
    private int[] bufferData;
    private int launchInterval;

    public Launcher(int x) {
        width = WIDTH;
        height = HEIGHT;
        this.x = x;
        y = Game.HEIGHT - Man.HEIGHT - height;
        isReady = true;
        leftUpper = new Point(x - width / 2, y - height / 2);
        direction = (int) (Math.random() * 10) < 5 ? Direction.LEFT : Direction.RIGHT;
        speed = SPEED;
        rocketQuantity = MAX_ROCKET_CAPACITY;
        launchInterval = LAUNCH_INTERVAL;

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
    }

    @Override
    public synchronized void move(Direction direction) {
        switch (direction) {
            case LEFT:
                x -= speed;
                if (x < width / 2) {
                    x = width / 2;
                    direction = Direction.RIGHT;
                }
                break;
            case RIGHT:
                x += speed;
                if (x > Game.WIDTH - width / 2 - 1) {
                    x = Game.WIDTH - width / 2 - 1;
                    direction = Direction.LEFT;
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

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public int getLaunchInterval() {
        return launchInterval;
    }

    public void setLaunchInterval(int launchInterval) {
        this.launchInterval = launchInterval;
    }

    public int getRocketQuantity() {
        return rocketQuantity;
    }

    public void setRocketQuantity(int rocketQuantity) {
        this.rocketQuantity = rocketQuantity;
    }
}