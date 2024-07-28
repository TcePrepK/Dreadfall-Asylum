package core;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL46.*;

public class Loader {
    private static final List<Integer> vaos = new ArrayList<>();
    private static final List<Integer> vbos = new ArrayList<>();
    private static final List<Integer> textures = new ArrayList<>();

    public static RawModel loadToVAO(final float[] positions, final int dimensions) {
        final int vaoID = createVAO();

        storeDataInAttributeList(0, dimensions, positions);

        Loader.unbindVAO();
        return new RawModel(vaoID, -1, positions.length / dimensions);
    }

    public static RawModel loadToVAO(final float[] positions, final int[] indices) {
        final int vaoID = createVAO();
        final int vboID = bindIndicesBuffer(indices);

        storeDataInAttributeList(0, 3, positions);

        Loader.unbindVAO();
        return new RawModel(vaoID, vboID, indices.length);
    }

    public static RawModel loadToVAO(final float[] positions, final byte[] colors, final int[] indices) {
        final int vaoID = createVAO();
        final int vboID = bindIndicesBuffer(indices);

        storeDataInAttributeList(0, 3, positions);
        storeDataInAttributeList(1, 4, colors);

        Loader.unbindVAO();
        return new RawModel(vaoID, vboID, indices.length);
    }

    public static void cleanUp() {
        for (final int vao : vaos) {
            glDeleteVertexArrays(vao);
        }

        for (final int vbo : vbos) {
            glDeleteBuffers(vbo);
        }

        for (final int texture : textures) {
            glDeleteTextures(texture);
        }
    }

    public static void cleanModel(final RawModel model) {
        glDeleteVertexArrays(model.getVaoID());

        if (model.getVboID() >= 0) {
            glDeleteBuffers(model.getVboID());
        }
    }

    private static int createVAO() {
        final int vaoID = glGenVertexArrays();

        vaos.add(vaoID);

        glBindVertexArray(vaoID);

        return vaoID;
    }

    private static void storeDataInAttributeList(final int attributeNumber, final int coordinateSize, final float[] data) {
        final int vboID = glGenBuffers();
        final FloatBuffer buffer = core.Loader.storeDataInFloatBuffer(data);

        vbos.add(vboID);

        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(attributeNumber, coordinateSize, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private static void storeDataInAttributeList(final int attributeNumber, final int coordinateSize, final byte[] data) {
        final int vboID = glGenBuffers();
        final ByteBuffer buffer = core.Loader.storeDataInByteBuffer(data);

        vbos.add(vboID);

        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(attributeNumber, coordinateSize, GL_BYTE, true, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private static void unbindVAO() {
        glBindVertexArray(0);
    }

    private static int bindIndicesBuffer(final int[] indices) {
        final int vboID = glGenBuffers();
        vbos.add(vboID);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboID);
        final IntBuffer buffer = core.Loader.storeDataInIntBuffer(indices);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

        return vboID;
    }

    private static ByteBuffer storeDataInByteBuffer(final byte[] data) {
        final ByteBuffer buffer = BufferUtils.createByteBuffer(data.length);
        buffer.put(data);
        buffer.flip();

        return buffer;
    }

    private static IntBuffer storeDataInIntBuffer(final int[] data) {
        final IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();

        return buffer;
    }

    private static FloatBuffer storeDataInFloatBuffer(final float[] data) {
        final FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);

        buffer.put(data);
        buffer.flip();

        return buffer;
    }

}
