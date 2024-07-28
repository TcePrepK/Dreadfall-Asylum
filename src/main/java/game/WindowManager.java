package game;

import core.GameRoot;
import game.windows.BaseWindow;
import game.windows.mainWindow.MainWindow;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;

public class WindowManager {
    public static final ArrayList<BaseWindow> windows = new ArrayList<>();

    public static void initialize(final GameRoot root) {
        windows.add(new MainWindow().initialize(root));
//        windows.add(new MouseWindow().initialize());
    }

    public static void frameUpdate(final GameRoot root) {
        for (final BaseWindow window : windows) {
            window.frameUpdate(root);
            glfwSwapBuffers(window.windowID);
        }
        glfwPollEvents();
    }

    public static void cleanUp() {
        for (final BaseWindow window : windows) {
            window.closeWindow();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////

    public static boolean shouldClose() {
        return windows.stream().allMatch(BaseWindow::shouldClose);
    }
}
