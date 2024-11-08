package game;

import core.GameRoot;
import core.Screen;
import core.WindowQuad;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import toolbox.Keyboard;
import toolbox.Vector2DI;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;

public class MainWindow {
    public static long windowID;
    private static WindowQuad windowQuad;
    private static boolean isRunning = true;

    private static MainWindowShader mainShader;

    public static void initialize(GameRoot root) {
        GLFW.glfwInit();

        { // Setup window
            GLFW.glfwWindowHint(GLFW.GLFW_TRANSPARENT_FRAMEBUFFER, GLFW.GLFW_TRUE);
            GLFW.glfwWindowHint(GLFW.GLFW_DECORATED, GLFW.GLFW_FALSE);
            GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
            GLFW.glfwWindowHint(GLFW.GLFW_SOFT_FULLSCREEN, GLFW.GLFW_TRUE);

            windowID = GLFW.glfwCreateWindow(Screen.WIDTH, Screen.HEIGHT, "Window 1", GLFW.glfwGetPrimaryMonitor(), 0);
            if (windowID == 0) {
                throw new RuntimeException("Failed to create the GLFW window");
            }

            System.out.println("Window created successfully");

            GLFW.glfwMakeContextCurrent(windowID);
            GLFW.glfwShowWindow(windowID);
            GL.createCapabilities();
        }

        { // Get ready to render
            Keyboard.initializeForWindow(windowID);

            windowQuad = new WindowQuad();

            // Shaders
            mainShader = new MainWindowShader();
            mainShader.initializeAttachments(root);
            mainShader.loadResolution(Screen.SIZE);
        }
    }

    public static void frameUpdate(final GameRoot root) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(0.0f, 0.0f, 0.0f, 1f);

        mainShader.start();
        {
            windowQuad.render();
        }
        mainShader.stop();

//        windowShader.start();
//        {
//            glActiveTexture(GL_TEXTURE0);
//            glBindTexture(GL_TEXTURE_2D, attachmentBuffer.manager.get("lighting").getOldID());
//            windowQuad.render();
//        }
//        windowShader.stop();
//
//        lightingShader.start();
//        attachmentBuffer.start();
//        {
//            lightingShader.loadPlayer();
//            lightingShader.loadWindowLeftBottom(getLeftBottom());
//            glActiveTexture(GL_TEXTURE0);
//            glBindTexture(GL_TEXTURE_2D, attachmentBuffer.manager.get("lighting").getOldID());
//            windowQuad.render();
//        }
//        attachmentBuffer.stop();
//        lightingShader.stop();

        glfwSwapBuffers(windowID);
        glfwPollEvents();
    }

    public static void closeWindow() {
        GLFW.glfwDestroyWindow(windowID);
    }

    public static boolean shouldClose() {
        return !isRunning || GLFW.glfwWindowShouldClose(windowID);
    }
}
