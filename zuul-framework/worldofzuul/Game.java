package worldofzuul;

public class Game {
    private static Room currentRoom;
    private Materials hemp, linen, bamboo, cotton, polyester;
    private static Water water;
    private static Bucket bucket;
    private static Chemicals chemicals;
    private static Pesticides pesticides;
    private Room mainRoom, materials, well, farm, factory, colorFactory, sewingFactory, fabricFactory;


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
        hemp = new Materials("hemp", 1, roomsToUseItem, new int[]{1, 2});
        linen = new Materials("linen", 2, roomsToUseItem, new int[]{2, 3});
        bamboo = new Materials("bamboo", 3, roomsToUseItem, new int[]{2, 3});
        cotton = new Materials("cotton", 4, roomsToUseItem, new int[]{2, 3});
        polyester = new Materials("polyester", 5, roomsToUseItem, new int[]{2, 3});

        water = new Water();
        bucket = new Bucket("bucket", 7, new Room[]{farm, factory});

        chemicals = new Chemicals("chemicals", 8);
        pesticides = new Pesticides("pesticides", 9);

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
                System.out.println("You cannot use " + material.getName() + " in here");
                System.out.println("Try going to " + material.getRoomsToUseItem()[material.getState()].getName());
                return false;
            }

            // Check the materials stage
            if (material.getState() == 0) {
                // If something is planted
                if (!nothingPlanted) {
                    System.out.println("A seed is already planted: " + currentRoom.getInventory());
                    return false;
                }
                // Plant material
                System.out.println("You plant " + material);
                Player.dropItem(item);
                material.setPlanted();
                System.out.println("It needs water: " + material.getWaterAmountNeeded()[0] + " time(s)");
                return true;
            }
            // Check the materials stage
            if (material.getState() == 1) {
                // Make fabric
                System.out.println("You use machines to make fabric of " + material);
                material.upgradeState();
                System.out.println("It needs water: " + material.getWaterAmountNeeded()[1] + " time(s)");
                Player.dropItem(item);
                return true;
            }
            // Check the materials stage
            if (material.getState() == 2) {
                // Dye fabric
                System.out.println("You dye " + material + " into the color " + material.getColor());
                material.upgradeState();
                Player.dropItem(item);
                return true;
            }
            // Check the materials stage
            if (material.getState() == 3) {
                // Make T-shirt
                System.out.println("You're sewing " + material + " into a T-shirt with the color " + material.getColor());
                material.upgradeState();
                Player.dropItem(item);
                System.out.println("You've finished making a T-shirt, Type: \"quit\" to exit the game.");
                return true;
            }
        }

        // Check if the command is the bucket
        if (item.equals(bucket)) {
            // Check if bucket is filled with water
            if (!bucket.hasWater()) {
                System.out.println("You need to get " + water.getName() + " first");
                return false;
            }
            // Check if the room is a room you can use water in
            if (!(currentRoom == bucket.getRoomsToUseBucket()[0] || currentRoom == bucket.getRoomsToUseBucket()[1])) {
                System.out.println("You cannot use " + bucket.getName() + " in " + currentRoom.getName());
                return false;
            }
            // Check if the room is the farm
            if (currentRoom == bucket.getRoomsToUseBucket()[0]) {
                // Check if something is planted
                if (plantedMaterial == null) {
                    System.out.println("You need to plant something before watering it");
                    return false;
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
                    return false;
                }
                System.out.println("It needs water: " + plantedMaterial.getWaterAmountNeeded()[0] + " time(s) more");
                return false;
            }
            // Check if the room is the factory
            if (currentRoom == bucket.getRoomsToUseBucket()[1]) {
                // Pour water in the machines/filling them up.

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
        }

        // Water is a special item, which can only be picked up in a bucket
        if (item.equals(water)) {
            if (!Player.getInventory().contains(bucket)) {
                System.out.println("You need a bucket in your inventory to get water");
                return false;
            }
            if (bucket.hasWater()) {
                System.out.println("You've already filled your bucket with water");
                return false;
            }
            bucket.setHasWater();
            System.out.println("You fill your bucket with water");
            return false;
        }
        // If material is planted, material is not planted or its just an ordinary item pick it up
        Player.pickUpItem(item);
        System.out.println("You pick up: " + item);
        return true;
    }


    // Drop item method
    public static void dropItem(Item item) {
        Player.dropItem(item);
    }


    public static void adjustItemName(Item item) {

    }

}
