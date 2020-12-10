package worldofzuul;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public ImageView hintImage;
    @FXML
    private ImageView acceptPickUp, denyPickUp;
    @FXML
    private Label itemDescription;
    @FXML
    private VBox pickUpQuestion;
    @FXML
    private ImageView roomInventoryItem1, roomInventoryItem2, roomInventoryItem3, roomInventoryItem4, roomInventoryItem5, roomInventoryItem6;
    @FXML
    private Pane playInvPane;
    @FXML
    private ImageView playerInventoryItem1, playerInventoryItem2, playerInventoryItem3, playerInventoryItem4, playerInventoryItem5, playerInventoryItem6, playerInventoryItem7, playerInventoryItem8, playerInventoryItem9;
    @FXML
    private ImageView backgroundImage;
    private VBox pickMaterialColor;
    private ToggleGroup pickColor;
    @FXML
    private Label hintLabel;
    @FXML
    public Pane PaneShowHelp;
    @FXML
    private Button button1, button2, button3, button4;
    @FXML
    private Label textBox, textBox1, textBox2, textBox3, textBox4, textBox5;
    @FXML
    private Label scoreboard;
    @FXML
    private FlowPane roomInventory;
    @FXML
    private Pane prosConsPanel, prosConsPanel1;
    private worldofzuul.Item selectedItemPlayInv, selectedItemRoomInv, itemSelectedOnce = null;;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRoom(Room.getAllRooms().get(0));
    }

    public void setInventory(Inventory inventory) {
        ImageView[] inventorySlots = new ImageView[]{roomInventoryItem1, roomInventoryItem2, roomInventoryItem3, roomInventoryItem4, roomInventoryItem5, roomInventoryItem6};

        if (inventory == Player.getInventory()) {
            inventorySlots = new ImageView[]{playerInventoryItem1, playerInventoryItem2, playerInventoryItem3, playerInventoryItem4, playerInventoryItem5, playerInventoryItem6, playerInventoryItem7, playerInventoryItem8, playerInventoryItem9};
        }

        for (ImageView imageView : inventorySlots) {
            imageView.setDisable(true);
            imageView.setImage(null);
        }

        int i = 0;
        for (Item item : inventory.getArrayList()) {
            inventorySlots[i].setImage(item.getItemIcon());
            inventorySlots[i].setDisable(false);
            i++;
        }
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
        // Reset selected item
        selectedItemRoomInv = null;
        askToPickUp(null);
        // Set room welcome description
        setTextBox(room);

        setScoreboard(room);
        // Set button text labels
        setNavigationButtons(room);
        // Set room inventory
        setRoomInventory(room);
        // Set currentRoom
        Game.setCurrentRoom(room);
        // Set hintBox
        setHintLabel();
        // Set background
        setBackgroundImage(room);

        prosConsPanel.setVisible(false);
        prosConsPanel1.setVisible(false);
        if (Game.getCurrentRoom() == Game.getMaterials()) {
            if (Game.getChosenMaterial() == null) {
                prosConsPanel.setVisible(true);
                prosConsPanel1.setVisible(true);
            }
        }
    }

    private Image getImage(String url) {
        Image image;
        try {
            image = new Image(new FileInputStream(url));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return image;
    }

    private void setBackgroundImage(Room room) {
        backgroundImage.setImage(getImage(room.getBackgroundImage()));
    }

    private void setRoomInventory(Room room) {
        setInventory(room.getInventory());
    }

    public void setTextBox(Room room) {
        textBox.setText(room.getLongDescription());
    }

    public void setNavigationButtons(Room room) {
        Button[] buttons = new Button[]{button1, button2, button3, button4};
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
        if (!Game.getCurrentRoom().getInventory().getArrayList().contains(item)) {
            return;
        }
        Player.pickUpItem(item);
        setInventory(Game.getCurrentRoom().getInventory());
        setInventory(Player.getInventory());
        setRoomInventory(Game.getCurrentRoom());
        setHintLabel();
    }

    public void dropItem(Item item) {
        if (!Player.getInventory().getArrayList().contains(item)) {
            return;
        }
        Player.dropItem(item);
        setInventory(Game.getCurrentRoom().getInventory());
        setInventory(Player.getInventory());
        setHintLabel();
    }

    public void onPickUpButtonClicked(MouseEvent mouseEvent) {
        if (selectedItemRoomInv == null) {
            setTextBox(Game.getCurrentRoom());
            return;
        }
        if (!Game.getItem(selectedItemRoomInv)) {
            setInventory(Player.getInventory());
            setTextBox(Game.getCurrentRoom());
            selectedItemRoomInv = null;
            askToPickUp(null);
            return;
        }
        pickUpItem(selectedItemRoomInv);
        setTextBox(Game.getCurrentRoom());
        selectedItemRoomInv = null;
        askToPickUp(null);
    }

    public void onDropButtonClicked(MouseEvent mouseEvent) {
        if (selectedItemPlayInv == null) {
            setTextBox(Game.getCurrentRoom());
            return;
        }
        dropItem(selectedItemPlayInv);
        setTextBox(Game.getCurrentRoom());
    }

    public void onUseButtonClicked(MouseEvent mouseEvent) {
        if (selectedItemPlayInv == null) {
            setTextBox(Game.getCurrentRoom());
            return;
        }

        if (!Game.useItem(selectedItemPlayInv)) {
            setInventory(Player.getInventory());
            setInventory(Game.getCurrentRoom().getInventory());
            setTextBox(Game.getCurrentRoom());
            setHintLabel();
            return;
        }
        if (Game.getChosenMaterial().getState() == 2 && Game.getChosenMaterial().isInProcess()) {
            chooseColor();
        }
        dropItem(selectedItemPlayInv);
        // Check if the material should be upgraded
        Game.enoughOfEverything(Game.getChosenMaterial().isInProcess() || Game.getChosenMaterial().isPlanted());
        setInventory(Game.getCurrentRoom().getInventory());
        setTextBox(Game.getCurrentRoom());
    }


    private void chooseColor() {
        pickColor = new ToggleGroup();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Choose a color");
        stage.initStyle(StageStyle.UNDECORATED);

        pickMaterialColor = new VBox();
        Label label = new Label("Choose a color");
        pickMaterialColor.getChildren().add(label);
        pickMaterialColor.setStyle("-fx-background-color: lightgrey; " + "-fx-border-color: black; " + "-fx-padding: 5");
        pickMaterialColor.setSpacing(5);

        for (String color : Game.getChosenMaterial().getAllColors()) {
            RadioButton radioButton = new RadioButton(color);
            radioButton.setToggleGroup(pickColor);
            pickMaterialColor.getChildren().add(radioButton);
        }

        Button acceptButton = new Button("Accept");
        acceptButton.setOnMouseClicked(this::acceptChosenColor);
        pickMaterialColor.getChildren().add(acceptButton);

        Scene scene = new Scene(pickMaterialColor);
        stage.setScene(scene);
        stage.show();
    }

    public void acceptChosenColor(MouseEvent mouseEvent) {
        RadioButton radioButton = (RadioButton) pickColor.getSelectedToggle();
        if (radioButton == null) {
            return;
        }
        String color = radioButton.getText();
        Game.getChosenMaterial().setColor(color);
        setHintLabel();
        Game.enoughOfEverything(Game.getChosenMaterial().isInProcess() || Game.getChosenMaterial().isPlanted());
        setInventory(Game.getCurrentRoom().getInventory());

        Stage stage = (Stage) pickMaterialColor.getScene().getWindow();
        stage.close();
    }

    public void materialClicked(MouseEvent mouseEvent) {

        //if (room.getName().equals("materials")) {
        if (selectedItemRoomInv == null) {
            return;
        }
        textBox1.setText(selectedItemRoomInv.getName());
        textBox4.setText("Pros");
        textBox5.setText("Cons");

        if (selectedItemRoomInv == Game.getHemp()) {
            textBox2.setText("Low use of pesticides");
            textBox3.setText("Large water consumption\n - 650L per t-shirt\nGo through chemical process");
        }
        if (selectedItemRoomInv == Game.getLinen()) {
            textBox2.setText("Easy degradable in the nature\nLow water consumption\n - 6.4L per t-shirt \nLow use of pesticides");
            textBox3.setText("Creases easily\nGo through chemical process");
        }
        if (selectedItemRoomInv == Game.getBamboo()) {
            textBox2.setText("Fast-growing\nProduces a lot of oxygen per hectare\nAbsorbs up to 12 ton carbon dioxide\n - 2 tons more than the average per hectare\nLow water consumption");
            textBox3.setText("Primarily grown in China\n - Low requirements in proportion to pollution\nGo through chemical process");
        }
        if (selectedItemRoomInv == Game.getCotton()) {
            textBox2.setText("Lightweight and soft\n");
            textBox3.setText("Large water consumption\n - 2700L per t-shirt\nGrows best in countries with a low rainfall\nGo through chemical process");
        }
        if (selectedItemRoomInv == Game.getPolyester()) {
            textBox2.setText("Hard-wearing\nDries fast\nCreases not easily\nCheap");
            textBox3.setText("Based on oil and natural gas\nUse of Carcinogen\n - Chemical in benzene and asbestos\n - Cancer-causing & harmful to the environment\nProduced in countries with low requirements to pollution\n - eg China, Bangladesh and Indonesia");
        }

    }

    public void openInventory(MouseEvent mouseEvent) {
        boolean setVisibility = !playInvPane.isVisible();
        playInvPane.setVisible(setVisibility);
    }

    public void setHintLabel() {
        hintLabel.setText("Pick up a material \nto begin your journey");
        if (Game.getChosenMaterial() == null) {
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
        hintImage.setVisible(!labelVisibility);
        setHintLabel();
    }

    public void setScoreboard(Room room) {
        scoreboard.setText(String.valueOf("Current score: " + Player.playerScore.getScore()));
    }

    public void showHelp(MouseEvent mouseEvent) {
        boolean setVisibility = !PaneShowHelp.isVisible();
        PaneShowHelp.setVisible(setVisibility);
    }

    public void onItemInPlayInvClicked(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        selectedItemPlayInv = selectItem(imageView.getImage(), Player.getInventory());
    }

    public void onItemInRoomInvClicked(MouseEvent mouseEvent) {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        selectedItemRoomInv = selectItem(imageView.getImage(), Game.getCurrentRoom().getInventory());

        if (selectedItemRoomInv == itemSelectedOnce) {
            selectedItemRoomInv = null;
        }
        askToPickUp(selectedItemRoomInv);
    }

    private void askToPickUp(Item item) {
        itemSelectedOnce = selectedItemRoomInv;
        if (item == null) {
            pickUpQuestion.setVisible(false);
            return;
        }
        itemDescription.setText(item.getDescription());
        pickUpQuestion.setVisible(true);
    }

    public Item selectItem(Image image, Inventory inventory) {
        for (Item item : inventory.getArrayList()) {
            if (image == item.getItemIcon()) {
                return item;
            }
        }
        return null;
    }

    public void onAcceptPickUp(MouseEvent mouseEvent) {
        onPickUpButtonClicked(mouseEvent);
        pickUpQuestion.setVisible(false);
    }

    public void onDenyPickUp(MouseEvent mouseEvent) {
        pickUpQuestion.setVisible(false);
    }

}
