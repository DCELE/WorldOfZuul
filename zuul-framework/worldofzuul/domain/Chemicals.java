package worldofzuul.domain;

public class Chemicals extends Item {

    private Room[] roomsToUseChemicals;

    public Chemicals(String name, int id, Room[] roomsToUseChemicals, String itemIcon, String description) {
        super(name, id, itemIcon, description);
        this.roomsToUseChemicals = roomsToUseChemicals;
    }

    public Chemicals(String name, int id, String itemIcon) {
        super(name, id, itemIcon);
    }
    public Room[] getRoomsToUse() {
        return roomsToUseChemicals;
    }

}

