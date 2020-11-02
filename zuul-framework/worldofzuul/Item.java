package worldofzuul;

public abstract class Item {
    private String name;
    private int id;

    public Item(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //public int nextFreeId(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
