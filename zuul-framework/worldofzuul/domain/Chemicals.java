package worldofzuul.domain;

public class Chemicals extends Item {

    // Chemicals are usable in certain rooms
    private Room[] roomsToUseChemicals;

    public Chemicals(String name, int id, Room[] roomsToUseChemicals, String itemIcon, String description) {
        super(name, id, itemIcon, description);
        this.roomsToUseChemicals = roomsToUseChemicals;
    }

    public Room[] getRoomsToUse() {
        return roomsToUseChemicals;
    }

}

