package worldofzuul;

public class Bucket extends Item {
    private boolean hasWater;

    public Bucket(String name, int id) {
        super(name, id);
        hasWater = false;
    }

    public boolean hasWater()
    {
        return hasWater;
    }

    public void setHasWater() {
        if (hasWater) {
            this.setName("bucket");
            this.hasWater = false;
        } else {
            this.setName("bucket with water");
            this.hasWater = true;
        }
    }
}