package core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class test extends JFrame {
    private Robot robot;

    public test() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        addMouseMotionListener(new CustomMouseMotionListener());
    }

    private class CustomMouseMotionListener implements MouseMotionListener {
        private int prevX = Integer.MAX_VALUE;
        private int prevY = Integer.MAX_VALUE;

        @Override
        public void mouseMoved(MouseEvent e) {
            int newX = e.getX();
            int newY = e.getY();

            if (prevX == Integer.MAX_VALUE) {
                prevX = newX;
                prevY = newY;
                return;
            }

            float sensitivity = 0.1f;
            newX = (int) Math.floor(prevX + (newX - prevX) * sensitivity);
            newY = (int) Math.floor(prevY + (newY - prevY) * sensitivity);

            robot.mouseMove(newX, newY);
            prevX = newX;
            prevY = newY;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            // Handle mouse dragged events if needed
        }
    }
}
