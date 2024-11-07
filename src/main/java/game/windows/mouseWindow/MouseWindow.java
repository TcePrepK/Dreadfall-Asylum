package game.windows.mouseWindow;

import core.AttachmentBuffer;
import core.GameRoot;
import game.LightingShader;
import game.windows.BaseWindow;
import game.windows.Screen;
import toolbox.Vector2D;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import toolbox.Keyboard;
import toolbox.Mouse;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.GL_R8;

public class MouseWindow extends BaseWindow {
    private final MouseWindowShader windowShader;
    private static LightingShader lightingShader;
    private static AttachmentBuffer attachmentBuffer;

    public MouseWindow() {
        super("Mouse Window", Screen.CENTER, new Vector2D(50, 50));

        windowShader = new MouseWindowShader();
        lightingShader = new LightingShader();

        final Vector2D res = size.get();
        attachmentBuffer = new AttachmentBuffer((int) res.x, (int) res.y);
        attachmentBuffer.addAttachment("lighting", 0, GL_R8, GL_RED, GL_UNSIGNED_BYTE);
        attachmentBuffer.initialize();

        Keyboard.keyPressed.add(this::closeWindow, Keyboard.ESCAPE);

        { // Attachments
            windowShader.attachWatcher("resolution", size);
            lightingShader.attachPlayerLight();
        }
    }

    @Override
    public void frameUpdate(final GameRoot root) {
        final Vector2D mousePos = Mouse.getMousePos();
        moveWindow(mousePos);

        GLFW.glfwMakeContextCurrent(windowID);
        GL.setCapabilities(capabilities);

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        lightingShader.start();
        attachmentBuffer.start();
        {
            lightingShader.loadPlayer();
            lightingShader.loadWindowLeftBottom(getLeftBottom());
            windowQuad.render();
        }
        attachmentBuffer.stop();
        lightingShader.stop();

        windowShader.start();
        {
//            windowShader.loadResolution(size);
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, attachmentBuffer.manager.get("lighting").getOldID());
            windowQuad.render();
        }
        windowShader.stop();
    }

    @Override
    public boolean shouldClose() {
        return !isRunning;
    }
}