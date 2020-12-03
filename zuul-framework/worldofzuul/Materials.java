package worldofzuul;

import com.sun.prism.Material;

import java.util.ArrayList;

public class Materials extends Item {
    // State is either seed, plant, fabric or t-shirt.
    private int state;
    private int[] waterAmountNeeded;
    private int[] chemicalsAmountNeeded;
    private ArrayList<Recipe> recipes;
    private static Recipe activeRecipe;
    private boolean planted;
    private boolean inProcess;
    // The rooms in order, where you can use (interact with) the item
    private Room[] roomsToUseItem;
    private String color;
    private String[] stateNames;
    private static ArrayList<Materials> allMaterials = new ArrayList<>();

    //, int[] waterAmountNeeded, int[] chemicalsAmountNeeded
    public Materials(String name, int id, Room[] roomsToUseItem) {
        super(name, id);
        this.state = 0;
        this.roomsToUseItem = roomsToUseItem;
        this.planted = false;
        //this.waterAmountNeeded = waterAmountNeeded;
        //this.chemicalsAmountNeeded = chemicalsAmountNeeded;
        this.color = "colored";
        allMaterials.add(this);

        stateNames = new String[]{" seed", " plant", " fabric", " " + getColor() + " fabric", " " + getColor() + " t-shirt"};
        if (super.getId() == 5) {
            this.state = 1;
            stateNames = new String[]{" chemicals", " fabric", " " + getColor() + " fabric", " " + getColor() + " t-shirt"};

        }
        this.recipes = new ArrayList<>();
        setNameForState();
    }

    public void setRecipe(Recipe recipe) {
        if (this.recipes.size() < stateNames.length) {
            recipe.setName(stateNames[this.recipes.size()], stateNames[this.recipes.size() + 1]);
            this.recipes.add(recipe);
        }
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void upgradeState() {
        setActiveRecipe(this);
        this.state += 1;
        setNameForState();
    }

    public void setNameForState() {
        int stateNameInt = getState();
        if (getId() == 5) {
            stateNameInt = getState() - 1;
        }
        this.setName(getName().split(" ")[0] + stateNames[stateNameInt]);
    }

    public String[] getStateNames() {
        return stateNames;
    }

    public int getState() {
        return state;
    }

    public Room[] getRoomsToUseItem() {
        return this.roomsToUseItem;
    }

    public boolean isPlanted() {
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

    public boolean isInProcess() {
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

    public static Recipe getActiveRecipe() {
        return activeRecipe;
    }

    public static void setActiveRecipe(Materials material) {
        activeRecipe = material.getRecipes().get(material.getState());
    }

    public void decrementWater() {
        if (activeRecipe.getWater() > 0) {
            activeRecipe.setWater(activeRecipe.getWater()-1);
        }
        setActiveRecipe(this);
    }

    public void decrementOther() {
        if (activeRecipe.getOther() > 0) {
            activeRecipe.setOther(activeRecipe.getOther()-1);
        }
        setActiveRecipe(this);
    }

    public static ArrayList<Materials> getAllMaterials() {
        return allMaterials;
    }
}
