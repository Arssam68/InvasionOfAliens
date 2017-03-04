package com.basic;

import com.basic.model.Direction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

import static com.basic.model.Direction.LEFT;
import static com.basic.model.Direction.RIGHT;

public class Man implements Runnable{
    public static final int WIDTH = 20;
    public static final int HEIGHT = 40;
    private static final String IMAGE_L_FILENAME = "src\\com\\basic\\resources\\WalkingManL.jpg";
    private static final String IMAGE_R_FILENAME = "src\\com\\basic\\resources\\WalkingManR.jpg";

    private int x;
    private int y;
    private int width;
    private int height;
    private Point leftUpper;
    private Direction direction;
    private int speed;
    private String imageLFilename;
    private BufferedImage bufferL;
    private int[] bufferLData;
    private String imageRFilename;
    private BufferedImage bufferR;
    private int[] bufferRData;

    public Man(int x, int speed) {
        width = WIDTH;
        height = HEIGHT;
        this.x = x;
        y = Game.FRAME_HEIGHT - height - 10;
        imageLFilename = IMAGE_L_FILENAME;
        imageRFilename = IMAGE_R_FILENAME;
        leftUpper = new Point(x - width / 2, y - height / 2);
        direction = (int) (Math.random() * 10) < 5 ? LEFT : RIGHT;
        this.speed = speed;

        BufferedImage bi = null;
        try {
            bi = ImageIO.read(new File(imageLFilename));
        } catch (IOException e) {
            System.out.println(String.format("Файл %s не найден", imageLFilename));
        }
        bufferL = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int a = bi.getRGB(i, j);
                bufferL.setRGB(i, j, a);
            }
        }
        bufferLData = ((DataBufferInt) bufferL.getRaster().getDataBuffer()).getData();
        try {
            bi = ImageIO.read(new File(imageRFilename));
        } catch (IOException e) {
            System.out.println(String.format("Файл %s не найден", imageRFilename));
        }
        bufferR = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int a = bi.getRGB(i, j);
                bufferR.setRGB(i, j, a);
            }
        }
        bufferRData = ((DataBufferInt) bufferR.getRaster().getDataBuffer()).getData();

        startManMove();
    }

    public void startManMove() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        int speedChangePeriod = 100;
        while (true) {
            if (speedChangePeriod-- == 0) {
                speed = (int) (Math.random() * 3 + 1);
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
                if (x > Game.FRAME_WIDTH - width / 2 - 1) {
                    x = Game.FRAME_WIDTH - width / 2 - 1;
                    direction = LEFT;
                }
        }
        leftUpper.setLocation(x - width / 2, y - height / 2);
    }

    public BufferedImage getBuffer() {
        BufferedImage buffer;
        return buffer = direction == LEFT ? bufferL : bufferR;
    }

    public int[] getBufferData() {
        int[] bufferData;
        return bufferData = direction == LEFT ? bufferLData : bufferRData;
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
