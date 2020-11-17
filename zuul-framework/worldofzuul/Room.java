package worldofzuul;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;


public class Room {
    private String description;
    private HashMap<Room, Room[]> exits;
    private Inventory inventory;
    private String name;
    private int waterNeededInMachine;
    private static ArrayList<Room> allRooms = new ArrayList<>();

    public Room(String name, String description, Inventory inventory) {
        this.name = name;
        this.description = description;
        exits = new HashMap<Room, Room[]>();
        this.inventory = inventory;
        allRooms.add(this);
    }

    public Room(String name, String description, Inventory inventory, int waterNeededInMachine) {
        this.name = name;
        this.description = description;
        exits = new HashMap<Room, Room[]>();
        this.inventory = inventory;
        this.waterNeededInMachine = waterNeededInMachine;
        allRooms.add(this);
    }

    public void setExit(Room direction, Room[] neighbor) {
        exits.put(direction, neighbor);
    }

    public String getShortDescription() {
        return description;
    }

    public String getLongDescription() {
        return "You are " + description + ".\n" + inventory.toString() + ".\n" + getExitString();
    }

    private String getExitString() {
        String returnString = "Exits:";
        Set<Room> keys = exits.keySet();
        for (Room exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    public Room[] getExits() {
        return exits.get(this);
    }

    public Room getExitIndex(int n) {
        return exits.get(this)[n];
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public String getName() {
        return name;
    }

    public static ArrayList<Room> getAllRooms() {
        return allRooms;
    }
}

