package game;

import core.GameRoot;
import game.subWindows.BaseSubWindow;
import game.subWindows.mainWindow.MainSubWindow;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;

public class SubWindowManager {
    public static final ArrayList<BaseSubWindow> windows = new ArrayList<>();

    public static void initialize(final GameRoot root) {
        windows.add(new MainSubWindow().initialize(root));
//        windows.add(new MouseWindow().initialize());
    }

    public static void frameUpdate(final GameRoot root) {
        for (final BaseSubWindow window : windows) {
            window.frameUpdate(root);
            glfwSwapBuffers(window.windowID);
        }
        glfwPollEvents();
    }

    public static void cleanUp() {
        for (final BaseSubWindow window : windows) {
            window.closeWindow();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////

    public static boolean shouldClose() {
        return windows.stream().allMatch(BaseSubWindow::shouldClose);
    }
}
