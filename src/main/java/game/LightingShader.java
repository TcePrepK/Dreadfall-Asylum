package game;

import math.Vector2D;
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

    public void loadPlayer() {
        loadUniform("lightPos", Player.position);
        loadUniform("lightDir", Player.lightDirection);
        loadUniform("lightPower", Player.lightPower);
    }
}
