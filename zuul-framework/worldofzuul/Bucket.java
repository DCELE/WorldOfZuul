package worldofzuul;

import java.util.HashMap;

public class Bucket extends Item {
    private boolean hasWater;
    private Room[] roomsToUseBucket;

    public Bucket(String name, int id, Room[] roomsToUseBucket) {
        super(name, id);
        hasWater = false;
        this.roomsToUseBucket = roomsToUseBucket;
    }

    public boolean hasWater()
    {
        return hasWater;
    }

    public void setHasWater() {
        if (!hasWater) {
            this.hasWater = true;
            setName((getName() + " with water"));
        } else {
            this.hasWater = false;
            setName((getName().split(" "))[0]);
        }
    }

    public Room[] getRoomsToUseBucket() {
        return roomsToUseBucket;
    }
}
