package game.subWindows;

import core.BaseShader;
import toolbox.ShaderManager;

public abstract class BaseWindowShader extends BaseShader {
    public BaseWindowShader(final String name) {
        super(name, name + "/main.vert", name + "/main.frag");
        ShaderManager.addShader(this);
    }
}
