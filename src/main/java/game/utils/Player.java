package game.utils;

import game.subWindows.Screen;
import toolbox.Vector2D;
import toolbox.Mouse;
import toolbox.VariableWatcher;

public class Player {
    public static VariableWatcher<Vector2D> position = new VariableWatcher<>(Screen.CENTER);
    public static VariableWatcher<Vector2D> lookingDirection = new VariableWatcher<>(new Vector2D(1, 0));
    public static float lightPower = 2;

    static {
        Mouse.movementListener.add(() -> {
            final Vector2D toLight = Mouse.getMousePos().sub(position.get());
            lookingDirection.set(toLight.mult(-1).normalize());
        });
    }
}
