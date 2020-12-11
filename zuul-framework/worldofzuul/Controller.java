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
    public ImageView wellImageView;
    @FXML
    private ImageView hintImage;
    @FXML
    private Label itemDescription;
    @FXML
    private VBox pickUpQuestion;
    @FXML
    private ImageView roomInventoryItem1, roomInventoryItem2, roomInventoryItem3, roomInventoryItem4, roomInventoryItem5, roomInventoryItem6, navMain1;
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
    public Pane PaneShowHelp, navMainPane, navMaterialPane, navFarmPane, navWellPane, navFactoryPane, navColorPane, navSewingPane, navFabricPane;
    @FXML
    private Button button1, button2, button3, button4;
    @FXML
    private Label textBox, textBox1, textBox2, textBox3, textBox4, textBox5, testlabel;
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
            if (inventory == Game.getWell().getInventory() && item == Game.getWater()) {
                continue;
            }

            inventorySlots[i].setImage(item.getItemIcon());
            inventorySlots[i].setDisable(false);
            i++;
        }

    }

    private void placeItemsInRoom(Room room) {
        for(Item item: room.getInventory().getArrayList()) {
            if (room == Game.getWell()) {
                if (item.getId() == 7) {
                    bucketImageView.setImage(item.getItemIcon());
                    bucketImageView.setOnMouseClicked(this::onItemInRoomInvClicked);

                }
                Image image = new Image ("worldofzuul/WorldOfZuulPNG/Icons/waterRoomHead.png");
                talkingFace.setImage(image);

            }

            if (room == Game.getFarm()) {
                if (item.getId() == 8) {
                    pesticideImageView.setImage(item.getItemIcon());
                }
                Image image = new Image ("worldofzuul/WorldOfZuulPNG/Icons/farmRoomHead.png");
                talkingFace.setImage(image);
            }

            if (room == Game.getFactory() || room == Game.getSewingFactory() || room == Game.getColorFactory()) {
                if (item.getId() == 9) {
                    chemicalImageView.setImage(item.getItemIcon());
                }
                Image image = new Image ("worldofzuul/WorldOfZuulPNG/Icons/factoryHead.png");
                talkingFace.setImage(image);
            }

            if (room == Game.getMaterials()) {
                switch(item.getId()) {
                    case 1:
                        materialImage1.setImage(item.getItemIcon());
                    case 2:
                        materialImage2.setImage(item.getItemIcon());
                    case 3:
                        materialImage3.setImage(item.getItemIcon());
                    case 4:
                        materialImage4.setImage(item.getItemIcon());
                    case 5:
                        materialImage5.setImage(item.getItemIcon());

                }
                if (item.getId() == 9) {
                    chemicalImageView.setImage(item.getItemIcon());
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

    public void factoryNavClicked(MouseEvent mouseEvent) { loadRoom(Game.getFactory());}

    public void wellNavClicked(MouseEvent mouseEvent) { loadRoom(Game.getWell());}

    public void farmNavClicked(MouseEvent mouseEvent) { loadRoom(Game.getFarm());}

    public void materialNavClicked(MouseEvent mouseEvent) { loadRoom(Game.getMaterials());}

    public void mainNavClicked(MouseEvent mouseEvent) {loadRoom(Game.getMainRoom());}

    public void colorNavClicked(MouseEvent mouseEvent) {loadRoom(Game.getColorFactory());}

    public void sewingNavClicked(MouseEvent mouseEvent) {loadRoom(Game.getSewingFactory());}

    public void fabricNavClicked(MouseEvent mouseEvent) {loadRoom(Game.getFabricFactory());}



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
        // Set room inventory
        setRoomInventory(room);
        // Set currentRoom
        Game.setCurrentRoom(room);
        // Set hintBox
        setHintLabel();
        // Set background
        setBackgroundImage(room);
        //Set exitImages
        setNavigationImages(room);
        // Set

        placeItemsInRoom(room);


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


    private void setRoomInventory(Room room) {
        setInventory(room.getInventory());
        
        wellImageView.setVisible(false);
        if (room == Game.getWell()) {
            wellImageView.setVisible(true);

        }
    }

    public void setTextBox(Room room) {

        textBox.setText(room.getLongDescription());
    }


    public void setNavigationImages(Room room) {
        //Set visible true
        if (room == Game.getMainRoom()) {
            navMainPane.setVisible(true);
        }
        if (room == Game.getMaterials()) {
            navMaterialPane.setVisible(true);
        }
        if (room == Game.getFarm()) {
            navFarmPane.setVisible(true);
        }
        if (room == Game.getColorFactory()) {
            navColorPane.setVisible(true);
        }
        if (room == Game.getFabricFactory()) {
            navFabricPane.setVisible(true);
        }
        if (room == Game.getFactory()) {
            navFactoryPane.setVisible(true);
        }
        if (room == Game.getSewingFactory()) {
            navSewingPane.setVisible(true);
        }
        if (room == Game.getWell()) {
            navWellPane.setVisible(true);
        }


        //set visible false
        if (room != Game.getMainRoom()) {
            navMainPane.setVisible(false);
        }
        if (room != Game.getMaterials()) {
            navMaterialPane.setVisible(false);
        }
        if (room != Game.getFarm()) {
            navFarmPane.setVisible(false);
        }
        if (room != Game.getColorFactory()) {
            navColorPane.setVisible(false);
        }
        if (room != Game.getFabricFactory()) {
            navFabricPane.setVisible(false);
        }
        if (room != Game.getFactory()) {
            navFactoryPane.setVisible(false);
        }
        if (room != Game.getSewingFactory()) {
            navSewingPane.setVisible(false);
        }
        if (room != Game.getWell()) {
            navWellPane.setVisible(false);
        }


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
            loadRoom(Game.getCurrentRoom());
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

        if (Game.getChosenMaterial().getId() == 5) {
            if (Game.getChosenMaterial().getRecipes().size() == Game.getChosenMaterial().getState()-1) {
                hintLabel.setText("You've come to \nthe end of your journey. \nThank you for playing \nour game \n- T6-1");
            }
            return;
        }

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
        selectedItemRoomInv = null;
        askToPickUp(null);
    }

    public void onWellClicked(MouseEvent mouseEvent) {
        onItemInRoomInvClicked(mouseEvent);

    }
}
