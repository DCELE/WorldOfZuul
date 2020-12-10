package worldofzuul;

public class Game {
    private static Room currentRoom;
    private static Materials hemp, linen, bamboo, cotton, polyester;
    private static Water water;
    private static Bucket bucket;
    private static Chemicals chemicals;
    private static Pesticides pesticides;
    private static Materials chosenMaterial;
    private static Room mainRoom, materials, well, farm, factory, colorFactory, sewingFactory, fabricFactory;
    private static String gameGuides;
    private static ScoreSystem playerScore;

    public Game() {
        setupGame();
        new Player();
    }

    private void setupGame() {

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

        //Creating scoresystem
        ScoreSystem playerScore = new ScoreSystem(100);

        // Initializing rooms
        mainRoom = new Room("Mainroom", "in the main room and can go to the other rooms from here", mainRoomInventory, "worldofzuul/WorldOfZuulPNG/Rooms/Mainroom.png");
        materials = new Room("Materials", "in the material room. Here you can pick a material you want to work with", materialsInventory, "worldofzuul/WorldOfZuulPNG/Rooms/Materials.png");
        well = new Room("Water", "in the water reservoir. If you have a bucket then you can pick up some water", wellInventory, "worldofzuul/WorldOfZuulPNG/Rooms/Well.png");
        farm = new Room("Farm", "on the farm. You can plant your chosen seed and grow them here", farmInventory, "worldofzuul/WorldOfZuulPNG/Rooms/Farm.png");
        factory = new Room("Factory", "in the factory. You can process your product here", factoryInventory, "worldofzuul/WorldOfZuulPNG/Rooms/Factory.png");
        colorFactory = new Room("Color", "in the coloring room of the factory. You can color your fabric here", colorInventory, "worldofzuul/WorldOfZuulPNG/Rooms/Coloring.png");
        sewingFactory = new Room("Sewing", "in the sewing room of the factory. You can sew your fabric here", sewingInventory, "worldofzuul/WorldOfZuulPNG/Rooms/Sewing.png");
        fabricFactory = new Room("Fabric", "in the fabric room of the factory. You can make your T-shirt here",fabricInventory, "worldofzuul/WorldOfZuulPNG/Rooms/Fabric.png");

        // Initializing items
        hemp = new Materials("hemp", 1, -5,"worldofzuul/WorldOfZuulPNG/Items/HempSeed.png");
        linen = new Materials("linen", 2, -15, "worldofzuul/WorldOfZuulPNG/Items/LinenSeed.png");
        bamboo = new Materials("bamboo", 3, -20, "worldofzuul/WorldOfZuulPNG/Items/BambooSeed.png");
        cotton = new Materials("cotton", 4, -25, "worldofzuul/WorldOfZuulPNG/Items/CottonSeed.png");
        polyester = new Materials("polyester", 5, -10, "worldofzuul/WorldOfZuulPNG/Items/Polyester.png");

        water = new Water();
        bucket = new Bucket("bucket", 7, new Room[]{farm, fabricFactory, colorFactory}, "worldofzuul/WorldOfZuulPNG/Items/Bucket.png");
        pesticides = new Pesticides("pesticides", 8, farm, "worldofzuul/WorldOfZuulPNG/Items/Pesticides.png");
        chemicals = new Chemicals("chemical", 9, new Room[]{fabricFactory, colorFactory}, "worldofzuul/WorldOfZuulPNG/Items/Chemicals.png");

        // Declaring and initializing recipes (4 per material and 3 for polyester)
        hemp.addRecipe(new Recipe(farm, 0, 0));
        hemp.addRecipe(new Recipe(fabricFactory, 0, 0));
        hemp.addRecipe(new Recipe(colorFactory, 1, 1));
        hemp.addRecipe(new Recipe(sewingFactory, 0, 0));

        linen.addRecipe(new Recipe(farm, 1, 1));
        linen.addRecipe(new Recipe(fabricFactory, 1, 1));
        linen.addRecipe(new Recipe(colorFactory, 1, 1));
        linen.addRecipe(new Recipe(sewingFactory, 0, 0));

        bamboo.addRecipe(new Recipe(farm, 2, 2));
        bamboo.addRecipe(new Recipe(fabricFactory, 1, 1));
        bamboo.addRecipe(new Recipe(colorFactory, 2, 2));
        bamboo.addRecipe(new Recipe(sewingFactory, 0, 0));

        cotton.addRecipe(new Recipe(farm, 2, 2));
        cotton.addRecipe(new Recipe(fabricFactory, 1, 1));
        cotton.addRecipe(new Recipe(colorFactory, 2, 2));
        cotton.addRecipe(new Recipe(sewingFactory, 0, 0));

        polyester.addRecipe(new Recipe(fabricFactory, 1, 1));
        polyester.addRecipe(new Recipe(colorFactory, 2, 2));
        polyester.addRecipe(new Recipe(sewingFactory, 0, 0));

        // Placing items
        materialsInventory.addToInventory(hemp);
        materialsInventory.addToInventory(linen);
        materialsInventory.addToInventory(bamboo);
        materialsInventory.addToInventory(cotton);
        materialsInventory.addToInventory(polyester);

        wellInventory.addToInventory(bucket);
        wellInventory.addToInventory(water);

        farmInventory.addToInventory(pesticides);
        factoryInventory.addToInventory(chemicals);

        mainRoom.setExit(well);
        mainRoom.setExit(farm);
        mainRoom.setExit(factory);
        mainRoom.setExit(materials);
        materials.setExit(mainRoom);
        well.setExit(mainRoom);
        farm.setExit(mainRoom);
        factory.setExit(colorFactory);
        factory.setExit(mainRoom);
        factory.setExit(sewingFactory);
        factory.setExit(fabricFactory);
        colorFactory.setExit(factory);
        sewingFactory.setExit(factory);
        fabricFactory.setExit(factory);

        currentRoom = mainRoom;

        chosenMaterial = null;
    }

