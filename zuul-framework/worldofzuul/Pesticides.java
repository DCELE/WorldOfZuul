package worldofzuul;

public class Pesticides extends Item {
    private Room roomToUsePesticides;

    public Pesticides(String name, int id, Room roomToUsePesticides) {
        super(name, id);
        this.roomToUsePesticides = roomToUsePesticides;
    }

    public Room getRoomToUse() {
        return roomToUsePesticides;
    }
}
