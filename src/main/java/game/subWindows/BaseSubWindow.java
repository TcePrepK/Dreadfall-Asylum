package game.subWindows;

import core.GameRoot;
import core.WindowQuad;
import game.SubWindowManager;
import toolbox.Vector2D;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import toolbox.Keyboard;
import toolbox.VariableWatcher;

import static org.lwjgl.opengl.GL11.glViewport;

public abstract class BaseSubWindow {
    public long windowID;
    protected Vector2D leftTop;
    protected VariableWatcher<Vector2D> size;
    protected WindowQuad windowQuad;
    protected boolean isRunning = true;

    protected GLCapabilities capabilities;

    public BaseSubWindow(final String title, final Vector2D center, final Vector2D size) {
        leftTop = center.sub(size.div(2));
        this.size = new VariableWatcher<>(size);
    }

    public BaseSubWindow initialize(final GameRoot root) {
        return this;
    }

    public abstract void frameUpdate(final GameRoot root);

    //////////////////////////////////////////////////////////////////////////////

    protected void moveWindow(final Vector2D center) {
        final Vector2D res = size.get();
        leftTop = center.sub(res.div(2));
        GLFW.glfwSetWindowPos(windowID, (int) leftTop.x, (int) leftTop.y);
    }

    protected void resizeWindow(final Vector2D size) {
        this.size.set(size);
        moveWindow(leftTop.add(size.div(2)));
        GLFW.glfwSetWindowSize(windowID, (int) size.x, (int) size.y);

        GLFW.glfwMakeContextCurrent(windowID);
        glViewport(0, 0, (int) size.x, (int) size.y);
    }

    public Vector2D getLeftTop() {
        return leftTop;
    }

    public Vector2D getLeftBottom() {
        final Vector2D res = size.get();
        return new Vector2D(leftTop.x, leftTop.y + res.y);
    }

    public void closeWindow() {
        SubWindowManager.windows.remove(this);
        GLFW.glfwDestroyWindow(windowID);
    }

    public boolean shouldClose() {
        return isRunning;
    }
}
