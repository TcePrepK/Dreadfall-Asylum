package game.utils;

import game.rooms.BaseRoom;
import toolbox.Vector2DI;

public class Map {
    private final int size;

    private final BaseRoom[] rooms;
    private final Vector2DI startPosition;

    public Map(final int size) {
        this.size = size;

        rooms = new BaseRoom[size * size];
        // Right center!
        startPosition = new Vector2DI(size - 1, size / 2);
    }

    private void generateWorld() {
        int x = startPosition.x;
        int y = startPosition.y;
        generateRoom(x, y);


    }

    private BaseRoom generateRoom(int x, int y) {
        BaseRoom room = new BaseRoom(x, y);
        setRoom(x, y, room);
        return room;
    }

    public BaseRoom getRoom(final int x, final int y) {
        return rooms[x + y * size];
    }

    public void setRoom(final int x, final int y, final BaseRoom room) {
        rooms[x + y * size] = room;
    }
}
