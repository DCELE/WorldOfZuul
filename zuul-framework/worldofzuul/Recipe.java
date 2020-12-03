package worldofzuul;

import com.sun.prism.Material;

public class Recipe {
    private String name;
    private Room usableIn;
    private int water;
    private int other; // Either pesticides or chemicals never both

    public Recipe (Room usableIn, int water, int other) {
        this.usableIn = usableIn;
        this.water = water;
        this.other = other;
    }

    @Override
    public String toString() {
        String water = "Water needed: " + this.water + "\n";
        String other = "Other needed: " + this.other;
        String returnString = "Recipe: \n" +
                name + "\n" +
                water + other;
        return returnString;
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

    public void setWater(int water) {
        this.water = water;
    }

    public void setOther(int other) {
        this.other = other;
    }

    public void setName(String now, String toBecome) {
        this.name = now + " -->" + toBecome;
    }

    public Room getUsableIn() {
        return usableIn;
    }
}
