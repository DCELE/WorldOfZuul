package worldofzuul;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> inventory;

    public Inventory() {
        this.inventory = new ArrayList<Item>();
    }

    public ArrayList<Item> getArrayList() {
        return this.inventory;
    }

    @Override
    public String toString() {
        if (this.inventory.size() == 0){
            return "There are no items in this room";
        }
        return "Items in this room " + this.inventory;
    }

    public void addToInventory(Item item) {
        this.inventory.add(item);
    }

    public void removeFromInventory(Item item) {
        this.inventory.remove(item);
    }

    public boolean contains(String name) {
        for (Item item : this.inventory) {
            if (item.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public Item getItemFromInventory(Item item) {
        return null;
    }
}