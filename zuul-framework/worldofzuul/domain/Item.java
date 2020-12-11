package worldofzuul.domain;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public abstract class Item {
    private String name;
    private int id;
    private Image itemIcon;
    private String description;

    public Item(String description) {
        this.description = description;
    }

    public Item(String name, int id, String itemIcon, String description) {
        this.name = name;
        this.id = id;
        this.description = description;
        // Try to find the image at the URL
        try {
            this.itemIcon = new Image(new FileInputStream(itemIcon));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            this.itemIcon = null;
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Image getItemIcon() {
        return itemIcon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Used to set a new Image for the item by sending an URL with the method
    public void setItemIcon(String itemIcon) {
        try {
            this.itemIcon = new Image(new FileInputStream(itemIcon));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            this.itemIcon = null;
        }
    }
}
