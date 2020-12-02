package worldofzuul;

public class Recipe {
    private String name;
    private int water;
    private int other; // Either pesticides or chemicals never both

    public Recipe (String name, int water, int other) {
        this.name = name;
        this.water = water;
        this.other = other;
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
