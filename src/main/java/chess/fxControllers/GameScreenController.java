package chess.fxControllers;

import javafx.fxml.FXML;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class GameScreenController {

    private int mouseX, mouseY;

    @FXML
    private GridPane boardGrid;

    /*public void piecePressed(javafx.scene.input.MouseEvent mouseEvent) {
        DropShadow ds1 = new DropShadow();
        ds1.setOffsetY(4.0f);
        ds1.setOffsetX(4.0f);
        ds1.setColor(Color.CORAL);
        wPawn8.setEffect(ds1);
    }*/

    public void mouseOver(MouseEvent mouseEvent) {

    }

    public void mouseClicked(MouseEvent mouseEvent) {

    }
}