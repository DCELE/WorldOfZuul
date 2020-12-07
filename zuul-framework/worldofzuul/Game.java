package worldofzuul;

public class Game {
    private static Room currentRoom;
    private Materials hemp, linen, bamboo, cotton, polyester;
    private static Water water;
    private static Bucket bucket;
    private static Chemicals chemicals;
    private static Pesticides pesticides;
    private static Room mainRoom, materials, well, farm, factory, colorFactory, sewingFactory, fabricFactory;
    private static String gameGuides;
    private static ScoreSystem playerScore;

    public Game() {
        createRooms();
        new Player();

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

        //Creating scoresystem
        ScoreSystem playerScore = new ScoreSystem(100);

        // Initializing rooms
        mainRoom = new Room("Mainroom", "in the main room and can go to the other rooms from here", mainRoomInventory);
        materials = new Room("Materials", "in the material room. Here you can pick a material you want to work with", materialsInventory);
        well = new Room("Water", "in the water reservoir. If you have a bucket then you can pick up some water", wellInventory);
        farm = new Room("Farm", "in the farm. You can plant your chosen seed and grow them here", farmInventory);
        factory = new Room("Factory", "in the factory. You can process your product here", factoryInventory);
        colorFactory = new Room("Color", "in the coloring room of the factory. You can color your fabric here", colorInventory, 1);
        sewingFactory = new Room("Sewing", "in the sewing room of the factory. You can sew your fabric here", sewingInventory);
        fabricFactory = new Room("Fabric", "in the fabric room of the factory. You can make your T-shirt here", fabricInventory);

        // Initializing items
        Room[] roomsToUseItem = new Room[]{farm, fabricFactory, colorFactory, sewingFactory};
        hemp = new Materials("hemp", 1, roomsToUseItem, new int[]{1, 2, 2}, new int[]{1,1}, -5);
        linen = new Materials("linen", 2, roomsToUseItem, new int[]{2, 3, 3}, new int[]{1,1},-15);
        bamboo = new Materials("bamboo", 3, roomsToUseItem, new int[]{2, 3, 3}, new int[]{1,1},-20);
        cotton = new Materials("cotton", 4, roomsToUseItem, new int[]{2, 3, 3}, new int[]{1,1},-25);
        polyester = new Materials("polyester", 5, roomsToUseItem, new int[]{2, 3, 3}, new int[]{1,1},-10);

        water = new Water();
        bucket = new Bucket("bucket", 7, new Room[]{farm, fabricFactory, colorFactory});
        pesticides = new Pesticides("pesticides", 8);
        chemicals = new Chemicals("chemical", 9, new Room[]{fabricFactory, colorFactory});


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
    }

    public static Room getCurrentRoom() {
        return currentRoom;
    }

    public static void setCurrentRoom(Room currentRoom) {
        Game.currentRoom = currentRoom;
        if(Game.currentRoom == materials) {

        }
    }

