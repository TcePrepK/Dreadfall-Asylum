package toolbox;

public class Vector2DI {
    public int x;
    public int y;

    public Vector2DI(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2DI() {
        this(0, 0);
    }

    public Vector2DI add(final Vector2DI v) {
        return new Vector2DI(x + v.x, y + v.y);
    }

    public Vector2DI add(final int n) {
        return new Vector2DI(x + n, y + n);
    }

    public Vector2DI sub(final Vector2DI v) {
        return new Vector2DI(x - v.x, y - v.y);
    }

    public Vector2DI sub(final int n) {
        return new Vector2DI(x - n, y - n);
    }

    public Vector2DI mult(final int scalar) {
        return new Vector2DI(x * scalar, y * scalar);
    }

    public Vector2DI div(final int scalar) {
        if (scalar == 0) {
            return new Vector2DI(x, y);
        }
        return new Vector2DI(x / scalar, y / scalar);
    }

    public float dot(final Vector2DI v) {
        return x * v.x + y * v.y;
    }

    public float length() {
        return (float) Math.sqrt(dot(this));
    }

    public Vector2D normalize() {
        return this.toFloat().div(length());
    }

    public Vector2D toFloat() {
        return new Vector2D(x, y);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final Vector2DI vector2D = (Vector2DI) obj;
        return Float.compare(vector2D.x, x) == 0 && Float.compare(vector2D.y, y) == 0;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
