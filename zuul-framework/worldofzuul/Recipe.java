package worldofzuul;

public class Recipe {
    private String name;
    private int waterNeeded;
    private int otherNeeded; // Either pesticides or chemicals never both

    public Recipe (String name, int waterNeeded, int otherNeeded) {
        this.name = name;
        this.waterNeeded = waterNeeded;
        this.otherNeeded = otherNeeded;
    }

    public String getName() {
        return name;
    }

    public int getOtherNeeded() {
        return otherNeeded;
    }

    public int getWaterNeeded() {
        return waterNeeded;
    }

    public void setName(String name) {
        this.name = name;
    }
}
