package worldofzuul.domain;

public class Pesticides extends Item {
    // Pesticides are only usable in one room
    private Room roomToUsePesticides;

    public Pesticides(String name, int id, Room roomToUsePesticides, String itemIcon, String description) {
        super(name, id, itemIcon, description);
        this.roomToUsePesticides = roomToUsePesticides;
    }

    public Room getRoomToUse() {
        return roomToUsePesticides;
    }
}
