package worldofzuul;

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
        String usableIn = "1. Go to " + this.usableIn.getName() + ":\n";
        String use = "2. Use " + Game.getChosenMaterial().getName() + "\n";
        String water = "3. Water " + this.water + " time(s)\n";
        String other;

        if (Materials.getActiveRecipe().getUsableIn() == Game.getPesticides().getRoomToUsePesticides()) {
            other = "4. Pesticides " + this.other + " time(s)";
        } else {
            other = "4. Chemical " + this.other + " time(s)";
        }

        return "Recipe: " +
                name + "\n" +
                usableIn + use + water + other;
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
