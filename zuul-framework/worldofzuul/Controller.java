package worldofzuul;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller {
    @FXML
    ListView<Item> inventoryList;
    @FXML
    ObservableList<Item> playerInventory;
    
    
    //Remove items from playerInventory
    public void removeItem (ActionEvent actionEvent)
    {
        Item item = inventoryList.getSelectionModel().getSelectedItem();
        playerInventory.remove(item);
    }


    @Override
    public void initialize (URL url, ResourceBundle resourceBundle)
   {
       playerInventory = FXCollections.observableArrayList();
       Item item1 = new Materials("Hemp", 3);
       Item item2 = new Materials("Cotton", 4);
       playerInventory.add(item1);
       playerInventory.add(item2);
       inventoryList.setItems(playerInventory);
       
   }
}
    



