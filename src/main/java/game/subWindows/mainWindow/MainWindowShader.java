package game.subWindows.mainWindow;

import core.GameRoot;
import game.subWindows.BaseWindowShader;
import toolbox.VariableWatcher;
import toolbox.Vector2D;
import toolbox.Vector2DI;

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
