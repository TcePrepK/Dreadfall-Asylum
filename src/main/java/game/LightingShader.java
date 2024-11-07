package game;

import game.utils.Player;
import toolbox.Vector2D;
import shaders.BaseShader;

public class LightingShader extends BaseShader {
    public LightingShader() {
        super("LightingShader", "lighting/main.vert", "lighting/main.frag");
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
