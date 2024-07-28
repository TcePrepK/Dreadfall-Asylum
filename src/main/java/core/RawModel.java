package core;

public class RawModel {
    private final int vaoID;
    private final int vboID;
    private final int vertexCount;

    public RawModel(final int vaoID, final int vboID, final int vertexCount) {
        this.vaoID = vaoID;
        this.vboID = vboID;
        this.vertexCount = vertexCount;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVboID() {
        return vboID;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void cleanUp() {
        Loader.cleanModel(this);
    }
}
