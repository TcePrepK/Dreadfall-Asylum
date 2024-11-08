package game;

import core.GameRoot;
import game.subWindows.BaseWindowShader;
import toolbox.Vector2D;

public class MainWindowShader extends BaseWindowShader {
    public MainWindowShader() {
        super("mainWindow");
    }

    public void initializeAttachments(GameRoot root) {
        attachWatcher("time", root.time);
    }

    public void loadResolution(final Vector2D resolution) {
        loadUniform("resolution", resolution);
    }
}
