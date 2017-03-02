package com.basic;

import com.basic.model.CollisionObject;
import com.basic.model.Direction;
import com.basic.model.Movable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import static com.basic.model.Direction.LEFT;
import static com.basic.model.Direction.RIGHT;

public class Alien extends CollisionObject implements Runnable, Movable{
    private final int BOMB_CAPACITY = 100;
    public static final int WIDTH = 70;
    public  static final int HEIGHT = 20;

    private int x;
    private int y;
    private int width;
    private int height;
    private Direction direction;
    private int speed;
    private int bombQuantity;
    private Point leftUpper;
    private String filename = "src\\com\\basic\\resources\\ufo-spaceship-flying-saucer.jpg";
    private BufferedImage buffer;
    private int[] bufferData;
    private Graphics bufferGrafics;

    public Point getLeftUpper() {
        return leftUpper;
    }

    public BufferedImage getBuffer() {
        return buffer;
    }

    public int[] getBufferData() {
        return bufferData;
    }

    public Graphics getBufferGrafics() {
        return bufferGrafics;
    }

    public Alien(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        direction = (int) (Math.random() * 10) < 5 ? LEFT : RIGHT;
        speed = 2;
        bombQuantity = BOMB_CAPACITY;
        leftUpper = new Point(x - width / 2, y - height / 2);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(new File(filename));
        } catch (IOException e) {
            System.out.println(String.format("Файл %s не найден", filename));
        }
        buffer = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < bi.getWidth(); i++) {
            for (int j = 0; j < bi.getHeight(); j++) {
                int a = bi.getRGB(i, j);
                buffer.setRGB(i, j, a);
            }
        }
        bufferData = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();
        bufferGrafics = buffer.getGraphics();

        startAlienMove();
    }

    public void startAlienMove() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) {
            move();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    @Override
    public void move() {
        if (x <= width / 2) {
            x = width / 2;
            direction = RIGHT;
        } else if (x > Main.FRAME_WIDTH - Alien.WIDTH / 2 - 1) {
            x = Main.FRAME_WIDTH - Alien.WIDTH / 2 - 1;
            direction = LEFT;
        }
        switch (direction) {
            case LEFT:
                x -= speed;
                break;
            case RIGHT:
                x += speed;
        }
    }
}
