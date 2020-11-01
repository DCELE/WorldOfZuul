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

        /*

        String returnString = "Choose a path:";
        Set<String> keys = exits.keySet();
        for(String exit : keys){
            returnString += " " + exit;
        }
        return returnString;

         */
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

    // Set particular item in the room
    public void setItem(Item newItem)
    {
        items.add(newItem);
    }

    // Get description of the item in the room
    public String getRoomItems()
    {
        String output =  "";
        for (int i = 0; i < items.size(); i++)
        {
            output += items.get(i).getDescription() + "";
        }
        return output;
    }
}

