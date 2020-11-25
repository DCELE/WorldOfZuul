package worldofzuul;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.net.URL;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;


public class Controller implements Initializable {
    Room currentRoom = null;

    @FXML
    private Button button1, button2, button3, button4;
    @FXML
    private Label textBox;
    @FXML
    private


    //Arraylist eksempel
    ArrayList<String> str = new ArrayList<String>();

    @FXML ListView listView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRoom(Room.getAllRooms().get(0));

    }

    public void onNavigationButtonClicked(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        String labelText = button.getText();
        loadRoom(getRoom(labelText));
    }

    // Load room everytime NavigationButton is clicked
    public void loadRoom(Room room) {
        // Set room text label

        // Set room welcome description
        setTextBox(room);

        // Set button text labels
        setNavigationButtons(room);
        // Set room inventory
        // Set room welcome text
    }


    public void setTextBox(Room room) {
        textBox.setText(room.getLongDescription());
    }

    public void setNavigationButtons(Room room) {
        Button[] buttons = new Button[] {button1, button2, button3, button4};
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
        for (Room room : Room.getAllRooms()) {
            if (!room.getName().equals(roomName)) {
                continue;
            }
            currentRoom = room;
            break;
        }
        return currentRoom;
    }

    public void onUseButtonClicked(MouseEvent mouseEvent) {

    }

    public void roomInventoryClicked(MouseEvent mouseEvent)
    {

    }

/*

    str.clear();
    String a = "1";
    String b = "2";
    String c = "3";
    String d = "4";
    str.add(a);
    str.add(b);
    str.add(c);
    str.add(d);
    listView.getItems().addAll(str);
    */

}
