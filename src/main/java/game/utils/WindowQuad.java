package game.utils;

import core.Loader;
import core.RawModel;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class WindowQuad {
    public final RawModel windowQuad = Loader.loadToVAO(new float[]{-1, -1, -1, 1, 1, -1, 1, 1}, 2);

    public void render() {
        glBindVertexArray(windowQuad.getVaoID());
        glEnableVertexAttribArray(0);
        glDrawArrays(GL_TRIANGLE_STRIP, 0, windowQuad.getVertexCount());
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }
}
