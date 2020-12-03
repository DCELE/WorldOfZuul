package worldofzuul;

public class Game {
    private static Room currentRoom;
    private Materials hemp, linen, bamboo, cotton, polyester;
    private static Water water;
    private static Bucket bucket;
    private static Chemicals chemicals;
    private static Pesticides pesticides;
    private static Materials chosenMaterial;
    private Room mainRoom, materials, well, farm, factory, colorFactory, sewingFactory, fabricFactory;
    private static String gameGuides;

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

        // Initializing rooms
        mainRoom = new Room("Mainroom", "in the main room and can go to the other rooms from here", mainRoomInventory);
        materials = new Room("Materials", "in the material room. Here you can pick a material you want to work with", materialsInventory);
        well = new Room("Water", "in the water reservoir. If you have a bucket then you can pick up some water", wellInventory);
        farm = new Room("Farm", "in the farm. You can plant your chosen seed and grow them here", farmInventory);
        factory = new Room("Factory", "in the factory. You can process your product here", factoryInventory);
        colorFactory = new Room("Color", "in the coloring room of the factory. You can color your fabric here", colorInventory);
        sewingFactory = new Room("Sewing", "in the sewing room of the factory. You can sew your fabric here", sewingInventory);
        fabricFactory = new Room("Fabric", "in the fabric room of the factory. You can make your T-shirt here", fabricInventory);

        // Initializing items
        hemp = new Materials("hemp", 1);
        linen = new Materials("linen", 2);
        bamboo = new Materials("bamboo", 3);
        cotton = new Materials("cotton", 4);
        polyester = new Materials("polyester", 5);

        water = new Water();
        bucket = new Bucket("bucket", 7, new Room[]{farm, fabricFactory, colorFactory});
        pesticides = new Pesticides("pesticides", 8, farm);
        chemicals = new Chemicals("chemical", 9, new Room[]{fabricFactory, colorFactory});

        // Declaring and initializing recipes (4 per material and 3 for polyester)
        hemp.addRecipe(new Recipe(farm, 0, 0));
        hemp.addRecipe(new Recipe(fabricFactory, 0, 0));
        hemp.addRecipe(new Recipe(colorFactory, 0, 0));
        hemp.addRecipe(new Recipe(sewingFactory, 0, 0));

        linen.addRecipe(new Recipe(farm, 2, 2));
        linen.addRecipe(new Recipe(fabricFactory, 1, 1));
        linen.addRecipe(new Recipe(colorFactory, 2, 2));
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
        // Block of code for usage of materials
        for (Materials material : Materials.getAllMaterials()) {
            // Check if command is a material
            if (!item.equals(material)) {
                continue;
            }
            // Check if the room and the material's state correspond
            if (!(currentRoom == chosenMaterial.getRoomToUseItem())) {
                gameGuides = ("You cannot use " + chosenMaterial.getName() + " in here");
                Player.setPlayerThinks("I should try going to " + chosenMaterial.getRoomToUseItem().getName());
                return false;
            }

            // Check the materials stage
            if (chosenMaterial.getState() == 0) {
                // If something is planted
                if (chosenMaterial.isPlanted()) {
                    Player.setPlayerThinks("It seems like a seed is already planted: " + currentRoom.getInventory());
                    return false;
                }
                // Plant material
                Player.dropItem(item);
                chosenMaterial.setPlanted();
                enoughOfEverything();
                return true;
            }
            // Check the materials stage
            if (chosenMaterial.getState() == 1 || chosenMaterial.getState() == 2) {
                // Make fabric or dye fabric
                Player.dropItem(item);
                chosenMaterial.setInProcess();
                enoughOfEverything();
                return true;
            }
            // Check the materials stage
            if (chosenMaterial.getState() == 3) {
                // Make T-shirt
                Player.dropItem(item);
                enoughOfEverything();
                gameGuides = ("You've finished making a T-shirt, you can quit the game when you're ready.");
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
                if (!chosenMaterial.isPlanted()) {
                    Player.setPlayerThinks("I need to plant something before watering it");
                    return false;
                }
            }

            // Check if the room is the factory
            if (currentRoom == bucket.getRoomsToUseBucket()[1] || currentRoom == bucket.getRoomsToUseBucket()[2]) {
                // Pour water in the machines/filling them up in fabricFactory.
                if (!chosenMaterial.isInProcess()) {
                    return false;
                }
            }

            if (Materials.getActiveRecipe().getWater() <= 0) {
                return false;
            }
            chosenMaterial.decrementWater();
            bucket.setHasWater();
            enoughOfEverything();
            return false;
        }

        if (item.equals(pesticides)) {
            if (currentRoom != pesticides.getRoomToUsePesticides()) {
                System.out.println("You can't use " + item + " in this room");
                return false;
            }

            if (!chosenMaterial.isPlanted()) {
                return false;
            }

            if (Materials.getActiveRecipe().getOther() <= 0) {
                return false;
            }

            chosenMaterial.decrementOther();
            if (Materials.getActiveRecipe().getOther() == 0) {
                enoughOfEverything();
            }

        }

        //Use chemicals on fabric and color machine
        if (item.equals(chemicals)) {
            if (!(currentRoom == chemicals.getRoomsToUseChemicals()[0] || currentRoom == chemicals.getRoomsToUseChemicals()[1])) {
                System.out.println("You can't use " + item + " in this room");
            }

            if (!chosenMaterial.isInProcess()) {
                return false;
            }

            if (currentRoom == chemicals.getRoomsToUseChemicals()[0]) {
                if (Materials.getActiveRecipe().getOther() <= 0) {
                    return false;
                }
                chosenMaterial.decrementOther();

                if (Materials.getActiveRecipe().getOther() == 0) {
                    enoughOfEverything();
                }
            }

            if (currentRoom == chemicals.getRoomsToUseChemicals()[1]) {
                if (Materials.getActiveRecipe().getOther() <= 0) {
                    return false;
                }
                chosenMaterial.decrementOther();

                if (Materials.getActiveRecipe().getOther() == 0) {
                    enoughOfEverything();
                }
            }
        }
        return false;
    }

    private static void enoughOfEverything() {
        if (Materials.getActiveRecipe().getWater() == 0 && Materials.getActiveRecipe().getOther() == 0) {
            if (chosenMaterial.isPlanted()) {
                chosenMaterial.setPlanted();
            }
            // Material is no longer in process
            if (chosenMaterial.isInProcess()) {
                chosenMaterial.setInProcess();
            }
            // Plant becomes fabric
            chosenMaterial.upgradeState();
            gameGuides = (chosenMaterial.getName() + " is done, you can pick it up");
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

    public static Materials getChosenMaterial() {
        return chosenMaterial;
    }

    public static Chemicals getChemicals() {
        return chemicals;
    }

    public static Pesticides getPesticides() {
        return pesticides;
    }
}
