package worldofzuul;

import java.util.Scanner;

public class Game {
    private Parser parser;
    private Room currentRoom;
    private Inventory playerInventory;
    private Materials hemp, linen, bamboo, cotton, polyester;
    private Water water;
    private Bucket bucket;
    private Room mainRoom, materials, well, farm, factory, colorFactory;


    public Game() {
        createRooms();
        parser = new Parser();
        playerInventory = new Inventory();

    }

    private void createRooms() {

        Inventory mainRoomInventory, materialsInventory, wellInventory, farmInventory, factoryInventory, colorFactoryInventory;

        // Creating inventories
        mainRoomInventory = new Inventory();
        materialsInventory = new Inventory();
        wellInventory = new Inventory();
        farmInventory = new Inventory();
        factoryInventory = new Inventory();
        colorFactoryInventory = new Inventory();

        // Initializing rooms
        mainRoom = new Room("mainroom", "in the main room and can go to the other rooms from here.", mainRoomInventory);
        materials = new Room("materials", "in the material room. Here you can pick a material you want to work with.", materialsInventory);
        well = new Room("water", "in the water reservoir. If you have a bucket then you can pick up some water.", wellInventory);
        farm = new Room("farm", "in the farm. You can plant your chosen seed and grow them here.", farmInventory);
        factory = new Room("factory", "in the factory. You can process your product here.", factoryInventory);
        colorFactory = new Room("colorfactory", "in the coloring room of the factory. You can color your fabric here.", colorFactoryInventory);

        // Initializing items
        hemp = new Materials("hemp", 1, new Room[]{farm, factory, colorFactory, factory}, 1);
        linen = new Materials("linen", 2, new Room[]{farm, factory, colorFactory, factory}, 3);
        bamboo = new Materials("bamboo", 3, new Room[]{farm, factory, colorFactory, factory}, 4);
        cotton = new Materials("cotton", 4, new Room[]{farm, factory, colorFactory, factory}, 5);
        polyester = new Materials("polyester", 5, new Room[]{factory, colorFactory, factory});

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

        mainRoom.setExit("materials", materials);
        mainRoom.setExit("water", well);
        mainRoom.setExit("farm", farm);
        mainRoom.setExit("factory", factory);

        materials.setExit("mainroom", mainRoom);

        well.setExit("mainroom", mainRoom);

        farm.setExit("mainroom", mainRoom);

        factory.setExit("mainroom", mainRoom);
        factory.setExit("colorfactory", colorFactory);

        colorFactory.setExit("factory", factory);

        currentRoom = mainRoom;
    }

    public void play() {
        printWelcome();


        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing. Good bye.");
    }

    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Wool!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if (commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            printHelp();
        } else if (commandWord == CommandWord.GO) {
            goRoom(command);
        } else if (commandWord == CommandWord.GET) {
            getItem(command);
        } else if (commandWord == CommandWord.DROP) {
            dropItem(command);
        } else if (commandWord == CommandWord.USE) {
            useItem(command);
        } else if (commandWord == CommandWord.INVENTORY) {
            showInventory(command);
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        return wantToQuit;
    }

