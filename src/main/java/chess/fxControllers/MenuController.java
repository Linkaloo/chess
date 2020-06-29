package chess.fxControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    public void playPressed(ActionEvent event) throws IOException {
        Parent gameMenuParent = FXMLLoader.load(getClass().getClassLoader().getResource("gameSetup.fxml"));
        Scene gameMenuScene = new Scene(gameMenuParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(gameMenuScene);
        window.show();

    }
}


