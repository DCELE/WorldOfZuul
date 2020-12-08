package worldofzuul;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;


public class Room {
    private String description;
    private HashMap<Room, Room> exits;
    private Inventory inventory;
    private String name;
    private int waterAmountInMachine;
    private static ArrayList<Room> allRooms = new ArrayList<>();
    private String backgroundImage;

    public Room(String name, String description, Inventory inventory) {
        this.name = name;
        this.description = description;
        exits = new HashMap<Room, Room>();
        this.inventory = inventory;
        allRooms.add(this);
    }

    public Room(String name, String description, Inventory inventory, String url) {
        this.name = name;
        this.description = description;
        exits = new HashMap<Room, Room>();
        this.inventory = inventory;
        allRooms.add(this);
        this.backgroundImage = url;
    }

    public void setExit(Room neighbor) {
        exits.put(neighbor, this);
    }

    public String getShortDescription() {
        return description;
    }

    public String getLongDescription() {
        String textBox = "You are " + description + ".\n";

        if (Game.getGameGuides() != null) {
            textBox += Game.getGameGuides() + ".\n";
        }
        if (Player.getPlayerThinks() != null) {
            textBox += Player.getPlayerThinks();
        }
        return textBox;
    }

    private String getExitString() {
        String returnString = "Exits:";
        Set<Room> keys = exits.keySet();
        for (Room exit : keys) {
            returnString += " " + exit.getName();
        }
        return returnString;
    }

    public ArrayList<Room> getExits() {
        Set<Room> keys = exits.keySet();
        return new ArrayList<>(keys);
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

    public static Room getRoom(String roomName) {
        Room currentRoom = null;
        for (Room room : Room.getAllRooms()) {
            if (!room.getName().equals(roomName)) {
                continue;
            }
            currentRoom = room;
            break;
        }
        return currentRoom;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }
}

