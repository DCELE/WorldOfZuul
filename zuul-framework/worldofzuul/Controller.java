package worldofzuul;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    @FXML
    private Pane playInvPane;
    @FXML
    private ImageView playerInventory, playerInventoryItem1, playerInventoryItem2, playerInventoryItem3, playerInventoryItem4, playerInventoryItem5, playerInventoryItem6, playerInventoryItem7, playerInventoryItem8, playerInventoryItem9, bucketImageView, pesticideImageView, chemicalImageView, materialImage1, materialImage2, materialImage3, materialImage4, materialImage5, talkingFace;
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
    private ListView<Item> roomInventory;
    private ObservableList<Item> observRoomInventory;
    @FXML
    private Pane prosConsPanel, prosConsPanel1;
    private worldofzuul.Item selectedItemPlayInv;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRoom(Room.getAllRooms().get(0));

    }

    public void setPlayerInventory() {
        ImageView[] inventorySlots = new ImageView[] {playerInventoryItem1, playerInventoryItem2, playerInventoryItem3, playerInventoryItem4, playerInventoryItem5, playerInventoryItem6, playerInventoryItem7, playerInventoryItem8, playerInventoryItem9};
        for (ImageView imageView : inventorySlots) {
            imageView.setDisable(true);
        }

        int i = 0;
        for (Item item : Player.getInventory().getArrayList()) {
            inventorySlots[i].setImage(getImage(item.getItemIcon()));
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
        //Set exitImages
        //setNavigationImages(room);





        setItemImages(room);


        if (room == Game.getMainRoom()) {
            Image image = new Image ("worldofzuul/WorldOfZuulPNG/Icons/mainRoomHead.png");
            talkingFace.setImage(image);
        }

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



    private void setItemImages(Room room) {
        for(Item item: room.getInventory().getArrayList()) {


        if (room == Game.getWell()) {
            if (item.getId() == 7) {
                bucketImageView.setImage(getImage(item.getItemIcon()));
            }
            Image image = new Image ("worldofzuul/WorldOfZuulPNG/Icons/waterRoomHead.png");
            talkingFace.setImage(image);
        }

        if (room == Game.getFarm()) {
            if (item.getId() == 8) {
                pesticideImageView.setImage(getImage(item.getItemIcon()));
            }
            Image image = new Image ("worldofzuul/WorldOfZuulPNG/Icons/farmRoomHead.png");
            talkingFace.setImage(image);
        }

            if (room == Game.getFactory() || room == Game.getSewingFactory() || room == Game.getColorFactory()) {
                if (item.getId() == 9) {
                    chemicalImageView.setImage(getImage(item.getItemIcon()));
                }
                Image image = new Image ("worldofzuul/WorldOfZuulPNG/Icons/factoryHead.png");
                talkingFace.setImage(image);
            }

            if (room == Game.getMaterials()) {
                switch(item.getId()) {
                    case 1:
                        materialImage1.setImage(getImage(item.getItemIcon()));
                    case 2:
                        materialImage2.setImage(getImage(item.getItemIcon()));
                    case 3:
                        materialImage3.setImage(getImage(item.getItemIcon()));
                    case 4:
                        materialImage4.setImage(getImage(item.getItemIcon()));
                    case 5:
                        materialImage5.setImage(getImage(item.getItemIcon()));

                }
                if (item.getId() == 9) {
                    chemicalImageView.setImage(getImage(item.getItemIcon()));
                }
            }

    }
        if (room != Game.getWell()) {
            bucketImageView.setImage(null);
        }
        if (room != Game.getFarm()) {
            pesticideImageView.setImage(null);

        }
        if (room != Game.getFactory()) {
            chemicalImageView.setImage(null);
        }
        if (room != Game.getMaterials()) {
            materialImage1.setImage(null);
            materialImage2.setImage(null);
            materialImage3.setImage(null);
            materialImage4.setImage(null);
            materialImage5.setImage(null);
        }


    }

    private void setRoomInventory(Room room) {
        observRoomInventory = FXCollections.observableArrayList();
        observRoomInventory.addAll(room.getInventory().getArrayList());
        roomInventory.setItems(observRoomInventory);
    }

    public void setTextBox(Room room) {
        textBox.setText(room.getLongDescription());
    }


    /*public void setNavigationImages(Room room) {
        if (Game.getMainRoom()) {

        }
    } */
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
        Player.pickUpItem(item);
        setPlayerInventory();
        setRoomInventory(Game.getCurrentRoom());
        setHintLabel();
    }

    public void dropItem(Item item) {
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
            //playerInventory.refresh();
            setTextBox(Game.getCurrentRoom());
            return;
        }
        pickUpItem(selectedItem);
        setTextBox(Game.getCurrentRoom());
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
            //playerInventory.refresh();
            roomInventory.refresh();
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
        ImageView[] inventorySlots = new ImageView[] {playerInventoryItem1, playerInventoryItem2, playerInventoryItem3, playerInventoryItem4, playerInventoryItem5, playerInventoryItem6, playerInventoryItem7, playerInventoryItem8, playerInventoryItem9};

        for (ImageView imageView : inventorySlots) {
            if (imageView == null) {
                continue;
            }
            if (imageView.getImage() == null) {
                continue;
            }
            System.out.println(imageView.imageProperty());
            System.out.println();
        }

        /*

        System.out.println(imageView.getImage().getUrl());

        for (Item item : Player.getInventory().getArrayList()) {
            System.out.println(item.getName());
            System.out.println(item.getItemIcon());
            if (imageView.getImage() == getImage(item.getItemIcon())) {
                selectedItemPlayInv = item;
            }
        }

         */



    }
}
