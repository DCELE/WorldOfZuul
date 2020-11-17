package worldofzuul;

import java.util.Scanner;

public class Game {
    private Room currentRoom;
    private Inventory playerInventory;
    private Materials hemp, linen, bamboo, cotton, polyester;
    private Water water;
    private Bucket bucket;
    private Room mainRoom, materials, well, farm, factory, colorFactory, sewingFactory, fabricFactory;
    private Controller c1;


    public Game() {
        createRooms();
        playerInventory = new Inventory();
        c1 = new Controller();
    }

    private void createRooms() {

        Inventory mainRoomInventory, materialsInventory, wellInventory, farmInventory, factoryInventory, colorInventory, sewingInventory, fabricInventory;

        // Creating inventories
        mainRoomInventory = new Inventory();
        materialsInventory = new Inventory();
        wellInventory = new Inventory();
        farmInventory = new Inventory();
        factoryInventory = new Inventory();
        colorInventory = new Inventory();
        sewingInventory = new Inventory();
        fabricInventory = new Inventory();

        // Initializing rooms
        mainRoom = new Room("mainroom", "in the main room and can go to the other rooms from here.", mainRoomInventory);
        materials = new Room("materials", "in the material room. Here you can pick a material you want to work with.", materialsInventory);
        well = new Room("water", "in the water reservoir. If you have a bucket then you can pick up some water.", wellInventory);
        farm = new Room("farm", "in the farm. You can plant your chosen seed and grow them here.", farmInventory);
        factory = new Room("factory", "in the factory. You can process your product here.", factoryInventory);
        colorFactory = new Room("color", "in the coloring room of the factory. You can color your fabric here.", colorInventory, 1);
        sewingFactory = new Room("sewing", "in the sewing room of the factory. You can sew your fabric here.", sewingInventory);
        fabricFactory = new Room("fabric", "in the fabric room of the factory. You can make your T-shirt here.", fabricInventory);

        // Initializing items
        Room[] roomsToUseItem = new Room[]{farm, fabricFactory, colorFactory, sewingFactory};
        hemp = new Materials("hemp", 1, roomsToUseItem, new int[]{1, 2});
        linen = new Materials("linen", 2, roomsToUseItem, new int[]{2, 3});
        bamboo = new Materials("bamboo", 3, roomsToUseItem, new int[]{2, 3});
        cotton = new Materials("cotton", 4, roomsToUseItem, new int[]{2, 3});
        polyester = new Materials("polyester", 5, roomsToUseItem, new int[]{2, 3});

        bucket = new Bucket("bucket", 7, new Room[]{farm, factory});
        water = new Water();

        // Placing items
        materialsInventory.addToInventory(hemp);
        materialsInventory.addToInventory(linen);
        materialsInventory.addToInventory(bamboo);
        materialsInventory.addToInventory(cotton);
        materialsInventory.addToInventory(polyester);

        wellInventory.addToInventory(bucket);
        wellInventory.addToInventory(water);

        mainRoom.setExit(materials, new Room[]{materials, well, farm, factory});

        materials.setExit(mainRoom, new Room[]{mainRoom});

        well.setExit(mainRoom, new Room[]{mainRoom});

        farm.setExit(mainRoom, new Room[]{mainRoom});

        factory.setExit(mainRoom, new Room[]{mainRoom, colorFactory, sewingFactory, fabricFactory});

        colorFactory.setExit(factory, new Room[]{factory});

        sewingFactory.setExit(factory, new Room[]{factory});

        fabricFactory.setExit(factory, new Room[]{factory});

        currentRoom = mainRoom;
    }

