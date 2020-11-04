package worldofzuul;

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
        this.hasWater = !hasWater;
    }

    public Room[] getRoomsToUseBucket() {
        return roomsToUseBucket;
    }

}
