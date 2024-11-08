package game;

import core.GameRoot;
import core.Loader;
import org.lwjgl.glfw.GLFW;
import core.ShaderManager;
import toolbox.Timer;

public class Simulation {
    private static final GameRoot root = new GameRoot();

    private static final Timer fpsTimer = new Timer();

    public static void run() {
        MainWindow.initialize(root);

        while (!MainWindow.shouldClose()) {
            frameUpdate();
        }
    }

    private static void frameUpdate() {
        fpsTimer.start();

        MainWindow.frameUpdate(root);

        final double deltaTime = fpsTimer.stop();
        root.deltaTime = deltaTime;
        root.fps = 1 / deltaTime;
        final double timeSinceStart = root.time.get();
        root.time.set(timeSinceStart + deltaTime);
    }

    public static void cleanUp() {
        Loader.cleanUp();
        ShaderManager.cleanUp();
        MainWindow.closeWindow();
        GLFW.glfwTerminate();
    }
}
