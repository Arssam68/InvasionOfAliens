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

public class Launcher implements Runnable, Movable {
    private final int MAX_ROCKET_CAPACITY = 1000;
    public static final int WIDTH = 80;
    private static final int HEIGHT = 10;
    private static final String IMAGE_FILENAME = "src\\com\\basic\\resources\\launcher.jpg";
    private static final int SPEED = 4;

    private int x;
    private int y;
    private int width;
    private int height;
    private Point leftUpper;
    private Direction direction;
    private int speed;
    private int rocketQuantity;
    private String imageFilename;
    private BufferedImage buffer;
    private int[] bufferData;

    public Launcher(int x) {
        width = WIDTH;
        height = HEIGHT;
        this.x = x;
        y = Main.FRAME_HEIGHT - Man.HEIGHT - height - 20;
        imageFilename = IMAGE_FILENAME;
        leftUpper = new Point(x - width / 2, y - height / 2);
        direction = (int) (Math.random() * 10) < 5 ? LEFT : RIGHT;
        speed = SPEED;
        rocketQuantity = MAX_ROCKET_CAPACITY;

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

        startLauncherMove();
    }

    public void startLauncherMove() {
        new Thread(this).start();
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
            case LEFT:
                x -= speed;
                if (x < width / 2) {
                    x = width / 2;
                    direction = RIGHT;
                }
                break;
            case RIGHT:
                x += speed;
                if (x > Main.FRAME_WIDTH - width / 2 - 1) {
                    x = Main.FRAME_WIDTH - width / 2 - 1;
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