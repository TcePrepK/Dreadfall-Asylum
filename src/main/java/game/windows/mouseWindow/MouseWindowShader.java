package game.windows.mouseWindow;

import game.windows.BaseWindowShader;
import math.Vector2D;

public class MouseWindowShader extends BaseWindowShader {
    public MouseWindowShader() {
        super("mouseWindow");
    }

    public void loadResolution(final Vector2D resolution) {
        loadUniform("resolution", resolution);
    }
}
