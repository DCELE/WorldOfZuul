package worldofzuul;

public class Water extends Item {
    private static String name;
    private static int id;

    public Water() {
        name  = "water";
        id = 6;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
