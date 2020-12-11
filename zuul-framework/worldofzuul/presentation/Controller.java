package worldofzuul.presentation;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import worldofzuul.domain.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private ImageView wellImageView, wellTable, sewingTable, farmTable, factoryTable, fabricTable, coloringTable, materialsTable, mainroomTable;
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
    private ImageView playerInventory, playerInventoryItem1, playerInventoryItem2, playerInventoryItem3, playerInventoryItem4, playerInventoryItem5, playerInventoryItem6,
            playerInventoryItem7, playerInventoryItem8, playerInventoryItem9, bucketImageView, pesticideImageView, chemicalImageView, materialImage1, materialImage2, materialImage3,
            materialImage4, materialImage5, talkingFace, endResultImageView, endResultPersonImageView, closeButton;;
    @FXML
    private ImageView backgroundImage;
    @FXML
    private AnchorPane materialPane;
    private VBox pickMaterialColor;
    private ToggleGroup pickColor;
    @FXML
    private Label hintLabel;
    @FXML
    public Pane PaneShowHelp, navMainPane, navMaterialPane, navFarmPane, navWellPane, navFactoryPane, navColorPane, navSewingPane, navFabricPane, hintPane, endResultsPane, startPane;
    @FXML
    private Label textBox, textBox1, textBox2, textBox3, textBox4, textBox5;
    @FXML
    private Label scoreboard;
    @FXML
    private FlowPane roomInventory;
    @FXML
    private Pane prosConsPanel, prosConsPanel1;
    private Item selectedItemPlayInv, selectedItemRoomInv, itemSelectedOnce = null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRoom(Game.getMainRoom());
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
        // Set the scoreboard
        setScoreboard();
        // Set room inventory
        setRoomInventory(room);
        // Set currentRoom
        Game.setCurrentRoom(room);
        // Set hintBox
        setHintLabel();
        // Set background
        setBackgroundImage(room);
        // Set exitImages
        setNavigationImages(room);
        // Set playerHead
        setPlayerHead(room);
        //
        setFinalScore();


        prosConsPanel.setVisible(false);
        prosConsPanel1.setVisible(false);
        if (Game.getCurrentRoom() == Game.getMaterials()) {
            if (Game.getChosenMaterial() == null) {
                prosConsPanel.setVisible(true);
                prosConsPanel1.setVisible(true);
            }
        }
    }

    public void setInventory(Inventory inventory) {
        ImageView[] inventorySlots = new ImageView[]{roomInventoryItem1, roomInventoryItem2, roomInventoryItem3, roomInventoryItem4, roomInventoryItem5, roomInventoryItem6};

        if (inventory == Player.getInventory()) {
            inventorySlots = new ImageView[]{playerInventoryItem1, playerInventoryItem2, playerInventoryItem3, playerInventoryItem4, playerInventoryItem5, playerInventoryItem6, playerInventoryItem7, playerInventoryItem8, playerInventoryItem9};
        }

        // Disable the unused inventory slots
        for (ImageView imageView : inventorySlots) {
            imageView.setDisable(true);
            imageView.setImage(null);
        }

        int width = 0;
        if (inventory != Player.getInventory()) {
            disableAllSpecialImageViews();
            disableTables();
            if (inventory == Game.getMaterials().getInventory()) {
                materialPane.setDisable(false);
            }
            for (ImageView imageView : inventorySlots) {
                imageView.setFitWidth(width / 6.0);
            }
        }

        // Placing items in a specific place in the room
        int i = 0;
        for (Item item : inventory.getArrayList()) {
            if (inventory == Game.getWell().getInventory() && item == Game.getWater()) {
                continue;
            } else if (inventory == Game.getWell().getInventory() && item == Game.getBucket()) {
                bucketImageView.setImage(item.getItemIcon());
                bucketImageView.setDisable(false);

            } else if (inventory == Game.getFarm().getInventory() && item == Game.getPesticides()) {
                pesticideImageView.setImage(item.getItemIcon());
                pesticideImageView.setDisable(false);

            } else if (inventory == Game.getFactory().getInventory() && item == Game.getChemicals()) {
                chemicalImageView.setImage(item.getItemIcon());
                chemicalImageView.setDisable(false);

            } else if (inventory == Game.getMaterials().getInventory() && item == Game.getHemp()) {
                materialImage1.setImage(item.getItemIcon());
                materialImage1.setDisable(false);

            } else if (inventory == Game.getMaterials().getInventory() && item == Game.getLinen()) {
                materialImage2.setImage(item.getItemIcon());
                materialImage2.setDisable(false);

            } else if (inventory == Game.getMaterials().getInventory() && item == Game.getBamboo()) {
                materialImage3.setImage(item.getItemIcon());
                materialImage3.setDisable(false);

            } else if (inventory == Game.getMaterials().getInventory() && item == Game.getCotton()) {
                materialImage4.setImage(item.getItemIcon());
                materialImage4.setDisable(false);

            } else if (inventory == Game.getMaterials().getInventory() && item == Game.getPolyester()) {
                materialImage5.setImage(item.getItemIcon());
                materialImage5.setDisable(false);

            } else {
                inventorySlots[i].setImage(item.getItemIcon());
                inventorySlots[i].setDisable(false);
                i++;
            }
        }

        // Placing inventory in a specific place in the different rooms
        if (inventory == Game.getMainRoom().getInventory()) {
            width = 390;
            roomInventory.setLayoutX(50);
            roomInventory.setLayoutY(709-40);
            roomInventory.setPrefWidth(width);
            mainroomTable.setImage(getImage("worldofzuul/WorldOfZuulPNG/Icons/SmallTable.png"));
            mainroomTable.setVisible(true);
        } else if (inventory == Game.getMaterials().getInventory()) {
            width = 300;
            roomInventory.setLayoutX(50);
            roomInventory.setLayoutY(368-40);
            roomInventory.setPrefWidth(width);
        } else if (inventory == Game.getColorFactory().getInventory()) {
            width = 427;
            roomInventory.setLayoutX(610);
            roomInventory.setLayoutY(131-40);
            roomInventory.setPrefWidth(width);
            coloringTable.setImage(getImage("worldofzuul/WorldOfZuulPNG/Icons/ColorShelf.png"));
            coloringTable.setVisible(true);
        } else if (inventory == Game.getFabricFactory().getInventory()) {
            width = 427;
            roomInventory.setLayoutX(620);
            roomInventory.setLayoutY(283-40);
            roomInventory.setPrefWidth(width);
            fabricTable.setImage(getImage("worldofzuul/WorldOfZuulPNG/Icons/ColorShelf.png"));
            fabricTable.setVisible(true);
        } else if (inventory == Game.getFactory().getInventory()) {
            width = 369;
            roomInventory.setLayoutX(65);
            roomInventory.setLayoutY(720);
            roomInventory.setPrefWidth(width);
        } else if (inventory == Game.getFarm().getInventory()) {
            width = 403;
            roomInventory.setLayoutX(50);
            roomInventory.setLayoutY(394-40);
            roomInventory.setPrefWidth(width);
            farmTable.setImage(getImage("worldofzuul/WorldOfZuulPNG/Icons/Table.png"));
            farmTable.setVisible(true);
        } else if (inventory == Game.getSewingFactory().getInventory()) {
            width = 427;
            roomInventory.setLayoutX(600);
            roomInventory.setLayoutY(150);
            roomInventory.setPrefWidth(width);
            sewingTable.setImage(getImage("worldofzuul/WorldOfZuulPNG/Icons/ColorShelf.png"));
            sewingTable.setVisible(true);
        } else if (inventory == Game.getWell().getInventory()) {
            width = 443;
            roomInventory.setLayoutX(60);
            roomInventory.setLayoutY(485-40);
            roomInventory.setPrefWidth(width);
            wellTable.setImage(getImage("worldofzuul/WorldOfZuulPNG/Icons/Table.png"));
            wellTable.setVisible(true);
        }
    }

    private void disableTables() {
        ImageView[] allTables = new ImageView[] {wellTable, sewingTable, farmTable, factoryTable, fabricTable, coloringTable, materialsTable, mainroomTable};
        for (ImageView imageView : allTables) {
            imageView.setVisible(false);
        }
    }

    private void disableAllSpecialImageViews() {
        ImageView[] specialImageView = new ImageView[] {bucketImageView, pesticideImageView, chemicalImageView, materialImage1, materialImage2, materialImage3, materialImage4, materialImage5};
        for (ImageView imageView : specialImageView) {
            imageView.setImage(null);
            imageView.setDisable(true);
        }
        materialPane.setDisable(true);
    }

    //Method that takes input from a on_mouse_clicked and loads a new room.
    public void factoryNavClicked(MouseEvent mouseEvent) {
        loadRoom(Game.getFactory());
    }

    public void wellNavClicked(MouseEvent mouseEvent) {
        loadRoom(Game.getWell());
    }

    public void farmNavClicked(MouseEvent mouseEvent) {
        loadRoom(Game.getFarm());
    }

    public void materialNavClicked(MouseEvent mouseEvent) {
        loadRoom(Game.getMaterials());
    }

    public void mainNavClicked(MouseEvent mouseEvent) {
        loadRoom(Game.getMainRoom());
    }

    public void colorNavClicked(MouseEvent mouseEvent) {
        loadRoom(Game.getColorFactory());
    }

    public void sewingNavClicked(MouseEvent mouseEvent) {
        loadRoom(Game.getSewingFactory());
    }

    public void fabricNavClicked(MouseEvent mouseEvent) {
        loadRoom(Game.getFabricFactory());
    }

    //Informative ingame-head enabled based on room.
    private void setPlayerHead(Room room) {
        if (room == Game.getWell()) {
            Image image = new Image("worldofzuul/WorldOfZuulPNG/Icons/waterRoomHead.png");
            talkingFace.setImage(image);
        }

        if (room == Game.getFarm()) {
            Image image = new Image("worldofzuul/WorldOfZuulPNG/Icons/farmRoomHead.png");
            talkingFace.setImage(image);
        }

        if (room == Game.getFactory() || room == Game.getSewingFactory() || room == Game.getColorFactory()) {
            Image image = new Image("worldofzuul/WorldOfZuulPNG/Icons/factoryHead.png");
            talkingFace.setImage(image);
        }
        if (room == Game.getMaterials()) {
            Image image = new Image("worldofzuul/WorldOfZuulPNG/Icons/TextFruitHat.png");
            talkingFace.setImage(image);
        }
        if (room == Game.getMainRoom()) {
            Image image = new Image("worldofzuul/WorldOfZuulPNG/Icons/TextHelmet.png");
            talkingFace.setImage(image);
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
        // Setting the specific room's exits
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

    // add item to player inventory in relation to the graphics
    public void pickUpItem(Item item) {
        if (!Game.getCurrentRoom().getInventory().getArrayList().contains(item)) {
            return;
        }
        Player.pickUpItem(item);
        loadRoom(Game.getCurrentRoom());
        setInventory(Game.getCurrentRoom().getInventory());
        setInventory(Player.getInventory());
        setRoomInventory(Game.getCurrentRoom());
        setHintLabel();
    }

    // remove item from player inventory in relation to the graphics
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

    // Making a new window to be able to pick a color for the material
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
            if (Game.getChosenMaterial().getRecipes().size() == Game.getChosenMaterial().getState() - 1) {
                hintLabel.setText("You've come to \nthe end of your journey. \nThank you for playing \nour game \n- T6-1");
            }
            return;
        }

        if (Game.getChosenMaterial().getRecipes().size() == Game.getChosenMaterial().getState()) {

            hintLabel.setText("You've come to \nthe end of your journey. \nThank you for playing \nour game \n- T6-1");
        }
    }

    //This method determines what pane is shown at the end of the game.
    public void setFinalScore() {
        if (Game.getChosenMaterial() == null) {
            return;
        }

        //Requirements for a completion of the game.
        if (Game.getChosenMaterial().getRecipes().size() == Game.getChosenMaterial().getState()) {
            endResultsPane.setVisible(true);

            if (Player.playerScore.getScore() > 55) {
                Image image = new Image("worldofzuul/WorldOfZuulPNG/Results/PlaceFirst.png");
                Image imagePerson = new Image("worldofzuul/WorldOfZuulPNG/Results/ManFirstPlaceNatural.png");
                endResultImageView.setImage(image);
                endResultPersonImageView.setImage(imagePerson);

                //If statement that changes pane based on if you colored your shirt or not.
                if (Game.getChosenMaterial().getColor().equals("blue")) {
                    Image imagePersonColored = new Image("worldofzuul/WorldOfZuulPNG/Results/ManFirstPlaceColored.png");
                    endResultPersonImageView.setImage(imagePersonColored);
                } else {
                    Image imagePerson1 = new Image("worldofzuul/WorldOfZuulPNG/Results/ManFirstPlaceNatural.png");
                    endResultPersonImageView.setImage(imagePerson1);
                }
            }
            if (Player.playerScore.getScore() > 24 && Player.playerScore.getScore() < 56) {
               Image image = new Image("worldofzuul/WorldOfZuulPNG/Results/PlaceSecond.png");
                Image imagePerson = new Image("worldofzuul/WorldOfZuulPNG/Results/ManSecondPlaceNatural.png");
                endResultImageView.setImage(image);
                endResultPersonImageView.setImage(imagePerson);

                if (Game.getChosenMaterial().getColor().equals("blue")) {
                    Image imagePersonColored = new Image("worldofzuul/WorldOfZuulPNG/Results/ManSecondPlaceColored.png");
                    endResultPersonImageView.setImage(imagePersonColored);
                } else {
                    Image imagePerson2 = new Image("worldofzuul/WorldOfZuulPNG/Results/ManSecondPlaceNatural.png");
                    endResultPersonImageView.setImage(imagePerson2);
                }
            }
            if (Player.playerScore.getScore() > -1 && Player.playerScore.getScore() < 25) {
                Image image = new Image("worldofzuul/WorldOfZuulPNG/Results/PlaceThird.png");
                Image imagePerson = new Image("worldofzuul/WorldOfZuulPNG/Results/ManThirdPlaceNatural.png");
                endResultImageView.setImage(image);
                endResultPersonImageView.setImage(imagePerson);

                if (Game.getChosenMaterial().getColor().equals("blue")) {
                    Image imagePersonColored = new Image("worldofzuul/WorldOfZuulPNG/Results/ManThirdPlaceColored.png");
                    endResultPersonImageView.setImage(imagePersonColored);
                } else {
                    Image imagePerson3 = new Image("worldofzuul/WorldOfZuulPNG/Results/ManThirdPlaceNatural.png");
                    endResultPersonImageView.setImage(imagePerson3);
                }
            }
            if (Player.playerScore.getScore() < 0) {
                Image image = new Image("worldofzuul/WorldOfZuulPNG/Results/PlaceMinusTrump.png");
                    endResultImageView.setImage(image);
                }
            }
        }

    public void onHintClicked(MouseEvent mouseEvent) {
        boolean labelVisibility = hintLabel.isVisible();
        hintPane.setVisible(!labelVisibility);
        hintLabel.setVisible(!labelVisibility);
        hintImage.setVisible(!labelVisibility);
        setHintLabel();
    }

    public void setScoreboard() {
        scoreboard.setText("Current score: " + Player.playerScore.getScore());
    }

    public void showHelp(MouseEvent mouseEvent) {
        boolean setVisibility = !PaneShowHelp.isVisible();
        PaneShowHelp.setVisible(setVisibility);
    }

    public void startGame(MouseEvent mouseEvent) {
        startPane.setVisible(false);
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
        loadRoom(Game.getCurrentRoom());
    }

    public void onDenyPickUp(MouseEvent mouseEvent) {
        pickUpQuestion.setVisible(false);
        selectedItemRoomInv = null;
        askToPickUp(null);
    }

    public void onWellClicked(MouseEvent mouseEvent) {
        onItemInRoomInvClicked(mouseEvent);

    }
    
    public void closeButton(MouseEvent mouseEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
