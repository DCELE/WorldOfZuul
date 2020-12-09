package worldofzuul;

public class Bucket extends Item {
    private boolean hasWater;
    private Room[] roomsToUse;

    public Bucket(String name, int id, Room[] roomsToUseBucket, String itemIcon) {
        super(name, id, itemIcon);
        hasWater = false;
        this.roomsToUse = roomsToUseBucket;
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

    public Room[] getRoomsToUse() {
        return roomsToUse;
    }
}
