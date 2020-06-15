package chess.fxControllers;

import chess.modules.gameObjects.Board;
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

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

public class GameScreenController {

    private int mouseX, mouseY;
    private ImageView temp;

    @FXML
    private GridPane boardGrid;

    public void initialize() {
        Board board = new Board(boardGrid);
    }

    public void mousePressed(MouseEvent mouseEvent) {
//        File file = new File(String.valueOf(getClass().getClassLoader().getResource("assets/B.B.png")));
//        Image image = new Image(file.getPath());
//        ImageView imageView = new ImageView();
//        imageView.setImage(image);
//        boardGrid.add(imageView, 2, 0);

        if(mouseEvent.getTarget() instanceof ImageView)
            this.temp = (ImageView) mouseEvent.getTarget();
        else if(mouseEvent.getTarget() instanceof Pane) {
            if(temp != null) {
                boardGrid.getChildren().remove(temp);
                Integer col = GridPane.getColumnIndex((Pane) mouseEvent.getTarget()), row = GridPane.getRowIndex((Pane) mouseEvent.getTarget());
                boardGrid.add(temp, col, row);
            }
        }


    }
}