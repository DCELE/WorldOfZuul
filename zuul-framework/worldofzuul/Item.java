package worldofzuul;

public abstract class Item {
    private String name;
    private int id;

    public Item() {
    }

    public Item(String name, int id)
    {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString()
    {
        return name;
    }

    //public int nextFreeId(){}

    public String getName()
    {
        return name;
    }

    public Item getItem(String name)
    {
        return Item.this;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
