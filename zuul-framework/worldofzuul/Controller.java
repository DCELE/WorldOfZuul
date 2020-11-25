package worldofzuul;

import java.net.URL;
import java.util.ResourceBundle;

    public class Controller implements Initializable {
        Room currentRoom = null;

        @FXML
        private Button button1, button2, button3, button4;
        @FXML
        private Label textBox;


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            loadRoom(Room.getAllRooms().get(0));


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
            setRoomInventory(room);
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
}