    // Use materials in farm
    public static boolean useItem(Item item) {
        boolean nothingPlanted = true;
        Materials plantedMaterial = null;
        // Check if something is planted already in farm
        for (Materials material : Materials.getAllMaterials()) {
            if (material.isPlanted()) {
                nothingPlanted = false;
                plantedMaterial = material;
                break;
            }
        }
        // Block of code for usage of materials
        for (Materials material : Materials.getAllMaterials()) {
            // Check if command is a material
            if (!item.equals(material)) {
                continue;
            }
            // Check if the room and the material's state correspond
            if (!(currentRoom == material.getRoomsToUseItem()[material.getState()])) {
                gameGuides = ("You cannot use " + material.getName() + " in here");
                Player.setPlayerThinks("I should try going to " + material.getRoomsToUseItem()[material.getState()].getName());

                return false;
            }

            // Check the materials stage
            if (material.getState() == 0) {
                // If something is planted
                if (!nothingPlanted) {
                    Player.setPlayerThinks("It seems like a seed is already planted: " + currentRoom.getInventory());
                    return false;
                }
                // Plant material
                gameGuides = ("You plant " + material);
                if (material.getName().equals("hemp seed")){ Player.playerScore.addToScore(-5);}
                else if (material.getName().equals("linen seed")){Player.playerScore.addToScore(-15);}
                else if (material.getName().equals("bamboo seed")){Player.playerScore.addToScore(-10);}
                else if (material.getName().equals("cotton seed")){Player.playerScore.addToScore(-20);}
                Player.dropItem(item);
                material.setPlanted();
                Player.setPlayerThinks("It needs water: " + material.getWaterAmountNeeded()[0] + " time(s)");
                return true;
            }
            // Check the materials stage
            if (material.getState() == 1) {
                // If something is already being processed.
                // Make fabric
                if (material.getName().equals("polyester chemicals")){Player.playerScore.addToScore(-35);}
                material.setInProcess();
                Player.dropItem(item);
                Player.setPlayerThinks("It needs water: " + material.getWaterAmountNeeded()[1] + " time(s)");
                System.out.println("It needs water: " + material.getWaterAmountNeeded()[1] + " time(s)");
                System.out.println("It needs chemicals: " + material.getChemicalsAmountNeeded()[1] + " time(s)");
                return true;
            }
            // Check the materials stage
            if (material.getState() == 2) {
                // Dye fabric
                material.setInProcess();
                Player.dropItem(item);
                Player.setPlayerThinks("It needs water: " + material.getWaterAmountNeeded()[2] + " time(s)");
                return true;
            }
            // Check the materials stage
            if (material.getState() == 3) {
                // Make T-shirt
                gameGuides = ("You're sewing " + material + " into a T-shirt with the color " + material.getColor());
                material.upgradeState();
                Player.dropItem(item);
                gameGuides = ("You've finished making a T-shirt, Type: \"quit\" to exit the game.");
                return true;
            }
        }

        // Check if the command is the bucket
        if (item.equals(bucket)) {
            // Check if bucket is filled with water
            if (!bucket.hasWater()) {
                Player.setPlayerThinks("I need to get " + water.getName() + " first");
                return false;
            }
            // Check if the room is a room you can use water in
            boolean canUseBucket = false;
            for (Room room : bucket.getRoomsToUseBucket()) {
                if (currentRoom == room) {
                    canUseBucket = true;
                    break;
                }
            }
            if (!canUseBucket) {
                Player.setPlayerThinks("I cannot use " + bucket.getName() + " in " + currentRoom.getName());
                return false;
            }
            // Check if the room is the farm
            if (currentRoom == bucket.getRoomsToUseBucket()[0]) {
                // Check if something is planted
                if (plantedMaterial == null) {
                    Player.setPlayerThinks("I need to plant something before watering it");
                    return false;
                }
                // Watering seed
                plantedMaterial.decrementAmountNeeded(plantedMaterial.getWaterAmountNeeded(), 0);
                System.out.println("You water " + plantedMaterial.getName());
                Player.playerScore.addToScore(-5);
                bucket.setHasWater();

                // Check if seed has fully grown
                if (plantedMaterial.getWaterAmountNeeded()[0] == 0) {
                    Player.setPlayerThinks("It needs water: " + plantedMaterial.getWaterAmountNeeded()[0] + " time(s) more");
                    // Material is no longer planted
                    plantedMaterial.setPlanted();
                    // Seed becomes plant
                    plantedMaterial.upgradeState();
                    gameGuides = (plantedMaterial.getName() + " is fully grown, you can pick it up");
                    return false;
                }
                Player.setPlayerThinks("It needs water: " + plantedMaterial.getWaterAmountNeeded()[0] + " time(s) more");
                return false;
            }
            // Check if the room is the factory
            if (currentRoom == bucket.getRoomsToUseBucket()[1]) {
                // Pour water in the machines/filling them up in fabricFactory.
                Materials materialInProcess = null;
                for (Materials material : Materials.getAllMaterials()) {
                    if (material.isInProcess()) {
                        materialInProcess = material;
                        break;
                    }
                }
                if (materialInProcess == null) return false;
                materialInProcess.decrementAmountNeeded(materialInProcess.getWaterAmountNeeded(),1);
                Player.playerScore.addToScore(-5);
                System.out.println("You pour water into the machine with " + materialInProcess.getName() + " in it");
                bucket.setHasWater();

                if (materialInProcess.getWaterAmountNeeded()[1] == 0) {
                    // Material is no longer in process
                    materialInProcess.setInProcess();
                    // Plant becomes fabric
                    gameGuides = ("You use machines to make fabric of " + materialInProcess);
                    materialInProcess.upgradeState();
                    gameGuides = (materialInProcess.getName() + " is done, you can pick it up");
                    return false;
                }
                Player.setPlayerThinks("It needs water: " + materialInProcess.getWaterAmountNeeded()[1] + " time(s) more");
                return false;
            }

            if (currentRoom == bucket.getRoomsToUseBucket()[2]) {
                // Pour water in the machines/filling them up in colorFactory.
                Materials materialInProcess = null;
                for (Materials material : Materials.getAllMaterials()) {
                    if (material.isInProcess()) {
                        materialInProcess = material;
                        break;
                    }
                }
                if (materialInProcess == null) return false;
                materialInProcess.decrementAmountNeeded(materialInProcess.getWaterAmountNeeded(),2);
                Player.playerScore.addToScore(-5);
                System.out.println("You pour water into the machine with " + materialInProcess.getName() + " in it");
                bucket.setHasWater();

                if (materialInProcess.getWaterAmountNeeded()[2] == 0) {
                    // Material is no longer in process
                    materialInProcess.setInProcess();
                    // fabric becomes dyed fabric
                    gameGuides = ("You dye " + materialInProcess + " into the color " + materialInProcess.getColor());
                    materialInProcess.upgradeState();
                    gameGuides = (materialInProcess.getName() + " is done, you can pick it up");
                    return false;
                }

                Player.setPlayerThinks("It needs water: " + materialInProcess.getWaterAmountNeeded()[2] + " time(s) more");
                return false;
            }
        }

        //Use chemicals on fabric and color machine
        if (item.equals(chemicals))
        {
            Materials materialInProcessChemical = null;
            for (Materials material : Materials.getAllMaterials()) {
                if (material.isInProcess()) {
                    materialInProcessChemical = material;
                    break;
                }
            }
            if (materialInProcessChemical == null) return false;
            if (currentRoom == chemicals.getRoomsToUseChemicals()[0])
            {
                materialInProcessChemical.decrementAmountNeeded(materialInProcessChemical.getChemicalsAmountNeeded(),0);
                System.out.println("You pour chemicals into the fabric machine, you need " + materialInProcessChemical.getChemicalsAmountNeeded()[0] + " chemical(s)");



            }

            if (currentRoom == chemicals.getRoomsToUseChemicals()[1])
            {
                materialInProcessChemical.decrementAmountNeeded(materialInProcessChemical.getChemicalsAmountNeeded(),0);
                System.out.println("Your pour chemicals into the color machine, you need " + materialInProcessChemical.getChemicalsAmountNeeded()[0] + " chemical(s)");

            }

            if (!(currentRoom == chemicals.getRoomsToUseChemicals()[0]||currentRoom == chemicals.getRoomsToUseChemicals()[1]))
            {
                System.out.println("You can't use chemicals in this room");
            }


        }

        return false;
    }


    // Pick up item method
    public static boolean getItem(Item item) {
        for (Materials material : Materials.getAllMaterials()) {
            if (!item.equals(material)) {
                continue;
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
            gameGuides = ("You fill your bucket with water");
            return false;
        }
        // If material is planted, material is not planted or its just an ordinary item pick it up
        Player.pickUpItem(item);
        gameGuides = ("You pick up: " + item);
        return true;
    }


    // Drop item method
    public static void dropItem(Item item) {
        Player.dropItem(item);
    }

    public static String getGameGuides() {
        return gameGuides;
    }

    public static Room getMaterials() {
        return materials;
    }
}
