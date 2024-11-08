package shaderUtils;

public class UniformVariable<T> {
    private final int location;
    private T prevData;

    public UniformVariable(final int location) {
        this.location = location;
    }

    public void loadValue(final T data) {
        if (data.equals(prevData)) return;
        UniformRegistry.loadValue(location, data);
        prevData = data;
    }
}
