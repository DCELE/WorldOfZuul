package worldofzuul;

public class Bucket extends Item {
    private boolean hasWater;
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
