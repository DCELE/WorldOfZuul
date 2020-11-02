package worldofzuul;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> inventory;

    public Inventory() {
        this.inventory = new ArrayList<Item>();
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    @Override
    public String toString() {
        if (inventory.size() == 0){
            return "There are no items in this room";
        }
        return "Items in this room " + inventory;
    }

    public void addToInventory(Item item) {
        inventory.add(item);
    }

    public void removeFromInventory(Item item) {
        inventory.remove(item);
    }
}
