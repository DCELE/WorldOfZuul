package worldofzuul;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.util.Set;


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

        //System.out.println(getRoom(labelText).getName());
        loadRoom(getRoom(labelText));
        button.setText(labelText);
    }

    public void setButton(Button button, Room exitRoom) {
        button.setText(exitRoom.getName());
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
        setButton(Button1, room.getExits().get(0));

        if (room.getExits().size() > 1) {
            setButton(Button2, room.getExits().get(1));
        }
        else if (room.getExits().size() > 2) {
            setButton(Button3, room.getExits().get(2));
        }
        else if (room.getExits().size() > 3) {
            setButton(Button4, room.getExits().get(3));
        }
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
