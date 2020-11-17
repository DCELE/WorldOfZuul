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
    Room main, materials, factory, farm, waterReservoir, sewing, fabric, coloring;
    HashMap<Room, Room[]> neighborRooms;

    @FXML
    ListView roomInventoryList;
    @FXML
    ListView playerInventoryList;
    @FXML
    Label roomID;
    @FXML
    Button Button1, Button2, Button3, Button4;

    public void createRooms() {
/*
        main = new Room("Main room");
        materials = new Room("Materials room");
        factory = new Room("Factory");
        farm = new Room("Farm");
        waterReservoir = new Room("Water reservoir");
        sewing = new Room("Sewing room");
        fabric = new Room("Fabric room");
        coloring = new Room("Coloring room");


        main.addNeighbors(new Room[]{materials, factory, waterReservoir, farm});

        materials.addNeighbors(new Room[]{main});
        factory.addNeighbors(new Room[]{sewing, fabric, main, coloring});
        farm.addNeighbors(new Room[]{main});
        waterReservoir.addNeighbors(new Room[]{main});
        sewing.addNeighbors(new Room[]{factory});
        fabric.addNeighbors(new Room[]{factory});
        coloring.addNeighbors(new Room[]{factory});
*/
    }


    public void onButtonClicked(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        String labelText = button.getText();
        roomID.setText(labelText);

        loadRoom(labelText);
    }

    private void loadRoom(String labelText) {
        // Set room text label

        // Set button text labels
        /*
        Room[] allRooms = new Room[]{main, materials, factory, farm, waterReservoir, sewing, fabric, coloring};
        Room theRoom = null;
        for (Room room: allRooms) {
            if (room.getRoom(labelText) != null) {
                theRoom = room;
            }
        }
        Room[] neighbors = neighborRooms.get(theRoom);
        int neighborsInt = 0;
        for (Room room : neighbors) {
            neighborsInt++;
        }
        if (neighborsInt >= 1) {
            Button1.setText(neighbors[0].getName());
        }
        if (neighborsInt >= 2) {
            Button2.setText(neighbors[1].getName());
        }
        if (neighborsInt >= 3) {
            Button3.setText(neighbors[2].getName());
        }
        if (neighborsInt >= 4) {
            Button3.setText(neighbors[3].getName());
        }
         */

        // Set room inventory

        // Set room welcome text

    }
}
