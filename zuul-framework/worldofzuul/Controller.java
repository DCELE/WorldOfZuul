package worldofzuul;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;


public class Controller {
    @FXML
    ListView roomInventoryList;
    @FXML
    ListView playerInventoryList;
    @FXML
    Label roomID;
    @FXML
    Button Button1, Button2, Button3, Button4;

    public void startingRoom(Room room) {
        loadRoom(room);
    }

    public void onButtonClicked(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        String labelText = button.getText();


        loadRoom(getRoom(labelText));
        button.setText(labelText);
    }

    public void setButtons(Button button, Room exitRoom) {
        button.setText(exitRoom.getName());
    }

    public void loadRoom(Room room) {
        // Set room text label
        roomID.setText(room.getName());

        //System.out.println(room.getExit()[2].getName());
        // Set button text labels
        //setButtons(Button1, room.getExit()[0]);

        if (room.getExits().length > 1) {
            setButtons(Button2, room.getExitIndex(1));
        }
        else if (room.getExits().length > 2) {
            setButtons(Button3, room.getExitIndex(2));
        }
        else if (room.getExits().length > 3) {
            setButtons(Button4, room.getExitIndex(3));
        }
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
