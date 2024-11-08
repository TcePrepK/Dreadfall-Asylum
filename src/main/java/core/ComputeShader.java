package core;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL43.GL_COMPUTE_SHADER;

public class ComputeShader extends BaseShader {
    private int computeShader;

    private final String computeFile;

    public ComputeShader(final String computeFile) {
        super();
        this.computeFile = computeFile;
    }

    @Override
    protected void readShaderFiles() {
        computeShader = loadShaderFromFile(GL_COMPUTE_SHADER, this.computeFile);
    }

    @Override
    protected void attachShaders() {
        glAttachShader(programID, computeShader);
    }

    @Override
    public void cleanUp() {
        stop();

        glDetachShader(programID, computeShader);
        glDeleteProgram(programID);
    }
}
