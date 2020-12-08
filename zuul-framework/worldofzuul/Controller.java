package worldofzuul;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private VBox pickMaterialColor;
    private ToggleGroup pickColor;
    @FXML
    private Label hintLabel;
    @FXML
    private VBox vBoxPlayerInventory, vBoxRoomInventory;
    @FXML
    public Pane PaneShowHelp;
    @FXML
    private Button button1, button2, button3, button4;
    @FXML
    private Label textBox, textBox1, textBox2, textBox3, textBox4, textBox5;
    @FXML
    private Label scoreboard;
    @FXML
    private ListView<Item> roomInventory;
    private ObservableList<Item> observRoomInventory;
    @FXML
    private ListView<Item> playerInventory;
    private ObservableList<Item> observPlayerInventory;
    @FXML
    private Pane prosConsPanel, prosConsPanel1;


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

    private void setBackgroundImage(Room room) {
        //Image image = new Image("worldofzuul/WorldOfZuulPNG/Rooms/Mainroom.png");
        //Image image = new Image(room.getBackgroundImage());

        //GridPane gridPane = (GridPane) Main.getScene().getRoot();

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
        roomInventory.refresh();

        Stage stage = (Stage) pickMaterialColor.getScene().getWindow();
        stage.close();
    }

    public void materialClicked(MouseEvent mouseEvent) {

        //if (room.getName().equals("materials")) {

        Item selectedItem = roomInventory.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        textBox1.setText(selectedItem.getName());
        textBox4.setText("Pros");
        textBox5.setText("Cons");


        if (selectedItem == Game.getHemp()) {
            textBox2.setText("Lavt forbrug af pesticider");
            textBox3.setText("Stort vandforbrug\n - 650L per t-shirt\nGennemgå kemisk proces");
        }
        if (selectedItem == Game.getLinen()) {
            textBox2.setText("Let nedbrydeligt i naturen\nLavt vandforbrug \nLavt forbrug af pesticider\n - 6.4L per t-shirt");
            textBox3.setText("Krøller nemt\nGennemgå kemisk proces");
        }
        if (selectedItem == Game.getBamboo()) {
            textBox2.setText("Hurtigtvoksende\nProducerer meget oxygen pr hektar\nAbsorberer op til 12 ton kuldioxid\n - 2 ton mere end gennemmsnittet pr hektar\nLavt vandforbrug");
            textBox3.setText("Primært dyrket i Kina\n - Lave krav ift. forurening\nGennemgå kemisk proces");
        }
        if (selectedItem == Game.getCotton()) {
            textBox2.setText("Let og blødt\n");
            textBox3.setText("Stort vandforbrug\n - 2700L per t-shirt\nGror bedst i regnfattige lande\nGennemgå kemisk proces");
        }
        if (selectedItem == Game.getPolyester()) {
            textBox2.setText("Slidtstærkt\nTørrer hurtigt\nKrøller ikke så nemt\nBilligt ");
            textBox3.setText("Produkt baseret på olie og naturgas\nBrug af Carcinogen\n - Kemikalie der findes i benzen og asbest\n - Kræftfremkaldende & skadeligt for miljøet\nProduceret i lande med lave krav til forunering\n - Eksempelvis Kina, Bangladesh og Indonesien");
        }

    }

    public void openInventory(MouseEvent mouseEvent) {
        boolean setVisibility = !vBoxPlayerInventory.isVisible();
        vBoxPlayerInventory.setVisible(setVisibility);
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
        setHintLabel();
    }

    public void setScoreboard(Room room) {
        scoreboard.setText(String.valueOf("Current score: " + Player.playerScore.getScore()));
    }

    public void showHelp(MouseEvent mouseEvent) {
        boolean setVisibility = !PaneShowHelp.isVisible();
        PaneShowHelp.setVisible(setVisibility);
    }
}
