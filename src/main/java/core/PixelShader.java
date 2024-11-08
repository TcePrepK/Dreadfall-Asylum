package core;

import static org.lwjgl.opengl.GL20.*;

public class PixelShader extends BaseShader {
    protected String vertexFile;
    protected String fragmentFile;

    private int vertexShader;
    private int fragmentShader;

    public PixelShader(String shaderName, String vertexFile, String fragmentFile) {
        this.vertexFile = vertexFile;
        this.fragmentFile = fragmentFile;

        System.out.println("----------------------------------------------------");
        System.out.println("Initializing Pixel-Shader (" + shaderName + ")");
        initialize();
        System.out.println("✅ Initialization Complete ✅");
        System.out.println("----------------------------------------------------");
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
