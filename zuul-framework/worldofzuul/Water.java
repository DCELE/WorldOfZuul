package worldofzuul;

public class Water extends Item {
    private static String name;
    private static int id;

    public Water() {
        super("Water from the well. \nIt is somewhat clean.\nUse wisely and be careful not\nto spill it.");
        name  = "water";
        id = 6;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getName() {
        return name;
    }
}
