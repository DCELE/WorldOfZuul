package worldofzuul;

public class Chemicals extends Item {

    private Room[] roomsToUseChemicals;

    public Chemicals(String name, int id, Room[] roomsToUseChemicals) {
        super(name, id);
        this.roomsToUseChemicals = roomsToUseChemicals;
    }

    public Chemicals(String name, int id) {
        super(name, id);
    }
    public Room[] getRoomsToUse() {
        return roomsToUseChemicals;
    }

}

