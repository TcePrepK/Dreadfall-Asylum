package game;

import core.PixelShader;
import game.utils.Player;
import toolbox.Vector2D;
import core.BaseShader;

public class LightingShader extends PixelShader {
    public static final String VERTEX_FILE = "lighting/main.vert";
    public static final String FRAGMENT_FILE = "lighting/main.frag";

    public LightingShader() {
        super("Lighting Shader", VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadWindowLeftBottom(final Vector2D lb) {
        loadUniform("windowsLeftBottom", lb);
    }

    public void loadResolution(final Vector2D resolution) {
        loadUniform("resolution", resolution);
    }

    public void attachPlayerLight() {
        attachWatcher("lightPos", Player.position);
        attachWatcher("lightDir", Player.lookingDirection);
    }

    public void loadPlayer() {
        loadUniform("lightPower", Player.lightPower);
    }
}
