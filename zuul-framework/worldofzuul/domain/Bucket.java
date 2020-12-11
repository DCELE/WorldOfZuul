package worldofzuul.domain;

public class Bucket extends Item {
    // A bucket can have water or not
    private boolean hasWater;
    // A bucket is usable in certain rooms
    private Room[] roomsToUse;

    public Bucket(String name, int id, Room[] roomsToUseBucket, String itemIcon, String description) {
        super(name, id, itemIcon, description);
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
            super.setItemIcon("worldofzuul/WorldOfZuulPNG/Items/BucketWithWater.png");
        } else {
            this.hasWater = false;
            setName((getName().split(" "))[0]);
            super.setItemIcon("worldofzuul/WorldOfZuulPNG/Items/Bucket.png");
        }
    }

    public Room[] getRoomsToUse() {
        return roomsToUse;
    }
}
