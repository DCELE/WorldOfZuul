package worldofzuul;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> inventory;

    public Inventory() {
        this.inventory = new ArrayList<Item>();
    }

    public ArrayList<Item> getInventory() {
        return this.inventory;
    }

    @Override
    public String toString() {
        if (this.inventory.size() == 0) {
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

    public Item waterbucket(){
        Item waterbucket;
        return waterbucket = new Bucket("Waterbucket",9);
        }

    public boolean contains(String name) {
        for (Item item : this.inventory) {
            if (item.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public Item getItemFromInventory(String name) {
        for (Item item : this.inventory) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }


}
