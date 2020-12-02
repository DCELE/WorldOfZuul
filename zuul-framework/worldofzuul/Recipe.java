package worldofzuul;

import com.sun.prism.Material;

public class Recipe {
    private String name;
    private int water;
    private int other; // Either pesticides or chemicals never both

    public Recipe (String name, int water, int other) {
        this.name = name;
        this.water = water;
        this.other = other;
    }

    @Override
    public String toString() {
        String water = "Water needed: " + this.water + "\n";
        String other = "Other needed: " + this.other;

        if (this.water == 0 && this.other == 0) {

        }


        return "Recipe: \n" +
                name + "\n" +
                water + other;
    }

    public String getName() {
        return name;
    }

    public int getWater() {
        return water;
    }

    public int getOther() {
        return other;
    }

    public void setName(String name) {
        this.name = name;
    }
}