    public static Room getCurrentRoom() {
        return currentRoom;
    }

    public static void setCurrentRoom(Room currentRoom) {
        Game.currentRoom = currentRoom;
    }

    // Use materials in farm
    public static boolean useItem(Item item) {
        // You cannot use anything before picking up an item
        if (chosenMaterial == null) {
            return false;
        }
        // Block of code for usage of materials
        for (Materials material : Materials.getAllMaterials()) {
            // Check if command is a material
            if (!item.equals(material)) {
                continue;
            }
            // If the material is in its final state then return
            if (chosenMaterial.getState() >= chosenMaterial.getStateNames().length-1) {
                // Player has made a T-shirt
                gameGuides = ("You've finished making a T-shirt, you can quit the game when you're ready.");
                return false;
            }
            // Check if the room and the material's state correspond
            if (!(currentRoom == chosenMaterial.getRoomToUse())) {
                Player.setPlayerThinks("I should try going to " + chosenMaterial.getRoomToUse().getName());
                return false;
            }
            // Check the materials stage
            if (chosenMaterial.getState() == 0) {
                // Plant material
                chosenMaterial.setPlanted();
                if (material.getName().equals("hemp seed")){ Player.playerScore.addToScore(-5);}
                else if (material.getName().equals("linen seed")){Player.playerScore.addToScore(-15);}
                else if (material.getName().equals("bamboo seed")){Player.playerScore.addToScore(-10);}
                else if (material.getName().equals("cotton seed")){Player.playerScore.addToScore(-20);}
            }
            // Check the materials stage
            if (chosenMaterial.getState() >= 1) {
                // Make fabric or dye fabric
                chosenMaterial.setInProcess();
                if (material.getName().equals("polyester chemicals")){Player.playerScore.addToScore(-35);}
            }
            return true;
        }

        // Check if the command is the bucket
        if (item.equals(bucket)) {
            // Check if bucket is filled with water
            if (!bucket.hasWater()) {
                Player.setPlayerThinks("I need to get " + water.getName() + " first");
                return false;
            }
            // Check if the room is a room you can use water in
            boolean canUse = false;
            for (Room room : bucket.getRoomsToUse()) {
                if (currentRoom == room) {
                    canUse = true;
                    break;
                }
            }
            if (!canUse) {
                Player.setPlayerThinks("I cannot use " + bucket.getName() + " in " + currentRoom.getName());
                return false;
            }

            // Check if the room is the farm
            if (currentRoom == bucket.getRoomsToUse()[0]) {
                // Check if something is planted
                if (!chosenMaterial.isPlanted()) {
                    Player.setPlayerThinks("I need to plant something before watering it");
                    return false;
                }
                Player.playerScore.addToScore(-5);
            }

            // Check if the room is the factory
            if (currentRoom == bucket.getRoomsToUse()[1] || currentRoom == bucket.getRoomsToUse()[2]) {
                // Pour water in the machines/filling them up in fabricFactory.
                if (!chosenMaterial.isInProcess()) {
                    return false;
                }
            }

            if (Materials.getActiveRecipe().getWater() <= 0) {
                return false;
            }
            Player.playerScore.addToScore(-5);
            chosenMaterial.decrementWater();
            bucket.setHasWater();
        }

        if (item.equals(chemicals)) {
            for (Room room : chemicals.getRoomsToUse()) {
                useChemicalsOrPesticides(chosenMaterial.isInProcess(), room);
            }
        }

        if (item.equals(pesticides)) {
            useChemicalsOrPesticides(chosenMaterial.isPlanted(), pesticides.getRoomToUse());
        }

        enoughOfEverything(chosenMaterial.isInProcess() || chosenMaterial.isPlanted());
        return false;
    }

