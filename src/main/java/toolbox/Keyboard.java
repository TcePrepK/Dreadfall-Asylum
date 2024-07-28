package toolbox;

import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class Keyboard {
    public static final List<String> pressedList = new ArrayList<>();

    public static final Signal keyPressed = new Signal();

    public static final String LSHIFT = "42";
    public static final String SPACE = "57";
    public static final String ESCAPE = "1";

    public static void initializeForWindow(final long windowID) {
        GLFW.glfwSetKeyCallback(windowID, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                GLFW.glfwSetWindowShouldClose(window, true);
            }

            String keyName = GLFW.glfwGetKeyName(key, scancode);
            if (keyName == null) {
                keyName = Integer.toString(scancode);
            }

            if (action == GLFW.GLFW_PRESS) {
                Keyboard.pressedList.add(keyName);
            } else if (action == GLFW.GLFW_RELEASE) {
                Keyboard.pressedList.remove(keyName);
            }

            Keyboard.keyPressed.testKeys();
        });
    }

    public static void copyTextToClipboard(final String string) {
        final StringSelection stringSelection = new StringSelection(string);
        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    public static boolean isKeyDown(final String key) {
        return Keyboard.pressedList.contains(key.toLowerCase());
    }
}