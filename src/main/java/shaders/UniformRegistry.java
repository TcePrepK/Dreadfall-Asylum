package shaders;

import toolbox.Vector2D;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL20;
import toolbox.Logger;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class UniformRegistry {
    private static final Map<Class<?>, UniformRunnable<?>> classToFunction = new HashMap<>();

    static {
        UniformRegistry.addFunction(Boolean.class, (UniformRunnable<Boolean>) (location, v) -> glUniform1i(location, v ? 1 : 0));
        UniformRegistry.addFunction(Integer.class, (UniformRunnable<Integer>) GL20::glUniform1i);
        UniformRegistry.addFunction(Float.class, (UniformRunnable<Float>) GL20::glUniform1f);
        UniformRegistry.addFunction(Double.class, (UniformRunnable<Double>) (location, v) -> glUniform1f(location, v.floatValue()));
        UniformRegistry.addFunction(Vector2D.class, (UniformRunnable<Vector2D>) (location, v) -> glUniform2f(location, v.x, v.y));
        UniformRegistry.addFunction(Matrix4f.class, (UniformRunnable<Matrix4f>) (location, v) -> glUniformMatrix4fv(location, false, v.get(new float[16])));
    }

    private static void addFunction(final Class<?> c, final UniformRunnable<?> r) {
        UniformRegistry.classToFunction.put(c, r);
    }

    protected static <T> void loadValue(final int location, final T data) {
        final Class<?> key = data.getClass();
        if (UniformRegistry.classToFunction.containsKey(key)) {
            final UniformRunnable<T> runnable = (UniformRunnable<T>) UniformRegistry.classToFunction.get(key);
            runnable.run(location, data);
            return;
        }

        Logger.error("Data with the type of " + data.getClass().getSimpleName() + " couldn't have been loaded!");
    }
}