    // Use materials in farm
    private void useItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Use what?");
            return;
        }
        // Creating arrays with Rooms and Materials
        Materials[] materialsArray = {hemp, linen, bamboo, cotton, polyester};
        //Room[] roomArray = {mainRoom, materials, well, farm, factory, colorFactory};

        // Search through items in inventory
        for (Item item : playerInventory.getArrayList()) {
            // If player does have something in inventory (double negative, not nothing)
            if (playerInventory.getArrayList().size() != 0) {
                // Check if second command is an item in inventory
                if (command.getSecondWord().equals(item.getName())) {
                    // Going through all materials
                    for (Materials material : materialsArray) {
                        // Check if second command is a material and its state is 1 and current room is farm
                        if (command.getSecondWord().equals(material.getName())) {
                            // Check if current room corresponds with the state of the material (Seeds (state 1) can be used in farm [room 1])
                            if (currentRoom == material.getRoomsToUseItem()[material.getState()]) {
                                if (material.getState() == 0) {
                                    boolean nothingPlanted = true;
                                    for (Materials mats : materialsArray) {
                                        if (mats.isPlanted()) {
                                            nothingPlanted = false;
                                            break;
                                        }
                                    }
                                    if (nothingPlanted) {
                                        // Plant material
                                        System.out.println("You plant " + material);
                                        currentRoom.getInventory().addToInventory(item);
                                        playerInventory.removeFromInventory(item);
                                        material.setPlanted();
                                        System.out.println(currentRoom.getInventory());
                                    } else {
                                        System.out.println("A seed is already planted: " + currentRoom.getInventory());
                                    }
                                    return;
                                } else if (material.getState() == 1) {
                                    // Make fabric
                                    System.out.println("You use machines to make fabric of " + material);
                                    currentRoom.getInventory().addToInventory(item);
                                    playerInventory.removeFromInventory(item);
                                    System.out.println(currentRoom.getInventory());
                                    return;
                                } else if (material.getState() == 2) {
                                    // Dye fabric
                                    System.out.println("You dye " + material + " into the color " + material.getColor());
                                    currentRoom.getInventory().addToInventory(item);
                                    playerInventory.removeFromInventory(item);
                                    System.out.println(currentRoom.getInventory());
                                    return;
                                } else if (material.getState() == 3) {
                                    // Make T-shirt
                                    System.out.println("You're sewing " + material + " into a T-shirt with the color " + material.getColor());
                                    currentRoom.getInventory().addToInventory(item);
                                    playerInventory.removeFromInventory(item);
                                    System.out.println(currentRoom.getInventory());
                                    return;
                                }
                            } else {
                                System.out.println("You cannot use " + item + " in " + currentRoom.getName() + ". Try going to " + material.getRoomsToUseItem()[material.getState()].getName());
                                return;
                            }
                        }
                    }
                }
                // Check if second command is bucket or water
                if (command.getSecondWord().equals(bucket.getName()) || command.getSecondWord().equals("water")) {
                    if (playerInventory.contains(bucket)) {
                        // Check if bucket has water
                        if (bucket.hasWater()) {
                            // Check if current room is farm or factory
                            if (currentRoom == bucket.getRoomsToUseBucket()[0]) {
                                for (Materials mats : materialsArray) {
                                    if (mats.isPlanted()) {
                                        if (mats.getWaterAmountNeeded() > 0) {
                                            // Watering plants
                                            bucket.setHasWater();
                                            mats.decrementWaterAmountNeeded();
                                            System.out.println("You water " + mats.getName() + ", it needs water: " + mats.getWaterAmountNeeded() + " time(s) more");
                                            if (mats.getWaterAmountNeeded() == 0) {
                                                mats.upgradeState();
                                                mats.setPlanted();
                                                System.out.println(mats.getName() + " is fully grown, you can pick it up");
                                            }
                                        } else {
                                            System.out.println("Your plant doesn't need more water, you can pick it up");
                                        }
                                        return;
                                    }
                                }
                                System.out.println("No seeds are planted");
                                return;
                            } else if (currentRoom == bucket.getRoomsToUseBucket()[1]) {
                                // Pouring water in the machines/filling them up.
                                System.out.println("You pour water in the machinery");
                            } else {
                                System.out.println("You cannot use that item in " + currentRoom.getName() + " try going to: " + bucket.getRoomsToUseBucket()[0].getName() + " or " + bucket.getRoomsToUseBucket()[1].getName());
                                return;
                            }
                        } else {
                            System.out.println("You need to get water in your " + bucket.getName());
                            return;
                        }
                    } else {
                        System.out.println("You need a bucket in your inventory");
                        return;
                    }
                }
            }
        }
        if (playerInventory.getArrayList().size() == 0) {
            System.out.println("Your inventory is empty");
        } else {
            System.out.println(command.getSecondWord() + " isn't in your inventory: " + playerInventory.getArrayList());
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
        // For each item in the room check if the command is the same as an item in the room
        for (Item item : currentRoom.getInventory().getArrayList()) {
            if (command.getSecondWord().equals(item.getName())) {
                // Check materials for isPlanted
                Materials[] materialsArray = {hemp, linen, bamboo, cotton, polyester};
                for (Materials material : materialsArray) {
                    if (command.getSecondWord().equals(material.getName())) {
                        if (material.isPlanted()) {
                            Scanner s = new Scanner(System.in);
                            System.out.println(material.getName() + " is planted, do you wish to continue?");
                            String nextLine = s.nextLine();
                            if (nextLine.equals("yes")) {
                                currentRoom.getInventory().removeFromInventory(item);
                                playerInventory.addToInventory(item);
                                System.out.println("You picked up: " + item);
                                return;
                                // Reset plants amount of water needed
                            } else if (nextLine.equals("no")) {
                                System.out.println("You change your mind");
                                return;
                            } else {
                                System.out.println("Try again");
                                return;
                            }
                        } else {
                            // If material not planted just get it
                            currentRoom.getInventory().removeFromInventory(item);
                            playerInventory.addToInventory(item);
                            System.out.println("You picked up: " + item);
                            return;
                        }
                    }
                } // If you try picking up water, then check if player has a bucket
                if (command.getSecondWord().equals("water")) {
                    if (playerInventory.contains(bucket)) {
                        if (bucket.hasWater()) {
                            System.out.println("You've already filled your bucket with water");
                        } else {
                            // Set bucket's hasWater boolean to true.
                            bucket.setHasWater();
                            System.out.println("You filled your bucket with water");
                        }
                    } else {
                        // If player does not have a bucket and tries to get water
                        System.out.println("You don't have a bucket in your inventory");
                    }
                    return;
                } else {
                    currentRoom.getInventory().removeFromInventory(item);
                    playerInventory.addToInventory(item);
                    System.out.println("You picked up: " + item);
                    return;
                }
            }
        }
        System.out.println(command.getSecondWord() + " isn't in this room: " + currentRoom.getInventory());
    }

    // Drop item method
    private void dropItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Get what?");
            return;
        }

        // For each item in player inventory check if the command word is in the inventory.
        if (playerInventory.getArrayList().size() != 0) {
            for (Item item : playerInventory.getArrayList()) {
                if (command.getSecondWord().equals(item.getName()) || command.getSecondWord().equals("bucket")) {
                    currentRoom.getInventory().addToInventory(item);
                    playerInventory.removeFromInventory(item);
                    System.out.println("You dropped: " + item);
                    System.out.println(currentRoom.getInventory());
                    return;
                } else if (command.getSecondWord().equals(water.getName()) && playerInventory.contains(bucket) && bucket.hasWater()) {
                    bucket.setHasWater();
                    System.out.println("You dropped water");
                    System.out.println(currentRoom.getInventory());
                }
            }
        } else {
            System.out.println("You have nothing to drop");
        }
    }

    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;
        }
    }
}
