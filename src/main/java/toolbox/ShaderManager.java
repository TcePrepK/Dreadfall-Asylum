package toolbox;

import core.BaseShader;

import java.util.ArrayList;

public class ShaderManager {
    public static final ArrayList<BaseShader> allShaders = new ArrayList<>();

    public static <T extends BaseShader> void addShader(final T shader) {
        allShaders.add(shader);
    }

    public static void cleanUp() {
        for (final BaseShader shader : allShaders) {
            shader.cleanUp();
        }
    }
}
