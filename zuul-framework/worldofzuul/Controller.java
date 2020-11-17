package worldofzuul;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.util.HashMap;


public class Controller {
    @FXML
    ListView roomInventoryList;
    @FXML
    ListView playerInventoryList;
    @FXML
    Label roomID;
    @FXML
    Button Button1, Button2, Button3, Button4;

    public void onButtonClicked(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        String labelText = button.getText();



        loadRoom(labelText, button);
    }

    public void loadRoom(String labelText, Button button) {
        // Set room text label
        String roomName = roomID.getText();
        roomID.setText(labelText);

        // Set button text labels
        button.setText(roomName);
        getRoom(roomName)

        // Set room inventory

        // Set room welcome text
    }

    public Room getRoom(String roomName) {
        Room thisRoom = null;
        for (Room room : Room.getAllRooms()) {
            if (!room.getName().equals(roomName)) {
                continue;
            }
            thisRoom = room;
            break;
        }
        return thisRoom;
    }


}
