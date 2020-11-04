package worldofzuul;

import java.util.HashMap;
import java.util.Set;


public class Room 
{
    private String description;
    private HashMap<String, Room> exits;
    private Inventory inventory;

    public Room(String description, Inventory inventory)
    {
        this.description = description;
        exits = new HashMap<String, Room>();
        this.inventory = inventory;
    }

//    public Room(String description, Inventory inventory, FabricMachines processMachine, FabricMachines sewingMachine)
//    {
//        this.description = description;
//        exits = new HashMap<String, Room>();
//        this.inventory = inventory;
//    }

    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }

    public String getShortDescription()
    {
        return description;
    }

    public String getLongDescription()
    {
        return "You are " + description + ".\n"+ inventory.toString() + ".\n" + getExitString();
    }

    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }

    public Inventory getInventory()
    {
        return this.inventory;
    }
}

