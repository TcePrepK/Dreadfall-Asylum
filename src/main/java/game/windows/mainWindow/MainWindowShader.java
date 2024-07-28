package game.windows.mainWindow;

import game.windows.BaseWindowShader;
import math.Vector2D;

public class MainWindowShader extends BaseWindowShader {
    public MainWindowShader() {
        super("mainWindow");
    }

    public void loadResolution(final Vector2D resolution) {
        loadUniform("resolution", resolution);
    }

    public void loadTime(final double time) {
        loadUniform("time", time);
    }
}
