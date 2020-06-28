package chess.fxControllers;

import chess.modules.gameObjects.Board;
import chess.modules.gameObjects.gamePieces.Piece;
import chess.modules.gameObjects.gamePieces.PieceMove;
import chess.modules.GameController;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class GameScreenController {

    private int mouseX, mouseY;
    private Board board;
    private GameController gameController;
    private ImageView temp;

    @FXML
    private GridPane boardGrid;

    public void initialize() {
        board = new Board(boardGrid);
        gameController = new GameController(board);
    }

    public void mousePressed(MouseEvent mouseEvent) {

        if(mouseEvent.getTarget() instanceof ImageView) {
            temp = (ImageView) mouseEvent.getTarget();
            if(!(board.getPieceFromImage(temp).getPieceColor().equals(gameController.checkTurn()))) {
                temp = null;
            }
        }
        else if(mouseEvent.getTarget() instanceof Pane) {
            if(temp != null) {
                boardGrid.getChildren().remove(temp);
                Integer col = GridPane.getColumnIndex((Pane) mouseEvent.getTarget()), row = GridPane.getRowIndex((Pane) mouseEvent.getTarget());
                boardGrid.add(temp, col, row);
                gameController.movePiece(temp, col, row);
                temp = null;
            }
        }
    }
}