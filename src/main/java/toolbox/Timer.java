package toolbox;

public class Timer {
    private long startTime;

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public double stop() {
        final long endTime = System.currentTimeMillis();
        final long elapsedTime = endTime - startTime;
        return elapsedTime / 1000.0; // Convert milliseconds to seconds
    }
}
