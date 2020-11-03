package worldofzuul;

public class Bucket extends Item {
    private boolean hasWater;
    private boolean full;

    public Bucket(String name, int id) {
        super(name, id);
        this.full = full;

    }

    public void setHasWater(boolean hasWater) {
        this.hasWater = hasWater;
    }
}
