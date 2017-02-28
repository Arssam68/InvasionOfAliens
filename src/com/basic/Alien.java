package com.basic;

import com.basic.move.Direction;
import com.basic.move.Moveable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Alien extends JPanel implements Moveable {
    private final int BOMB_CAPACITY = 100;
    private int bombQuantity;
    private boolean isActive;
    private int width;
    private int height;
    private BufferedImage buffer;
    private Image image;
    private String filename = "src\\com\\basic\\resources\\ufo-spaceship-flying-saucer.jpg";
    private Direction direction;

    Alien(int width, int height, boolean isActive) {
        super();
        this.width = width;
        this.height = height;
        this.isActive = isActive;
        bombQuantity = BOMB_CAPACITY;
        try {
            image = ImageIO.read(new File(filename));
        } catch (IOException e) {
            System.out.println(String.format("Файл %s не найден", filename));
        }
        direction = (int) (Math.random() * 10) < 5 ? Direction.LEFT : Direction.RIGHT;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    private void rebuildBuffer(){
        int w = getWidth();
        int h = getHeight();
        buffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffer.createGraphics();
        g2d.setPaint(Color.darkGray);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(image, 0, 0, null);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (buffer == null) {
            rebuildBuffer();
        }
        g.drawImage(buffer, 0, 0, this);
    }

    @Override
    public void move(Direction direction) {

    }
}
