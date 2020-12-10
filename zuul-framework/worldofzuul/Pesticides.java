package worldofzuul;

public class Pesticides extends Item {
    private Room roomToUsePesticides;

    public Pesticides(String name, int id, Room roomToUsePesticides, String itemIcon, String description) {
        super(name, id, itemIcon, description);
        this.roomToUsePesticides = roomToUsePesticides;
    }

    public Room getRoomToUse() {
        return roomToUsePesticides;
    }
}
