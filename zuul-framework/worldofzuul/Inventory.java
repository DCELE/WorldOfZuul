package worldofzuul;

import java.util.ArrayList;
import java.util.List;

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

    public boolean contains(Item item) {
        for (Item it : this.inventory) {
            if (it.getName().equals(item.getName())) {
                return true;
            }
        }
        return false;
    }

    /*
    public Item getItemFromInventory(Item item) {
        for (Item it : this.inventory) {
            if (it.getName().equals(item.getName())) {
                item = it;
            }
        }
        return item;
    }
     */
}