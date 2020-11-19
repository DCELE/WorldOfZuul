package worldofzuul;

import java.net.URL;
import java.security.spec.ECField;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Controller implements Initializable {

    @FXML
    private Label lbl1,lbl2;

    @FXML
    private Button startButton,beginButton,wellButton,farmButton, materialsButton,
            factoryButton,mainroomButton,coloringButton,sewingButton;

    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception{
        Stage stage;
        Parent root;

        if(event.getSource()==startButton){
            stage = (Stage) startButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("room2.fxml"));
        }
        else if(event.getSource()==beginButton){
            stage = (Stage) beginButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("mainroom.fxml"));
        }
        else if(event.getSource()==wellButton){
            stage = (Stage) wellButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("well.fxml"));
        }
        else if(event.getSource()==farmButton){
            stage = (Stage) farmButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("farm.fxml"));
        }
        else if(event.getSource()==materialsButton){
            stage = (Stage) materialsButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("materials.fxml"));
        }
        else if(event.getSource()==factoryButton){
            stage = (Stage) factoryButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("factory.fxml"));
        }
        else if(event.getSource()==coloringButton){
            stage = (Stage) coloringButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("coloring.fxml"));
        }
        else if(event.getSource()==sewingButton){
            stage = (Stage) sewingButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("sewing.fxml"));
        }
        else{
            stage = (Stage) mainroomButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("mainroom.fxml"));
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }


}