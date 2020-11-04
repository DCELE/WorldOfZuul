package worldofzuul;

import java.util.ArrayList;

public class Materials extends Item {
    // State is either seed, plant, fabric or t-shirt.
    private int state;
    private int waterAmountNeeded;
    private boolean planted;
    // The rooms in order, where you can use (interact with) the item
    private Room[] roomsToUseItem;
    private String color;

    public Materials(String name, int id)
    {
        super(name, id);
        this.state = 0;
    }

    public Materials(String name, int id, Room[] roomsToUseItem, int waterAmountNeeded) {
        super(name, id);
        this.state = 0;
        this.roomsToUseItem = roomsToUseItem;
        this.planted = false;
        this.waterAmountNeeded = waterAmountNeeded;
        this.color = "natural";
    }

    public Materials(String name, int id, Room[] roomsToUseItem){
        super(name, id);
        this.state = 3;
        this.roomsToUseItem = roomsToUseItem;
    }

    public void upgradeState()
    {
        this.state += 1;
    }

    public int getState()
    {
        return state;
    }

    public Room[] getRoomsToUseItem()
    {
        return this.roomsToUseItem;
    }

    public boolean isPlanted()
    {
        return planted;
    }

    public void setPlanted() {
        this.planted = !planted;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getWaterAmountNeeded() {
        return waterAmountNeeded;
    }

    public void setWaterAmountNeeded(int waterAmountNeeded) {
        this.waterAmountNeeded = waterAmountNeeded;
    }

    public void decrementWaterAmountNeeded() {
        this.waterAmountNeeded -= 1;
    }
}
