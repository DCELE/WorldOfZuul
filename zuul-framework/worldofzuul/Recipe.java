package worldofzuul;

public class Recipe {
    private String name;
    private Room usableIn;
    private int water;
    private int other; // Either pesticides or chemicals never both
    private String itemIcon;

    public Recipe(Room usableIn, int water, int other, String itemIcon) {
        this.usableIn = usableIn;
        this.water = water;
        this.other = other;
        this.itemIcon = itemIcon;
    }

    @Override
    public String toString() {
        String pickUpMaterial = "Pick up " + Game.getChosenMaterial() + "\n";
        String usableIn = "Go to " + this.usableIn.getName() + ":\n";
        String use = "Use " + Game.getChosenMaterial().getName() + "\n";
        String water = "Water " + this.water + " time(s)\n";
        String other;

        if (Materials.getActiveRecipe().getUsableIn() == Game.getPesticides().getRoomToUse()) {
            other = "Pesticides " + this.other + " time(s)";
        } else {
            other = "Chemical " + this.other + " time(s)";
        }

        return "Recipe: " + this.name + "\n"
                + checkIfDone(pickUpMaterial, Player.getInventory().contains(Game.getChosenMaterial()) || Game.getChosenMaterial().isPlanted() || Game.getChosenMaterial().isInProcess())
                + checkIfDone(usableIn, Game.getCurrentRoom() == this.usableIn)
                + checkIfDone(use, Game.getChosenMaterial().isInProcess() || Game.getChosenMaterial().isPlanted())
                + checkIfDone(water, this.water == 0)
                + checkIfDone(other, this.other == 0);
    }

    private String checkIfDone(String string, boolean done) {
        if (done) {
            return "";
        }
        return string;
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

    public String getImage() {
        return itemIcon;
    }
}
