package chess.fxControllers;

import com.sun.javafx.font.CharToGlyphMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Objects;

public class GameSetupController {

    @FXML
    private Pane cpuDif;

    @FXML
    private ToggleGroup playChoice;

    public void backToMenuPressed(ActionEvent event) throws IOException {
        Parent MenuParent = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("menu.fxml")));
        Scene MenuScene = new Scene(MenuParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(MenuScene);
        window.show();

    }

    public void singlePlayerChoice(ActionEvent event) {
        cpuDif.setDisable(false);
    }

    public void multiPlayerChoice(ActionEvent event) {
        cpuDif.setDisable(true);
    }

    public void startGame(ActionEvent event) throws IOException {
        if(!(playChoice.getSelectedToggle() == null)){

            Parent gameScreenParent = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("gameScreen.fxml")));
            Scene gameScreenScene = new Scene(gameScreenParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(gameScreenScene);
            window.show();
        }
    }
}
