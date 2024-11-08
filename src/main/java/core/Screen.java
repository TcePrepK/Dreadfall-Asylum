package core;

import toolbox.Vector2D;

import java.awt.*;

public class Screen {
    public static final int WIDTH, HEIGHT;
    public static final Vector2D CENTER;
    public static final Vector2D SIZE;

    static {
        final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        final GraphicsDevice gd = ge.getDefaultScreenDevice();
        final DisplayMode mode = gd.getDisplayMode();

        WIDTH = mode.getWidth();
        HEIGHT = mode.getHeight();
        CENTER = new Vector2D((float) WIDTH / 2, (float) HEIGHT / 2);
        SIZE = new Vector2D(WIDTH, HEIGHT);
    }
}
