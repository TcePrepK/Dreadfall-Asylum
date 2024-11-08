package shaderUtils;

import toolbox.Logger;

import java.util.HashMap;
import java.util.HashSet;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;

public class UniformLoader {
    private final HashSet<String> nonExistentUniforms = new HashSet<>();
    private final HashMap<String, UniformVariable<?>> nameToUniform = new HashMap<>();
    private final int programID;

    public UniformLoader(final int programID) {
        this.programID = programID;
    }

    public <T> void loadUniform(final String uniformName, final T data) {
        final UniformVariable<T> uniform = getUniform(uniformName);
        if (uniform == null) return;
        uniform.loadValue(data);
    }

    public UniformVariable<?> registerUniform(final String uniformName) {
        final int location = glGetUniformLocation(programID, uniformName);
        if (location == -1) {
            nonExistentUniforms.add(uniformName);
            Logger.warn("Uniform \"" + uniformName + "\" isn't used!");
            return null;
        }

        return new UniformVariable<>(location);
    }

    private <T> UniformVariable<T> getUniform(final String uniformName) {
        if (nonExistentUniforms.contains(uniformName)) return null;

        UniformVariable<T> uniform = (UniformVariable<T>) nameToUniform.get(uniformName);
        if (uniform == null) {
            final int location = glGetUniformLocation(programID, uniformName);
            if (location == -1) {
                nonExistentUniforms.add(uniformName);
                Logger.warn("Uniform " + uniformName + " doesn't exist!");
                return null;
            }

            uniform = new UniformVariable<>(location);
            nameToUniform.put(uniformName, uniform);
        }

        return uniform;
    }
}
