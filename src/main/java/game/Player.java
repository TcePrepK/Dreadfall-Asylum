package game;

import game.windows.Screen;
import math.Vector2D;
import toolbox.Mouse;

public class Player {
    public static Vector2D position = Screen.CENTER;
    public static Vector2D lightDirection = new Vector2D(1, 0);
    public static float lightPower = 2;

    static {
        Mouse.movementListener.add(() -> {
            final Vector2D toLight = Mouse.getMousePos().sub(position);
            lightDirection = toLight.mult(-1).normalize();
        });
    }
}
