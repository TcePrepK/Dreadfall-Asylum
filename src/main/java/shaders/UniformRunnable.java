package shaders;

public interface UniformRunnable<T> {
    public void run(int location, T data);
}
