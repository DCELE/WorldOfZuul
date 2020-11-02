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
        Inventory outsideInventory, theatreInventory, pubInventory, labInventory, officeInventory, wellInventory, myInventory;

        // Creating inventories
        outsideInventory = new Inventory();
        theatreInventory = new Inventory();
        pubInventory = new Inventory();
        labInventory = new Inventory();
        officeInventory = new Inventory();
        wellInventory = new Inventory();
        myInventory = new Inventory();

        // Creating items
        Item bucket = new Bucket("bucket", 42);

        // Creating rooms
        Room outside, theatre, pub, lab, office, well;
        outside = new Room("outside the main entrance of the university", outsideInventory);
        theatre = new Room("in a lecture theatre", theatreInventory);
        pub = new Room("in the campus pub", pubInventory);
        lab = new Room("in a computing lab", labInventory);
        office = new Room("in the computing admin office", officeInventory);
        well = new Room("outside at the well north of the university", wellInventory);

        // Placing items
        wellInventory.addToInventory(bucket);

        // Dealing with directions
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.setExit("north", well);

        theatre.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        well.setExit("south", outside);

        currentRoom = outside;
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
        else if (commandWord == CommandWord.COLLECT) {

        }
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        return wantToQuit;
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
