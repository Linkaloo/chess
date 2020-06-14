package chess.fxControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;

public class GameScreenController {

    private int mouseX, mouseY;
    private ImageView temp;

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
//        File file = new File(String.valueOf(getClass().getClassLoader().getResource("assets/B.B.png")));
//        Image image = new Image(file.getPath());
//        ImageView imageView = new ImageView();
//        imageView.setImage(image);
//        boardGrid.add(imageView, 2, 0);

        if(mouseEvent.getTarget() instanceof ImageView)
            this.temp = (ImageView) mouseEvent.getTarget();
        else if(mouseEvent.getTarget() instanceof Pane) {
            boardGrid.getChildren().remove(temp);
            boardGrid.add(temp, GridPane.getColumnIndex((Pane)mouseEvent.getTarget()), GridPane.getRowIndex((Pane)mouseEvent.getTarget()));
        }

    }
}