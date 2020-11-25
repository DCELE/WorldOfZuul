package worldofzuul;

public class Player {
    private static Inventory playerInventory;

    public Player() {
        playerInventory = new Inventory();
    }

    public static Inventory getInventory() {
        return playerInventory;
    }

    public static void pickUpItem(Item item) {
        Game.getCurrentRoom().getInventory().removeFromInventory(item);
        playerInventory.addToInventory(item);
    }

    public static void dropItem(Item item) {
        Game.getCurrentRoom().getInventory().addToInventory(item);
        playerInventory.removeFromInventory(item);
    }
}
