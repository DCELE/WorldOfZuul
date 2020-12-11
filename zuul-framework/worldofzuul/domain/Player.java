package worldofzuul.domain;


public class Player {
    private static Inventory playerInventory;
    private static String playerThinks;
    public static ScoreSystem playerScore;

    public Player() {
        playerInventory = new Inventory();
        playerScore = new ScoreSystem(100);
    }

    public static Inventory getInventory() {
        return playerInventory;
    }

    // A player can pick up an item
    public static void pickUpItem(Item item) {
        Game.getCurrentRoom().getInventory().removeFromInventory(item);
        playerInventory.addToInventory(item);
    }

    // A player can drop an item
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
