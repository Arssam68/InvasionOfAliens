package com.basic;

import com.basic.model.Direction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import static com.basic.model.Direction.LEFT;
import static com.basic.model.Direction.RIGHT;

public class Man implements Runnable {
    private final int UPDATE_TIME = 25;
    public static final int WIDTH = 20;
    public static final int HEIGHT = 40;
    private static final String IMAGE_L_FILENAME = "src\\com\\basic\\resources\\WalkingManL.jpg";
    private static final String IMAGE_R_FILENAME = "src\\com\\basic\\resources\\WalkingManR.jpg";
    private static int currentNumber = 0;

    private int x;
    private int y;
    private int width;
    private int height;
    private Point leftUpper;
    private Direction direction;
    private int speed;
    private boolean isActive;
    private Thread thread;
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
        y = Game.HEIGHT - height / 2;
        imageLFilename = IMAGE_L_FILENAME;
        imageRFilename = IMAGE_R_FILENAME;
        leftUpper = new Point(x - width / 2, y - height / 2);
        direction = (int) (Math.random() * 10) < 5 ? LEFT : RIGHT;
        this.speed = speed;
        isActive = true;
        currentNumber++;
        thread = new Thread(this, "Man: " + String.valueOf(currentNumber));

        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        try {
            bi = ImageIO.read(new File(imageLFilename));
        } catch (IOException e) {
            System.out.println(String.format("Файл %s не найден", imageLFilename));
        }
        bufferL = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        int[] pixels = new int[width * height];
        bi.getRGB(0, 0, width, height, pixels, 0, width);
        /*for (int i = 0; i < pixels.length; i++) {
            if ((pixels[i] & 0xffffffff) == Game.CLEAR_COLOR)
                pixels[i] &= 0xffffff;
        }*/
        WritableRaster raster = bufferL.getRaster();

        raster.setDataElements(0, 0, width, height, pixels);
        bufferL.setData(raster);

        bufferLData = ((DataBufferInt) bufferL.getRaster().getDataBuffer()).getData();

        bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        try {
            bi = ImageIO.read(new File(imageRFilename));
        } catch (IOException e) {
            System.out.println(String.format("Файл %s не найден", imageRFilename));
        }
        bufferR = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        bi.getRGB(0, 0, width, height, pixels, 0, width);
        /*for (int i = 0; i < pixels.length; i++) {
            if ((pixels[i] & 0xffffffff) == Game.CLEAR_COLOR)
                pixels[i] &= 0xffffff;
        }*/
        raster = bufferR.getRaster();

        raster.setDataElements(0, 0, width, height, pixels);
        bufferR.setData(raster);

        bufferRData = ((DataBufferInt) bufferR.getRaster().getDataBuffer()).getData();

        startManMove();
    }

    public void startManMove() {
        new Thread(this, " Man: " + String.valueOf(currentNumber)).start();
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
        BufferedImage buffer;
        return buffer = direction == LEFT ? bufferL : bufferR;
    }

    public int[] getBufferData() {
        int[] bufferData;
        return bufferData = direction == LEFT ? bufferLData : bufferRData;
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

    public static void setCurrentNumber(int currentNumber) {
        Man.currentNumber = currentNumber;
    }
}
