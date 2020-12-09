package worldofzuul;

public abstract class Item {
    private String name;
    private int id;
    private String itemIcon;

    public Item() {
    }

    public Item(String name, int id, String itemIcon) {
        this.name = name;
        this.id = id;
        this.itemIcon = itemIcon;
    }

    @Override
    public String toString() {
        return name;
    }

    //public int nextFreeId(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getItemIcon() {
        return itemIcon;
    }
}
