package chess.fxControllers;

import javafx.fxml.FXML;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

    public void mouseOver(javafx.scene.input.MouseEvent mouseEvent) {

    }

}