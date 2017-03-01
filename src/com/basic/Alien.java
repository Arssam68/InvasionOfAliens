package com.basic;

import com.basic.model.CollisionObject;
import com.basic.model.Direction;
import com.basic.model.Movable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.basic.model.Direction.LEFT;
import static com.basic.model.Direction.RIGHT;

public class Alien extends CollisionObject implements Runnable, Movable{
    private final int BOMB_CAPACITY = 100;
    public static final int WIDTH = 70;
    public  static final int HEIGHT = 20;

    private int bombQuantity;
    private int width;
    private int height;
    private int x;
    private int y;
    private BufferedImage buffer;
    private Image image;
    private String filename = "src\\com\\basic\\resources\\ufo-spaceship-flying-saucer.jpg";
    private Direction direction;
    private int speed;

    public Alien(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bombQuantity = BOMB_CAPACITY;
        try {
            image = ImageIO.read(new File(filename));
        } catch (IOException e) {
            System.out.println(String.format("Файл %s не найден", filename));
        }
        direction = (int) (Math.random() * 10) < 5 ? LEFT : RIGHT;
        speed = 1;

        startAlienMove();
    }

    @Override
    public void draw(Graphics graphics) {
        paintComponent(graphics);
    }

    public BufferedImage getBuffer() {
        return buffer;
    }

    private void rebuildBuffer() {
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffer.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(image, 0, 0, null);
    }

    protected void paintComponent(Graphics g) {
        if (buffer == null) {
            rebuildBuffer();
        }
        g.drawImage(buffer, x - width / 2, y - height / 2, null);
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
