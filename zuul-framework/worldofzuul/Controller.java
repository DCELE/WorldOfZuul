package worldofzuul;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private VBox vBoxPlayerInventory, vBoxRoomInventory;
    @FXML
    public Pane PaneShowHelp;
    @FXML
    private Button button1, button2, button3, button4;
    @FXML
    private Label textBox, textBox1, textBox2,textBox3,textBox4,textBox5;
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
        // Set room text label

        // Set room welcome description
        setTextBox(room);

        setScoreboard(room);
        // Set button text labels
        setNavigationButtons(room);
        // Set room inventory
        setRoomInventory(room);
        // Set currentRoom
        Game.setCurrentRoom(room);

        prosConsPanel.setVisible(false);
        prosConsPanel1.setVisible(false);
        if (Game.getCurrentRoom() == Game.getMaterials()) {
            prosConsPanel.setVisible(true);
            prosConsPanel1.setVisible(true);
        }

    }

    private void setRoomInventory(Room room) {
        observRoomInventory = FXCollections.observableArrayList();
        observRoomInventory.addAll(room.getInventory().getArrayList());
        roomInventory.setItems(observRoomInventory);
    }

    public void setTextBox(Room room) {
        textBox.setText(room.getLongDescription()); }

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

    }

    public void dropItem(Item item) {
        observPlayerInventory.remove(item);
        observRoomInventory.add(item);
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
        Game.dropItem(selectedItem);
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
            return;
        }
        dropItem(selectedItem);
        setTextBox(Game.getCurrentRoom());
    }

    public void materialClicked(MouseEvent mouseEvent) {

        //if (room.getName().equals("materials")) {

            Item selectedItem = roomInventory.getSelectionModel().getSelectedItem();
            textBox1.setText(selectedItem.getName());
            textBox4.setText("Pros");
            textBox5.setText("Cons");


        if (selectedItem.getName().equals("hemp seed")) {
            textBox2.setText("Lavt forbrug af pesticider");
            textBox3.setText("Stort vandforbrug\n - 650L per t-shirt\nGennemgå kemisk proces");
        }
        if (selectedItem.getName().equals("linen seed")) {
            textBox2.setText("Let nedbrydeligt i naturen\nLavt vandforbrug \nLavt forbrug af pesticider\n - 6.4L per t-shirt");
            textBox3.setText("Krøller nemt\nGennemgå kemisk proces");
        }
        if (selectedItem.getName().equals("bamboo seed")) {
            textBox2.setText("Hurtigtvoksende\nProducerer meget oxygen pr hektar\nAbsorberer op til 12 ton kuldioxid\n - 2 ton mere end gennemmsnittet pr hektar\nLavt vandforbrug");
            textBox3.setText("Primært dyrket i Kina\n - Lave krav ift. forurening\nGennemgå kemisk proces");
        }
        if (selectedItem.getName().equals("cotton seed")) {
            textBox2.setText("Let og blødt\n");
            textBox3.setText("Stort vandforbrug\n - 2700L per t-shirt\nGror bedst i regnfattige lande\nGennemgå kemisk proces");
        }
        if (selectedItem.getName().equals("polyester chemicals")) {
            textBox2.setText("Slidtstærkt\nTørrer hurtigt\nKrøller ikke så nemt\nBilligt ");
            textBox3.setText("Produkt baseret på olie og naturgas\nBrug af Carcinogen\n - Kemikalie der findes i benzen og asbest\n - Kræftfremkaldende & skadeligt for miljøet\nProduceret i lande med lave krav til forunering\n - Eksempelvis Kina, Bangladesh og Indonesien");
        }

    }

    public void openInventory(MouseEvent mouseEvent) {
        boolean setVisibility = !vBoxPlayerInventory.isVisible();
        vBoxPlayerInventory.setVisible(setVisibility);
        vBoxRoomInventory.setVisible(setVisibility);
    }

    public void setScoreboard(Room room){ scoreboard.setText(String.valueOf("Current score: " + Player.playerScore.getScore()));}
}
    public void showHelp(MouseEvent mouseEvent) {
        boolean setVisibility = !PaneShowHelp.isVisible();
        PaneShowHelp.setVisible(setVisibility);
    }
    }
