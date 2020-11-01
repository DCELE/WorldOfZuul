package worldofzuul;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;


public class Room
{
    private String description;
    private HashMap<String, Room> exits;

    // New arraylist
    ArrayList<Item> items = new ArrayList<>();

    public Room(String description)
    {
        this.description = description;
        exits = new HashMap<String, Room>();
    }

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
        return "You are " + description + ".\n" + getExitString();
    }

    private String getExitString()
    {

        String returnString = "Exits:\n";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        //Ny returnString
        returnString += "\nItems in the room:\n ";
        returnString += getRoomItems();

        return returnString;
    }

    public Room getExit(String direction)
    {
        return exits.get(direction);
    }

    //Get items from the room
    public Item getItem(int index)
    {
        return items.get(index);
    }


    //Get items from the room (with String)
    public Item getItem(String itemName) {
        for (int i=0; i< items.size(); i++) {
            if (items.get(i).getDescription().equals(itemName)) {
                return items.get(i);
            }
        }
        return null;
    }

    //Remove items from the room
    public void removeItem(String itemName) {
        for (int i=0; i< items.size(); i++) {
            if (items.get(i).getDescription().equals(itemName)) {
                items.remove(i);
            }
        }
    }


    //Set particular item in the room
    public void setItem (Item newItem)
    {
        items.add(newItem);
    }

    //Get a description of the items in a room
    public String getRoomItems()
    {
        String output = "";
        for (int i=0; i<items.size(); i++)
        {
            output += items.get(i).getDescription() + " ";
        }
        return output;
    }
}

