package worldofzuul;

import com.sun.prism.Material;

import java.util.ArrayList;

public class Materials extends Item {
    // State is either seed, plant, fabric or t-shirt.
    private int state;
    private int[] waterAmountNeeded;
    private int[] chemicalsAmountNeeded;
    private boolean planted;
    private boolean inProcess;
    // The rooms in order, where you can use (interact with) the item
    private Room[] roomsToUseItem;
    private String color;
    private String[] stateNames;
    private static ArrayList<Materials> allMaterials = new ArrayList<>();
    private static int points;

    public Materials(String name, int id)
    {
        super(name, id);
        this.state = 0;
        allMaterials.add(this);
    }

    public Materials(String name, int id, Room[] roomsToUseItem, int[] waterAmountNeeded, int[] chemicalsAmountNeeded,int points) {
        super(name, id);
        this.state = 0;
        this.roomsToUseItem = roomsToUseItem;
        this.planted = false;
        this.waterAmountNeeded = waterAmountNeeded;
        this.chemicalsAmountNeeded = chemicalsAmountNeeded;
        this.color = "natural";
        this.points = points;
        allMaterials.add(this);
        stateNames = new String[]{" seed", " plant", " fabric", " " + getColor() + " fabric", " " + getColor() + " t-shirt"};
        if (super.getId() == 5)
        {
            this.state = 1;
            stateNames = new String[]{" chemicals", " fabric", " " + getColor() + " fabric", " " + getColor() + " t-shirt"};
            setNameForState();
            return;
        }
        setNameForState();
    }

    public Materials(String name, int id, Room[] roomsToUseItem){
        super(name, id);
        this.state = 1;
        this.roomsToUseItem = roomsToUseItem;
        allMaterials.add(this);
    }
    /* getPoints for material. Didnt work bc materials change name throughout the game
    public int getPoints(){
        return points;
    }*/


    public void upgradeState()
    {
        this.state += 1;
        setNameForState();
    }

    public void setNameForState() {
        int stateNameInt = getState();
        if (getId() == 5) {
            stateNameInt = getState()-1;
        }
        this.setName(getName().split(" ")[0] + stateNames[stateNameInt]);
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
        if (!isPlanted()) {
            this.planted = true;
            this.setName(getName() + " (planted)");
        } else {
            this.planted = false;
            this.setName(getName().split(" ")[0] + " " + getName().split(" ")[1]);
        }
    }

    public boolean isInProcess()
    {
        return inProcess;
    }

    public void setInProcess() {
        if (!isInProcess()) {
            this.inProcess = true;
            this.setName(getName() + " (in process)");
        } else {
            this.inProcess = false;
            this.setName(getName().split(" ")[0] + " " + getName().split(" ")[1]);
        }
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int[] getWaterAmountNeeded() {
        return waterAmountNeeded;
    }

    public int[] getChemicalsAmountNeeded()
    {
        return chemicalsAmountNeeded;
    }

    public void setWaterAmountNeeded(int[] waterAmountNeeded) {
        this.waterAmountNeeded = waterAmountNeeded;
    }

    public void decrementAmountNeeded(int[] whichArray, int index) {
        whichArray[index] -= 1;
    }

    public void setChemicalsAmountNeeded(int[] chemicalsAmountNeeded) { this.chemicalsAmountNeeded = chemicalsAmountNeeded;}

    public void decrementChemicalsAmountNeeded(int i) { this.chemicalsAmountNeeded[i] -= 1;}

    public static ArrayList<Materials> getAllMaterials() {
        return allMaterials;
    }

}
