package worldofzuul.domain;

import java.util.ArrayList;

public class Materials extends Item {
    // State is either seed, plant, fabric or t-shirt.
    private int state;
    private ArrayList<Recipe> recipes;
    private static Recipe activeRecipe;
    private boolean planted;
    private boolean inProcess;
    // The rooms in order, where you can use (interact with) the item
    private String color;
    private static String[] allColors;
    private String[] stateNames;
    private static ArrayList<Materials> allMaterials = new ArrayList<>();
    private int points;

    public Materials(String name, int id, int points, String itemIcon, String description) {
        super(name, id, itemIcon, name.toUpperCase() + "\n" + description);
        this.state = 0;
        this.planted = false;
        this.color = "colored";
        allColors = new String[]{"Natural", "Blue"};
        this.points = points;
        allMaterials.add(this);

        stateNames = new String[]{" seed", " plant", " fabric", " " + this.color + " fabric", " " + this.color + " t-shirt"};
        if (super.getId() == 5) {
            this.state = 1;
            stateNames = new String[]{" chemicals", " fabric", " " + this.color + " fabric", " " + this.color + " t-shirt"};

        }
        this.recipes = new ArrayList<>();
        setNameForState();
    }

    public void addRecipe(Recipe recipe) {
        if (this.recipes.size() < stateNames.length) {
            recipe.setName(stateNames[this.recipes.size()], stateNames[this.recipes.size() + 1]);
            this.recipes.add(recipe);
        }
    }
    /* getPoints for material. Didnt work bc materials change name throughout the game
    public int getPoints(){
        return points;
    }*/


    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void upgradeState() {
        this.state += 1;
        if (!this.color.equals(allColors[0].toLowerCase()) || this.color.equals("colored")) {
            this.setItemIcon(activeRecipe.getImage());
        } else {
            if (state == 3) {
                this.setItemIcon("worldofzuul/WorldOfZuulPNG/Icons/FabricWhiteIcon.png");
            } else {
                this.setItemIcon("worldofzuul/WorldOfZuulPNG/Items/TShirtWhite.png");
            }
        }
        setNameForState();
        if (getId() == 5) {
            if (recipes.size() > state-1) {
                setActiveRecipe(this);
                return;
            }
        }
        if (recipes.size() > state) {
            setActiveRecipe(this);
        }
    }

    public void setNameForState() {
        updateStateNames();
        int stateNameInt = getState();
        if (getId() == 5) {
            stateNameInt = getState() - 1;
        }
        this.setName(getName().split(" ")[0] + stateNames[stateNameInt]);
    }

    public String[] getStateNames() {
        return stateNames;
    }

    public void updateStateNames() {
        stateNames = new String[]{" seed", " plant", " fabric", " " + this.color + " fabric", " " + this.color + " t-shirt"};
        if (getId() == 5) {
            stateNames = new String[]{" chemicals", " fabric", " " + this.color + " fabric", " " + this.color + " t-shirt"};
        }
    }

    public int getState() {
        return state;
    }

    public Room getRoomToUse() {
        return activeRecipe.getUsableIn();
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
        this.color = color.toLowerCase();
        if (color.equals(allColors[0])) {
            activeRecipe.setWater(0);
            activeRecipe.setOther(0);
        } else if (color.equals(allColors[1])) {
            activeRecipe.setWater(2);
            activeRecipe.setOther(2);
        }
    }

    public static Recipe getActiveRecipe() {
        return activeRecipe;
    }

    public static void setActiveRecipe(Materials material) {
        if (material.getId() == 5) {
            activeRecipe = material.getRecipes().get(material.getState() - 1);
            return;
        }
        activeRecipe = material.getRecipes().get(material.getState());
    }

    public void decrementWater() {
        if (activeRecipe.getWater() > 0) {
            activeRecipe.setWater(activeRecipe.getWater() - 1);
        }
        setActiveRecipe(this);
    }

    public void decrementOther() {
        if (activeRecipe.getOther() > 0) {
            activeRecipe.setOther(activeRecipe.getOther() - 1);
        }
        setActiveRecipe(this);
    }

    public static ArrayList<Materials> getAllMaterials() {
        return allMaterials;
    }

    public String[] getAllColors() {
        return allColors;
    }

}
