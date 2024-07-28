package core;

import toolbox.AttachmentManager;

import static org.lwjgl.opengl.GL20.glDrawBuffers;
import static org.lwjgl.opengl.GL30.*;

public class AttachmentBuffer {
    private final int width, height;
    public final AttachmentManager manager;

    private int frameBufferID;

    public AttachmentBuffer(final int width, final int height) {
        this.width = width;
        this.height = height;
        manager = new AttachmentManager(width, height);
    }

    public void addAttachment(final String id, final int offset, final int internalFormat, final int format, final int dataType) {
        manager.add(id, offset, internalFormat, format, dataType);
    }

    public void initialize() {
        frameBufferID = createFrameBuffer();
    }

    public void start() {
        glBindFramebuffer(GL_FRAMEBUFFER, frameBufferID);
        glViewport(0, 0, width, height);

        manager.createAttachments();
        manager.bind();
    }

    public void stop() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glViewport(0, 0, width, height);

        manager.update();
    }

    private int createFrameBuffer() {
        final int frameBuffer = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, frameBuffer);

        final int size = manager.size();
        final int[] attachments = new int[size];
        for (int i = 0; i < size; i++) {
            attachments[i] = GL_COLOR_ATTACHMENT0 + i;
        }

        glDrawBuffers(attachments);
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glViewport(0, 0, width, height);

        return frameBuffer;
    }
}
