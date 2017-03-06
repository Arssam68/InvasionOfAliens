package com.basic;

import com.basic.model.Direction;
import com.basic.model.Movable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.EventListener;

import static com.basic.model.Direction.LEFT;
import static com.basic.model.Direction.RIGHT;

public class Launcher implements Movable, EventListener {
    private final int MAX_ROCKET_CAPACITY = 1000;
    public static final int WIDTH = 80;
    private static final int HEIGHT = 10;
    private static final String IMAGE_FILENAME = "src\\com\\basic\\resources\\Launcher.jpg";
    private static final int SPEED = 6;
    public static int LAUNCH_INTERVAL = (int) Game.UPDATE_RATE;

    private int x;
    private int y;
    private int width;
    private int height;
    private Point leftUpper;
    private boolean isReady;
    private Direction direction;
    private int speed;
    private int rocketQuantity;
    private String imageFilename;
    private BufferedImage buffer;
    private int[] bufferData;
    private int launchInterval;

    public Launcher(int x) {
        width = WIDTH;
        height = HEIGHT;
        this.x = x;
        y = Game.HEIGHT - Man.HEIGHT - height;
        isReady = true;
        imageFilename = IMAGE_FILENAME;
        leftUpper = new Point(x - width / 2, y - height / 2);
        direction = (int) (Math.random() * 10) < 5 ? LEFT : RIGHT;
        speed = SPEED;
        rocketQuantity = MAX_ROCKET_CAPACITY;
        launchInterval = LAUNCH_INTERVAL;

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
    }

    @Override
    public synchronized void move(Direction direction) {
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
}