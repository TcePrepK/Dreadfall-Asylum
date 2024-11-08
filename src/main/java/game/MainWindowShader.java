package game;

import core.GameRoot;
import core.PixelShader;
import toolbox.Vector2D;

public class MainWindowShader extends PixelShader {
    public static String VERTEX_FILE = "mainWindow/main.vert";
    public static String FRAGMENT_FILE = "mainWindow/main.frag";

    public MainWindowShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void setupAttachments(GameRoot root) {
        attachWatcher("time", root.time);
    }

    public void loadResolution(final Vector2D resolution) {
        loadUniform("resolution", resolution);
    }
}
