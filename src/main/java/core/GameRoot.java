package core;

import toolbox.VariableWatcher;

public class GameRoot {
    public double deltaTime = 0;
    public double fps = 0;

    public VariableWatcher<Double> time = new VariableWatcher<>(0.0);
}
