package toolbox;

public class Vector2D {
    public float x;
    public float y;

    public Vector2D(final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D() {
        this(0, 0);
    }

    public Vector2D add(final Vector2D v) {
        return new Vector2D(x + v.x, y + v.y);
    }

    public Vector2D add(final float n) {
        return new Vector2D(x + n, y + n);
    }

    public Vector2D sub(final Vector2D v) {
        return new Vector2D(x - v.x, y - v.y);
    }

    public Vector2D sub(final float n) {
        return new Vector2D(x - n, y - n);
    }

    public Vector2D mult(final float scalar) {
        return new Vector2D(x * scalar, y * scalar);
    }

    public Vector2D div(final float scalar) {
        if (scalar == 0) {
            return new Vector2D(x, y);
        }
        return new Vector2D(x / scalar, y / scalar);
    }

    public float dot(final Vector2D v) {
        return x * v.x + y * v.y;
    }

    public float length() {
        return (float) Math.sqrt(dot(this));
    }

    public Vector2D normalize() {
        return div(length());
    }

    public Vector2DI toInt() {
        return new Vector2DI((int) x, (int) y);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final Vector2D vector2D = (Vector2D) obj;
        return Float.compare(vector2D.x, x) == 0 && Float.compare(vector2D.y, y) == 0;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}