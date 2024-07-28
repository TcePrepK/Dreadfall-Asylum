package core;

import game.WindowManager;
import org.lwjgl.glfw.GLFW;
import toolbox.ShaderManager;
import toolbox.Timer;

public class MainSimulation {
    private static final GameRoot root = new GameRoot();

    private static final Timer fpsTimer = new Timer();

    public static void run() {
        // Initialize GLFW and create windows/screens
        GLFW.glfwInit();

        WindowManager.initialize(root);
        while (!WindowManager.shouldClose()) {
            frameUpdate();
        }
    }

    private static void frameUpdate() {
        fpsTimer.start();

        WindowManager.frameUpdate(root);

        final double deltaTime = fpsTimer.stop();
        root.deltaTime = deltaTime;
        root.fps = 1 / deltaTime;
        final double timeSinceStart = root.time.get();
        root.time.set(timeSinceStart + deltaTime);
    }

    public static void cleanUp() {
        Loader.cleanUp();
        ShaderManager.cleanUp();
        WindowManager.cleanUp();
        GLFW.glfwTerminate();
    }
}