    private static void useChemicalsOrPesticides(boolean materialsIs, Room room) {
        if (!materialsIs) {
            return;
        }
        if (currentRoom != room) {
            return;
        }
        if (Materials.getActiveRecipe().getOther() <= 0) {
            return;
        }
        chosenMaterial.decrementOther();
    }

    public static void enoughOfEverything(boolean upgradeable) {
        if (Materials.getActiveRecipe().getWater() == 0 && Materials.getActiveRecipe().getOther() == 0) {
            if (!upgradeable) {
                return;
            }
            if (chosenMaterial.isPlanted()) {
                chosenMaterial.setPlanted();
            }
            // Material is no longer in process
            if (chosenMaterial.isInProcess()) {
                chosenMaterial.setInProcess();
            }
            // Material is upgraded from one state to another

            if (chosenMaterial.getState() < chosenMaterial.getStateNames().length-1) {
                chosenMaterial.upgradeState();
                gameGuides = (chosenMaterial.getName() + " is done, you can pick it up");
            }
        }
    }

    // Pick up item method
    public static boolean getItem(Item item) {
        for (Materials material : Materials.getAllMaterials()) {
            if (!item.equals(material)) {
                continue;
            }

            if (chosenMaterial == null) {
                chosenMaterial = material;
                Materials.setActiveRecipe(chosenMaterial);
                // Remove all other materials from room
                currentRoom.getInventory().getArrayList().removeAll(currentRoom.getInventory().getArrayList());
            }

            if (material.isPlanted()) {
                material.setPlanted();
            }

            if (material.isInProcess()) {
                material.setInProcess();
            }
        }

        // Water is a special item, which can only be picked up in a bucket
        if (item.equals(water)) {
            if (!Player.getInventory().contains(bucket)) {
                Player.setPlayerThinks("I need a bucket in my inventory to get water");
                return false;
            }
            if (bucket.hasWater()) {
                Player.setPlayerThinks("I've already filled the bucket with water");
                return false;
            }
            bucket.setHasWater();
            return false;
        }
        return true;
    }

    public static String getGameGuides() {
        return gameGuides;
    }

    public static void setGameGuides(String string) {
        gameGuides = string;
    }

    public static Materials getChosenMaterial() {
        return chosenMaterial;
    }

    public static Chemicals getChemicals() {
        return chemicals;
    }

    public static Pesticides getPesticides() {
        return pesticides;
    }

    public static Room getMaterials() {
        return materials;
    }

    public static Materials getHemp() {
        return Game.hemp;
    }

    public static Materials getLinen() {
        return Game.linen;
    }

    public static Materials getBamboo() {
        return Game.bamboo;
    }

    public static Materials getCotton() {
        return Game.cotton;
    }

    public static Materials getPolyester() {
        return Game.polyester;
    }

    public static Room getWell() {
        return Game.well;
    }

    public static Room getFarm() {
        return Game.farm;
    }

    public static Room getFactory() {
        return Game.factory;
    }

    public static Room getMainRoom() {return Game.mainRoom;}

    public static Room getColorFactory() {return Game.colorFactory;}

    public static Room getSewingFactory() {return Game.sewingFactory;}


}
