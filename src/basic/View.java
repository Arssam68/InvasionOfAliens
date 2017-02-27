package basic;

import javax.swing.*;
import java.awt.*;

public class View extends JPanel {
    private static final Color BG_COLOR = Color.LIGHT_GRAY;

    private Controller controller;

    boolean isGameWon = false;
    boolean isGameLost = false;

    View(Controller controller) {
        setFocusable(true);
        this.controller = controller;
        addKeyListener(controller);
        this.setBackground(BG_COLOR);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        graphics.setColor(BG_COLOR);
    }
}
