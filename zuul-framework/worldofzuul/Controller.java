package worldofzuul;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    Button Button1, Button2, Button3, Button4;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRoom(Room.getAllRooms().get(0));
    }

    public void onButtonClicked(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        String labelText = button.getText();
        loadRoom(getRoom(labelText));
    }

    public void loadRoom(Room room) {
        // Set room text label

        // Set room welcome description


        // Set button text labels
        setNavigationButtons(room);
        // Set room inventory

        // Set room welcome text
    }

    public void setNavigationButtons(Room room) {
        Button[] buttons = new Button[] {Button1, Button2, Button3, Button4};
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
