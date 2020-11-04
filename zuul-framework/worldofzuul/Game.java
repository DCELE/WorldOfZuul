package worldofzuul;

import java.util.Arrays;

public class Game
{
    private Parser parser;
    private Room currentRoom;
    private Inventory playerInventory;
    private Materials hemp, linen, bamboo, cotton, polyester;
    private Water water;
    private Bucket bucket;
    private Room mainRoom, materials, well, farm, factory, colorFactory, processings,sewings;



    public Game() 
    {
        createRooms();
        parser = new Parser();
        playerInventory = new Inventory();

    }

    private void createRooms()
    {

        Inventory mainRoomInventory, materialsInventory, wellInventory, farmInventory, factoryInventory,
                colorFactoryInventory, processingsInventory, sewingsInventory;

        // Creating inventories
        mainRoomInventory = new Inventory();
        materialsInventory = new Inventory();
        wellInventory = new Inventory();
        farmInventory = new Inventory();
        factoryInventory = new Inventory();
        colorFactoryInventory = new Inventory();
        processingsInventory = new Inventory();
        sewingsInventory = new Inventory();

//        FabricMachines processMachine = new FabricMachines("process-machine", 9);
//        FabricMachines sewingMachine = new FabricMachines("sewing-machine",10);


        // Initializing rooms
        mainRoom = new Room("in the main room and can go to the other rooms from here.",mainRoomInventory);
        materials = new Room("in the material room. Here you can pick a material you want to work with.",materialsInventory);
        well = new Room("in the water reservoir. If you have a bucket then you can pick up some water.",wellInventory);
        farm = new Room("in the farm. You can plant your chosen seed and grow them here.",farmInventory);
        factory = new Room("in the factory. You can process your product here with the ",factoryInventory);
        processings = new Room("in processing room, here you can use stuff ",processingsInventory);
        sewings = new Room("in the sewing room, here you can sew your fabric", sewingsInventory);
        colorFactory = new Room("in the coloring room of the factory. You can color your fabric here.",colorFactoryInventory);

        // Initializing items
        hemp = new Materials("hemp",1, new Room[] {farm, factory});
        linen = new Materials("linen",2);
        bamboo = new Materials("bamboo",3);
        cotton = new Materials("cotton",4);
        polyester = new Materials("polyester",5);

        bucket = new Bucket("bucket",7);
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
        factory.setExit("processing", processings);
        factory.setExit("sewing", sewings);
        colorFactory.setExit("factory", factory);

        processings.setExit("factory",factory);
        sewings.setExit("factory",factory);

        currentRoom = mainRoom;
    }

    public void play() 
    {            
        printWelcome();

                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Wool!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    private boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if(commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            printHelp();
        }
        else if (commandWord == CommandWord.GO) {
            goRoom(command);
        }
        else if (commandWord == CommandWord.GET)
        {
            getItem(command);
        } else if (commandWord == CommandWord.DROP) {
            dropItem(command);
        } else if (commandWord == CommandWord.PLANT) {
            plantItem(command);
        }
        else if (commandWord == CommandWord.INVENTORY)
        {
            showInventory(command);
        }
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        return wantToQuit;
    }

    // Plant materials in farm
    private void plantItem(Command command) {
        if(!command.hasSecondWord()) {
            System.out.println("Plant what?");
            return;
        }
        // Creating arrays with Rooms and Materials
        Materials[] materialsArray = {hemp, linen, bamboo, cotton, polyester};
        Room[] roomArray = {mainRoom, materials, well, farm, factory, colorFactory};

        // If player does have something in inventory (double negative, not nothing)
        if (!(playerInventory.getArrayList().size() == 0)) {
            for (Item item : playerInventory.getArrayList()) {
                // Check if Second command is an item in inventory
                if (command.getSecondWord().equals(item.getName())) {
                    // Idk
                    for (Materials material : materialsArray) {
                        for (Room room : roomArray) {
                            if (command.getSecondWord().equals(material.getName()) && material.getState() == 1 && currentRoom == farm) {
                                System.out.println("You plant " + material);
                                currentRoom.getInventory().addToInventory(item);
                                playerInventory.removeFromInventory(item);
                                System.out.println("Items in this room " + currentRoom.getInventory());
                            }
                        }
                    }

                } else if (!command.getSecondWord().equals(item.getName())) {
                    System.out.println("You don't have that item in your inventory");
                } else {
                    hemp.getRoomsToUseItem();
                    System.out.println("You cannot use that item");
                }
            }
        } else {
            System.out.println("You have nothing in your inventory to use");
        }
    }

    private void showInventory(Command command) {
        if(command.hasSecondWord()) {
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
    private void getItem(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Get what?");
            return;
        }
        // Bucket is broken.
        // For each item in the room check if the command is the same as an item in the room
        for (Item item : currentRoom.getInventory().getArrayList()) {
            if (command.getSecondWord().equals(item.getName())) {
                // Can't pick up water without bucket
                if (!item.getName().equals("water")) {
                    currentRoom.getInventory().removeFromInventory(item);
                    playerInventory.addToInventory(item);
                    System.out.println("You picked up: " + item);
                    break;
                    // If you try picking up water, then check if player has a bucket
                } else if (command.getSecondWord().equals("water") && playerInventory.contains(bucket) && bucket.hasWater()) {
                    System.out.println("You've already filled your bucket with water");
                    break;
                }
                else if (command.getSecondWord().equals("water") && playerInventory.contains(bucket)) {
                    // Set bucket's hasWater boolean to true.
                    bucket.setHasWater();
                    System.out.println("You filled your bucket with water");
                    break;
                    // If player does not have a bucket and tries to get water
                } else if (command.getSecondWord().equals("water") && !playerInventory.contains(bucket)) {
                    System.out.println("You don't have a bucket in your inventory");
                    break;
                }
            }
        }
    }
    // Drop item method
    private void dropItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Get what?");
            return;
        }

        // For each item in player inventory check if the command word is in the inventory.
        for (Item item : playerInventory.getArrayList()) {
            if (command.getSecondWord().equals(item.getName())) {
                currentRoom.getInventory().addToInventory(item);
                playerInventory.removeFromInventory(item);
                System.out.println("You dropped: " + item);
                System.out.println(currentRoom.getInventory());
                break;
            } else if (command.getSecondWord().equals(water.getName()) && playerInventory.contains(bucket) && bucket.hasWater()) {
                bucket.setHasWater();
                System.out.println("You dropped water");
                System.out.println(currentRoom.getInventory());
            }
        }
    }

    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;
        }
    }

    private void processPlant(Command command)
    {
        FabricMachines recip = new FabricMachines("bucket, bucket, bucket");
        Inventory invv = processings.getInventory();
        System.out.println("Missing " + Arrays.toString(missing_Words(recip, invv )));
    }

    public static String[] missing_Words(String t, String s) {

        String[] s1 = t.split(" ");
        String[] s2 = s.split(" ");
        int sz = s1.length - s2.length;
        String[] missing_str = new String[sz];
        int c = 0;
        for (int i = 0; i < s1.length; i++) {
            int flag = 0;
            for (int j = 0; j < s2.length; j++) {
                if (s1[i].equals(s2[j]))
                    flag = 1;
            }
            if (flag == 0) {
                missing_str[c++] = s1[i];
            }
        }
        return missing_str;
    }

}
