package worldofzuul;

public class Player {
    private static Inventory playerInventory;
    private static String playerThinks;

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

    public static String getPlayerThinks() {
        return playerThinks;
    }

    public static void setPlayerThinks(String playerThinks) {
        Player.playerThinks = playerThinks;
    }
}
