package core;

import static org.lwjgl.opengl.GL20.*;

public class PixelShader extends BaseShader {
    protected String vertexFile;
    protected String fragmentFile;

    private int vertexShader;
    private int fragmentShader;

    public PixelShader(String vertexFile, String fragmentFile) {
        this.vertexFile = vertexFile;
        this.fragmentFile = fragmentFile;
        initialize();
    }

    @Override
    protected void readShaderFiles() {
        vertexShader = loadShaderFromFile(GL_VERTEX_SHADER, this.vertexFile);
        fragmentShader = loadShaderFromFile(GL_FRAGMENT_SHADER, this.fragmentFile);
    }

    @Override
    protected void attachShaders() {
        glAttachShader(programID, vertexShader);
        glAttachShader(programID, fragmentShader);
    }

    @Override
    public void cleanUp() {
        stop();

        glDetachShader(programID, vertexShader);
        glDetachShader(programID, fragmentShader);
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);

        glDeleteProgram(programID);
    }
}
