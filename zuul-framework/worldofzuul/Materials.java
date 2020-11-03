package worldofzuul;

import java.util.ArrayList;

public class Materials extends Item {
    // State is either seed, plant, fabric or t-shirt.
    private int state;
    private boolean planted;
    // The rooms in order, where you can use (interact with) the item
    private Room[] roomsToUseItem;

    public Materials(String name, int id)
    {
        super(name, id);
        this.state = 1;
    }

    public Materials(String name, int id, Room[] roomsToUseItem) {
        super(name, id);
        this.state = 1;
        this.roomsToUseItem = roomsToUseItem;
        this.planted = false;
    }

    public void upgradeState() {
        this.state += 1;
    }

    public int getState() {
        return state;
    }

    public Room[] getRoomsToUseItem() {
        return this.roomsToUseItem;
    }

    public boolean isPlanted() {
        return planted;
    }

    public void setPlanted() {
        this.planted = !planted;
    }
}
