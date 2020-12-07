package worldofzuul;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private VBox pickMaterialColor;
    private ToggleGroup pickColor;
    @FXML
    private Label hintLabel;
    @FXML
    private VBox vBoxPlayerInventory, vBoxRoomInventory;
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
        // Reset room description
        Game.setGameGuides(null);
        Player.setPlayerThinks(null);
        // Set room welcome description
        setTextBox(room);
        // Set button text labels
        setNavigationButtons(room);
        // Set room inventory
        setRoomInventory(room);
        // Set currentRoom
        Game.setCurrentRoom(room);
        // Set hintBox
        setHintLabel();
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
        Player.pickUpItem(item);
        setRoomInventory(Game.getCurrentRoom());
        setHintLabel();
    }

    public void dropItem(Item item) {
        observPlayerInventory.remove(item);
        observRoomInventory.add(item);
        Player.dropItem(item);
        setHintLabel();
    }

    public void onPickUpButtonClicked(MouseEvent mouseEvent) {
        Item selectedItem = roomInventory.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            setTextBox(Game.getCurrentRoom());
            return;
        }
        if (!Game.getItem(selectedItem)) {
            playerInventory.refresh();
            setTextBox(Game.getCurrentRoom());
            return;
        }
        pickUpItem(selectedItem);
        setTextBox(Game.getCurrentRoom());
    }

    public void onDropButtonClicked(MouseEvent mouseEvent) {
        Item selectedItem = playerInventory.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            setTextBox(Game.getCurrentRoom());
            return;
        }
        dropItem(selectedItem);
        setTextBox(Game.getCurrentRoom());
    }

    public void onUseButtonClicked(MouseEvent mouseEvent) {
        Item selectedItem = playerInventory.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            setTextBox(Game.getCurrentRoom());
            return;
        }

        if (!Game.useItem(selectedItem)) {
            playerInventory.refresh();
            roomInventory.refresh();
            setTextBox(Game.getCurrentRoom());
            setHintLabel();
            return;
        }
        if (Game.getChosenMaterial().getState() == 2 && Game.getChosenMaterial().isInProcess()) {
            chooseColor();
        }
        dropItem(selectedItem);
        // Check if the material should be upgraded
        Game.enoughOfEverything(Game.getChosenMaterial().isInProcess() || Game.getChosenMaterial().isPlanted());
        setTextBox(Game.getCurrentRoom());
    }

    private void chooseColor() {
        pickColor = new ToggleGroup();
        //Stage stage = new Stage();
        //Parent root = new FXMLLoader();
        //Scene scene = new Scene();

        Label label = new Label("Choose a color");
        pickMaterialColor.getChildren().add(label);
        for (String color : Game.getChosenMaterial().getAllColors()) {
            RadioButton radioButton = new RadioButton(color);
            radioButton.setToggleGroup(pickColor);
            pickMaterialColor.getChildren().add(radioButton);
        }
        Button acceptButton = new Button("Accept");
        acceptButton.setOnMouseClicked(this::acceptChosenColor);
        pickMaterialColor.getChildren().add(acceptButton);
        pickMaterialColor.setVisible(true);
    }

    public void acceptChosenColor(MouseEvent mouseEvent) {
        RadioButton radioButton = (RadioButton) pickColor.getSelectedToggle();
        String color = radioButton.getText();
        Game.getChosenMaterial().setColor(color);

        pickMaterialColor.setVisible(false);
        roomInventory.refresh();
    }

    public void openInventory(MouseEvent mouseEvent) {
        boolean setVisibility = !vBoxPlayerInventory.isVisible();
        vBoxPlayerInventory.setVisible(setVisibility);
        vBoxRoomInventory.setVisible(setVisibility);
    }

    public void setHintLabel() {
        hintLabel.setText("Pick up a material \nto begin your journey");
        if (Game.getChosenMaterial() == null ) {
            return;
        }
        hintLabel.setText(Materials.getActiveRecipe().toString());

        if (Game.getChosenMaterial().getRecipes().size() == Game.getChosenMaterial().getState()) {
            hintLabel.setText("You've come to \nthe end of your journey. \nThank you for playing \nour game \n- T6-1");
        }
    }

    public void onHintClicked(MouseEvent mouseEvent) {
        boolean labelVisibility = hintLabel.isVisible();
        hintLabel.setVisible(!labelVisibility);
        setHintLabel();
    }
}
