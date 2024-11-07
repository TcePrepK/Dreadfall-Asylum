package game.subWindows.mainWindow;

import core.AttachmentBuffer;
import core.GameRoot;
import game.LightingShader;
import game.subWindows.BaseSubWindow;
import game.subWindows.Screen;
import toolbox.Vector2D;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import toolbox.Keyboard;

import static org.lwjgl.opengl.GL30.*;

public class MainSubWindow extends BaseSubWindow {
    private static MainWindowShader windowShader;
    private static LightingShader lightingShader;

    private static AttachmentBuffer attachmentBuffer;

    public MainSubWindow() {
        super("Window 1", new Vector2D((float) Screen.WIDTH / 2, (float) Screen.HEIGHT / 2), new Vector2D(Screen.WIDTH, Screen.HEIGHT).div(1));
    }

    @Override
    public MainSubWindow initialize(final GameRoot root) {
        windowShader = new MainWindowShader();
        lightingShader = new LightingShader();

        final Vector2D res = size.get();
        attachmentBuffer = new AttachmentBuffer((int) res.x, (int) res.y);
        attachmentBuffer.addAttachment("lighting", 0, GL_R8, GL_RED, GL_UNSIGNED_BYTE);
        attachmentBuffer.initialize();

        Keyboard.keyPressed.add(this::closeWindow, Keyboard.ESCAPE);

        windowShader.attachWatcher("resolution", size);
        windowShader.attachWatcher("time", root.time);

        lightingShader.attachWatcher("resolution", size);

        return this;
    }

    @Override
    public void frameUpdate(final GameRoot root) {
//        final double wobble = 50 * Math.sin(10 * params.timeSinceStart);
//        moveWindow(Screen.CENTER.sub(new Vector2D((float) wobble, 0)));

        GLFW.glfwMakeContextCurrent(windowID);
        GL.setCapabilities(capabilities);

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        windowShader.start();
        {
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, attachmentBuffer.manager.get("lighting").getOldID());
            windowQuad.render();
        }
        windowShader.stop();

        lightingShader.start();
        attachmentBuffer.start();
        {
            lightingShader.loadPlayer();
            lightingShader.loadWindowLeftBottom(getLeftBottom());
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, attachmentBuffer.manager.get("lighting").getOldID());
            windowQuad.render();
        }
        attachmentBuffer.stop();
        lightingShader.stop();
    }

    @Override
    public boolean shouldClose() {
        return !isRunning;
    }
}
