package worldofzuul;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button button1, button2, button3, button4;
    @FXML
    private Label textBox;
    @FXML
    private ListView<Item> roomInventory;
    private ObservableList<Item> observRoomInventory;
    @FXML
    private ListView<Item> playerInventory;
    private ObservableList<Item> observPlayerInventory;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRoom(Room.getAllRooms().get(0));

        // Initialize playerInventory
        observPlayerInventory = FXCollections.observableArrayList(Player.getInventory().getArrayList());
        playerInventory.setItems(observPlayerInventory);
    }

    public void onNavigationButtonClicked(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        String labelText = button.getText();
        loadRoom(Room.getRoom(labelText));
    }

    // Load room everytime NavigationButton is clicked
    public void loadRoom(Room room) {
        // Set room text label

        // Set room welcome description
        setTextBox(room);

        // Set button text labels
        setNavigationButtons(room);
        // Set room inventory
        setRoomInventory(room);
        // Set currentRoom
        Game.setCurrentRoom(room);
    }

    private void setRoomInventory(Room room) {
        observRoomInventory = FXCollections.observableArrayList();
        observRoomInventory.addAll(room.getInventory().getArrayList());
        roomInventory.setItems(observRoomInventory);
    }

    public void setTextBox(Room room) {
        textBox.setText(room.getLongDescription());
    }

    public void setNavigationButtons(Room room) {
        Button[] buttons = new Button[] {button1, button2, button3, button4};
        for (Button button : buttons) {
            button.setVisible(false);
        }
        for (int i = 0; i < room.getExits().size(); i++) {
            setButton(buttons[i], room.getExits().get(i));
            buttons[i].setVisible(true);
        }
    }

    public void setButton(Button button, Room exitRoom) {
        button.setText(exitRoom.getName());
    }

    public void pickUpItem(Item item) {
        observRoomInventory.remove(item);
        observPlayerInventory.add(item);
    }

    public void dropItem(Item item) {
        observPlayerInventory.remove(item);
        observRoomInventory.add(item);
    }

    public void onPickUpButtonClicked(MouseEvent mouseEvent) {
        Item selectedItem = roomInventory.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        if (!Game.getItem(selectedItem)) {
            playerInventory.refresh();
            return;
        }
        pickUpItem(selectedItem);
    }

    public void onDropButtonClicked(MouseEvent mouseEvent) {
        Item selectedItem = playerInventory.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        Game.dropItem(selectedItem);
        dropItem(selectedItem);
    }

    public void onUseButtonClicked(MouseEvent mouseEvent) {
        Item selectedItem = playerInventory.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }

        if (!Game.useItem(selectedItem)) {
            playerInventory.refresh();
            roomInventory.refresh();
            return;
        }
        dropItem(selectedItem);
    }
}
