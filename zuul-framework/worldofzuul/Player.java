package worldofzuul;

public class Player {
    private static Inventory playerInventory;

    public Player() {
        playerInventory = new Inventory();
    }

    public static Inventory getInventory() {
        return playerInventory;
    }
}