    // Use materials in farm
    /*
    private void useItem(Command command) {
        // Check if there is a second word
        if (!command.hasSecondWord()) {
            System.out.println("Use what?");
            return;
        }
        // Check if inventory is empty
        if (playerInventory.getArrayList().size() == 0) {
            System.out.println("Your inventory is empty");
            return;
        }
        // Check if special case of use water, now you can write "use water"
        if (command.getSecondWord().equals(water.getName())) {
            command.setSecondWord(bucket.getName());
        }

        boolean isItemInInventory = false;
        for (Item item : playerInventory.getArrayList()) {
            // Check if item is in inventory
            if (command.getSecondWord().equals(item.getName())) {
                isItemInInventory = true;
                break;
            }
        }
        // If item is not in player's inventory
        if (!isItemInInventory) {
            System.out.println("You don't have that in your inventory");
            return;
        }

        Materials[] materialsArray = {hemp, linen, bamboo, cotton, polyester};
        boolean nothingPlanted = true;
        Materials plantedMaterial = null;
        // Check if something is planted already in farm
        for (Materials material : materialsArray) {
            if (material.isPlanted()) {
                nothingPlanted = false;
                plantedMaterial = material;
                break;
            }
        }
        // Block of code for usage of materials
        for (Materials material : materialsArray) {
            // Check if command is a material
            if (!command.getSecondWord().equals(material.getName())) {
                continue;
            }
            // Check if the room and the material's state correspond
            if (!(currentRoom == material.getRoomsToUseItem()[material.getState()])) {
                System.out.println("You cannot use " + material.getName() + " in here");
                System.out.println("Try going to " + material.getRoomsToUseItem()[material.getState()].getName());
                return;
            }


            // Check the materials stage
            if (material.getState() == 0) {
                // If something is planted
                if (!nothingPlanted) {
                    System.out.println("A seed is already planted: " + currentRoom.getInventory());
                    return;
                }
                // Plant material
                System.out.println("You plant " + material);
                currentRoom.getInventory().addToInventory(material);
                playerInventory.removeFromInventory(material);
                material.setPlanted();
                System.out.println(currentRoom.getInventory());
                System.out.println("It needs water: " + material.getWaterAmountNeeded()[0] + " time(s)");
                return;
            }
            // Check the materials stage
            if (material.getState() == 1) {
                // Make fabric
                System.out.println("You use machines to make fabric of " + material);
                material.upgradeState();
                System.out.println("It needs water: " + material.getWaterAmountNeeded()[1] + " time(s)");
                currentRoom.getInventory().addToInventory(material);
                playerInventory.removeFromInventory(material);
                System.out.println(currentRoom.getInventory());
                return;
            }
            // Check the materials stage
            if (material.getState() == 2) {
                // Dye fabric
                System.out.println("You dye " + material + " into the color " + material.getColor());
                material.upgradeState();
                currentRoom.getInventory().addToInventory(material);
                playerInventory.removeFromInventory(material);
                System.out.println(currentRoom.getInventory());
                return;
            }
            // Check the materials stage
            if (material.getState() == 3) {
                // Make T-shirt
                System.out.println("You're sewing " + material + " into a T-shirt with the color " + material.getColor());
                currentRoom.getInventory().addToInventory(material);
                playerInventory.removeFromInventory(material);
                System.out.println(currentRoom.getInventory());
                System.out.println("You've finished making a T-shirt, Type: \"quit\" to exit the game.");
                return;
            }
        }

        // Check if the command is the bucket
        if (command.getSecondWord().equals(bucket.getName())) {
            // Check if inventory contains bucket
            if (!playerInventory.contains(bucket)) {
                System.out.println("You need a " + bucket.getName() + " to do that");
                return;
            }
            // Check if bucket is filled with water
            if (!bucket.hasWater()) {
                System.out.println("You need to get " + water.getName() + " first");
                return;
            }
            // Check if the room is a room you can use water in
            if (!(currentRoom == bucket.getRoomsToUseBucket()[0] || currentRoom == bucket.getRoomsToUseBucket()[1])) {
                System.out.println("You cannot use " + bucket.getName() + " in " + currentRoom.getName());
                return;
            }
            // Check if the room is the farm
            if (currentRoom == bucket.getRoomsToUseBucket()[0]) {
                // Check if something is planted
                if (plantedMaterial == null) {
                    System.out.println("You need to plant something before watering it");
                    return;
                }
                // Watering seed
                plantedMaterial.decrementWaterAmountNeeded(0);
                System.out.println("You water " + plantedMaterial.getName());
                bucket.setHasWater();

                // Check if seed has fully grown
                if (plantedMaterial.getWaterAmountNeeded()[0] == 0) {
                    // Material is no longer planted
                    plantedMaterial.setPlanted();
                    // Seed becomes plant
                    plantedMaterial.upgradeState();
                    System.out.println(plantedMaterial.getName() + " is fully grown, you can pick it up");
                    return;
                }
                System.out.println("It needs water: " + plantedMaterial.getWaterAmountNeeded()[0] + " time(s) more");
                return;
            }
            // Check if the room is the factory
            if (currentRoom == bucket.getRoomsToUseBucket()[1]) {
                // Pour water in the machines/filling them up.

            }
        }
    }

    private void showInventory(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("What?");
            return;
        }
        if (playerInventory.getArrayList().size() == 0) {
            System.out.println("Your inventory is empty");
        } else {
            System.out.println("Your inventory: " + playerInventory.getArrayList());
        }
    }

    // Pick up item method
    private void getItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Get what?");
            return;
        }

        if (currentRoom.getInventory().getArrayList().size() == 0) {
            System.out.println(currentRoom.getName() + " has no items to pick up");
            return;
        }

        boolean isItemInRoom = false;
        Item getThisItem = null;
        for (Item item : currentRoom.getInventory().getArrayList()) {
            // Check if item is in Room
            if (command.getSecondWord().equals(item.getName())) {
                isItemInRoom = true;
                getThisItem = item;
                break;
            }
        }
        // If item is not in current room
        if (!isItemInRoom) {
            System.out.println(currentRoom.getInventory());
            System.out.println(currentRoom.getName() + " does not contain " + command.getSecondWord());
            return;
        }

        Materials[] materialsArray = {hemp, linen, bamboo, cotton, polyester};
        for (Materials material : materialsArray) {
            if (!command.getSecondWord().equals(material.getName())) {
                continue;
            }

            if (material.isPlanted()) {
                Scanner s = new Scanner(System.in);
                System.out.println(material.getName() + " is planted, do you wish to continue?");
                String nextLine = s.nextLine();
                if (nextLine.equals("no")) {
                    System.out.println("You change your mind");
                    return;
                }
                if (!nextLine.equals("yes")) {
                    System.out.println("Try again, next time answer \"yes\" or \"no\"");
                    return;
                }
                material.setPlanted();
            }
        }
        // Water is a special item, which can only be picked up in a bucket
        if (command.getSecondWord().equals(water.getName())) {
            if (!playerInventory.contains(bucket)) {
                System.out.println("You need a bucket in your inventory to get water");
                return;
            }
            if (bucket.hasWater()) {
                System.out.println("You've already filled your bucket with water");
                return;
            }
            bucket.setHasWater();
            System.out.println("You fill your bucket with water");
            return;
        }
        // If material is planted, material is not planted or its just an ordinary item pick it up
        currentRoom.getInventory().removeFromInventory(getThisItem);
        playerInventory.addToInventory(getThisItem);
        System.out.println("You pick up: " + getThisItem);
    }

    // Drop item method
    private void dropItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }

        // For each item in player inventory check if the command word is in the inventory.
        if (playerInventory.getArrayList().size() == 0) {
            System.out.println("You have nothing to drop");
            return;
        }
        for (Item item : playerInventory.getArrayList()) {
            if (command.getSecondWord().equals(item.getName())) {
                currentRoom.getInventory().addToInventory(item);
                playerInventory.removeFromInventory(item);
                System.out.println(currentRoom.getInventory());
                System.out.println("You dropped: " + item);
                return;
            } else if (command.getSecondWord().equals(water.getName()) && playerInventory.contains(bucket) && bucket.hasWater()) {
                bucket.setHasWater();
                System.out.println("You dropped water");
                System.out.println(currentRoom.getInventory());
            }
        }
    }

     */
}
