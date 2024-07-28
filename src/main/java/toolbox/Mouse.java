package toolbox;

import math.Vector2D;

import java.awt.*;

public class Mouse {
    public static Signal movementListener = new Signal();

    public static Vector2D getMousePos() {
        final Point pos = MouseInfo.getPointerInfo().getLocation();
        return new Vector2D(pos.x, pos.y);
    }

    static {
        // TODO: Dispatch movement listener for mouse events!
    }
}
