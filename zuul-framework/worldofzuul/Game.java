package worldofzuul;

public class Game
{
    private Parser parser;
    private Room currentRoom;
        

    public Game() 
    {
        createRooms();
        parser = new Parser();
        createPlayerInventory();
    }

    private void createRooms()
    {

        Inventory mainRoomInventory, materialsInventory, wellInventory, farmInventory, factoryInventory, colorFactoryInventory, myInventory;

        // Creating inventories
        mainRoomInventory = new Inventory();
        materialsInventory = new Inventory();
        wellInventory = new Inventory();
        farmInventory = new Inventory();
        factoryInventory = new Inventory();
        colorFactoryInventory = new Inventory();
        myInventory = new Inventory();

        // Creating items
        Item bucket = new Bucket("bucket", 42);
        Item water = new Materials("water", 6);

        Item hemp = new Materials("hemp", 1);
        Item linen = new Materials("linen",2);
        Item bamboo = new Materials("bamboo", 3);
        Item cotton = new Materials("cotton", 4);

        Item polyester = new Materials("polyester", 5);


        Room mainRoom, materials, well, farm, factory, colorFactory, exit;


        mainRoom = new Room("in the main room and can go to the other rooms from here.",mainRoomInventory);
        materials = new Room("in the material room. Here you can pick a material you want to work with.",materialsInventory);
        well = new Room("in the water reservoir. If you have a bucket then you can pick up some water.",wellInventory);
        farm = new Room("in the farm. You can plant your chosen seed and grow them here.",farmInventory);
        factory = new Room("in the factory. You can process your product here.",factoryInventory);
        colorFactory = new Room("in the coloring room of the factory. You can color your fabric here.",colorFactoryInventory);

        // Placing items
        //Materials room;
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

    public void createPlayerInventory() {
        Inventory playerInventory = new Inventory();
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
        }
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        return wantToQuit;
    }

    private void getItem(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Get what?");
            return;
        }

        String item = command.getSecondWord();

        currentRoom.
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
}
