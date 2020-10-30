package worldofzuul;

public class Game
{
    private Parser parser;
    private Room currentRoom;


    public Game()
    {
        createRooms();
        parser = new Parser();
    }


    //Creation of rooms and exits
    private void createRooms()
    {
        Room mainRoom, farm, water, textileFactory, colourFactory, materialRoom;

        mainRoom = new Room("in the mainRoom and can go to the other rooms from here.");
        farm = new Room("in the farm. Here you can plant your chosen seeds and grow them.");
        water = new Room("at the waterhole. If you have a bucket, you can pick up some water.");
        textileFactory = new Room("in the textileFactory. Fabrics are made in here.");
        colourFactory = new Room("in the colourFactory. Here you can color your fabric.");
        materialRoom = new Room("in the materialRoom. You can pick seeds you want to grow and work with.");

        mainRoom.setExit("east", farm);
        mainRoom.setExit("south", textileFactory);
        mainRoom.setExit("west", water);
        mainRoom.setExit("north", materialRoom);

        farm.setExit("west", mainRoom);

        water.setExit("east", mainRoom);

        textileFactory.setExit("north", mainRoom);
        textileFactory.setExit("south", colourFactory);

        colourFactory.setExit("north", textileFactory);

        materialRoom.setExit("south", mainRoom);

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
