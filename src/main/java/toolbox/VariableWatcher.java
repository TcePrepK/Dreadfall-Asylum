package toolbox;

import java.util.ArrayList;

public class VariableWatcher<T> {
    private T value;
    private final ArrayList<CustomRunnable<T>> onChanges = new ArrayList<>();

    public VariableWatcher(final T value, final CustomRunnable<T> onChange) {
        this.value = value;
        onChanges.add(onChange);
    }

    public VariableWatcher(final T value) {
        this.value = value;
    }

    public VariableWatcher(final CustomRunnable<T> onChange) {
        onChanges.add(onChange);
    }

    public void addOnChange(final CustomRunnable<T> onChange) {
        onChanges.add(onChange);
    }

    public T get() {
        return value;
    }

    public void set(final T value) {
        if (this.value.equals(value)) return;
        this.value = value;
        for (final CustomRunnable<T> onChange : onChanges) onChange.run(value);
    }
}
