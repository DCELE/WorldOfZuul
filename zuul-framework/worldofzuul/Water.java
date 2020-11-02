package worldofzuul;

public class Water extends Item {
    private static String name;
    private static int id;

    public Water(String name, int id) {
        super(name, id);
    }

    @Override
    public String getName() {
        return name;
    }
}
