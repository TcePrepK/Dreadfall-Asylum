import game.Simulation;
import toolbox.CustomError;

public class Main {
    public static void main(final String[] args) {
        try {
            Simulation.run();
        } catch (final Throwable e) {
            if (e instanceof CustomError) return;
            System.err.println(e.getMessage());
        }
        Simulation.cleanUp();
    }
}