package worldofzuul;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;


public class Controller implements Initializable {
    @FXML
    ListView roomInventoryList;
    @FXML
    ListView playerInventoryList;
    @FXML
    Label roomID;
    @FXML
    Button Button1, Button2, Button3, Button4;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRoom(Room.getAllRooms().get(0));
    }

    public void onButtonClicked(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        String labelText = button.getText();
        //button.setText(labelText);
        //System.out.println(getRoom(labelText).getName());

        loadRoom(getRoom(labelText));
    }

    public void loadRoom(Room room) {
        // Set room text label
        roomID.setText(room.getName());

        System.out.println(room.getName());
        // Set button text labels
        setAllRoomButtons(room);
        // Set room inventory

        // Set room welcome text
    }

    public void setAllRoomButtons(Room room) {
        Button[] buttons = new Button[] {Button1, Button2, Button3, Button4};
        for (Button button : buttons) {
            button.setVisible(false);
        }
        int i;
        for (int j = 0; j < room.getExits().size(); j++) {
            System.out.print(room.getExits().get(j).getName() + " ");
        }
        System.out.println();
        for (i = 0; i < room.getExits().size(); i++) {
            System.out.println(buttons[i] + " -> " + room.getExits().get(i).getName());
            setButton(buttons[i], room.getExits().get(i));
            buttons[i].setVisible(true);
        }

    }

    public void setButton(Button button, Room exitRoom) {
        button.setText(exitRoom.getName());
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
