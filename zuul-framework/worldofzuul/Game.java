package worldofzuul;

import java.util.ArrayList;

public class Game
{
    private Parser parser;
    private Room currentRoom;

    //Ny Arraylist
    ArrayList<Item> inventory = new ArrayList<Item>();

    public Game()
    {
        createRooms();
        parser = new Parser();

    }
    // Main-room, Materials, Water, Farm, Factory, Color-Factory

    private void createRooms()
    {

        Room mainRoom, materials, water, farm, factory, colorFactory;


        mainRoom = new Room("in the main room and can go to the other rooms from here.");
        materials = new Room("in the material room. Here you can pick a material you want to work with.");
        water = new Room("in the water reservoir. If you have a bucket then you can collect some water.");
        farm = new Room("in the farm. You can plant your chosen seed and grow them here.");
        factory = new Room("in the factory. You can process your product here.");
        colorFactory = new Room("in the coloring room of the factory. You can color your fabric here.");
        

        mainRoom.setExit("materials", materials);
        mainRoom.setExit("water", water);
        mainRoom.setExit("farm", farm);
        mainRoom.setExit("factory", factory);

        materials.setExit("mainroom", mainRoom);

        water.setExit("mainroom", mainRoom);

        farm.setExit("mainroom", mainRoom);

        factory.setExit("mainroom", mainRoom);
        factory.setExit("colorfactory", colorFactory);

        colorFactory.setExit("factory", factory);

        currentRoom = mainRoom;

        //Items in the materials
        materials.setItem(new Item("hemp"));
        materials.setItem(new Item("flax"));
        materials.setItem(new Item("cotton"));
        materials.setItem(new Item("bamboo"));

        materials.setItem(new Item("polyester"));

        //Items at the waterhole
        water.setItem(new Item("bucket"));
        water.setItem(new Item("water"));


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
        System.out.println("Welcome to the World of Zuul!");
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
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        //Ny inventory
        else if (commandWord == CommandWord.INVENTORY)
        {
            printInventory();
        }
        //Ny getItem
        else if (commandWord == CommandWord.GET)
        {
            getItem(command);
        }
        //Ny dropItem
        else if (commandWord == CommandWord.DROP)
        {
            dropItem(command);
        }
        //Ny collectWater
        else if (commandWord == CommandWord.COLLECT)
        {
        collectWater(command);
        }

        return wantToQuit;
    }

    //Ny collectWater
    private void collectWater(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Collect what?");
            return;
        }

        String item = command.getSecondWord();
        Item newItem = currentRoom.getItem(item);
        int index1 = 0;

        int index2 = 0;
        for (int i=0; i<inventory.size(); i++)
        {
            if (inventory.get(i).getDescription().equals("bucket")) {
                newItem = inventory.get(i);
                index2 = i;
                System.out.println("You picked up water with the bucket");
                inventory.add(new Item("bucket with water"));
                inventory.remove(index1);
            }
            else {
                System.out.println("You're missing a bucket");
            }

           // inventory.remove(index1);
        }


        }


/*
        if (newItem == null) {
            System.out.println("That item is not here!");
        }
        else {
            inventory.add(newItem);
            currentRoom.removeItem(item);
            System.out.println("Picked up: " + item);
        }*/



    //Ny dropItem
    private void dropItem(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }

        String item = command.getSecondWord();

        Item newItem = null;
        int index = 0;
        for (int i=0; i<inventory.size(); i++) {
            if (inventory.get(i).getDescription().equals(item)) {
                newItem = inventory.get(i);
                index = i;
            }
        }
        if (newItem == null) {
            System.out.println("That item is not in your inventory!");
        }
        else {
            inventory.remove(index);
            currentRoom.setItem(new Item(item));
            System.out.println("Dropped: " + item);
        }
    }


    //Ny getItem
    private void getItem(Command command)
    {
        if(!command.hasSecondWord())
        {
            System.out.println("Get what?");
            return;
        }

        String item = command.getSecondWord();

        Item newItem = currentRoom.getItem(item);

        if (newItem == null) {
            System.out.println("That item is not here!");
        }
        else {
            inventory.add(newItem);
            currentRoom.removeItem(item);
            System.out.println("Picked up: " + item);
        }
    }

    //Ny printInventory metode
    private void printInventory()
    {
        String output = "";
        for (int i=0; i<inventory.size(); i++)
        {
            output += inventory.get(i).getDescription() + " ";
        }
        System.out.println("You are carrying:");
        System.out.println(" " + output);
    }
    //laves om
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
}
