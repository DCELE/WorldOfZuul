package worldofzuul;

public abstract class Item {
    private String name;
    private int id;

    public Item() {
    }

    public Item(String name, int id) {
        this.name = name;
        this.id = id;
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

    public String getOnlyNameLast() {
        String[] string = this.getName().split(" ");
        return string[string.length-1];
    }

    public String getOnlyNameFirst() {
        String[] string = this.getName().split(" ");
        return string[0];
    }

}
