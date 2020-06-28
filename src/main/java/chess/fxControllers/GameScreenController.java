package chess.fxControllers;

import chess.modules.gameObjects.Board;
import chess.modules.gameObjects.gamePieces.Piece;
import chess.modules.gameObjects.gamePieces.PieceColor;
import chess.modules.gameObjects.gamePieces.PieceMove;
import chess.modules.GameController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.List;

public class GameScreenController {

    private int mouseX, mouseY;
    private Board board;
    private GameController gameController;
    private ImageView temp;
    private List<PieceMove> legalMoves;
    private Pane tempPane;
    private Piece currPiece;

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
            else {
                this.currPiece = board.getPieceFromImage(temp);
                this.legalMoves = gameController.checkLegalMoves(currPiece);
                Integer col = GridPane.getColumnIndex((ImageView) mouseEvent.getTarget()), row = GridPane.getRowIndex((ImageView) mouseEvent.getTarget());
                tempPane = (Pane)boardGrid.getChildren().get(row * 8 + col);
                tempPane.setStyle("-fx-background-color:#00FF00;");
            }
        }
        else if(mouseEvent.getTarget() instanceof Pane) {
            if(temp != null) {
                boardGrid.getChildren().remove(temp);
                Integer col = GridPane.getColumnIndex((Pane) mouseEvent.getTarget()), row = GridPane.getRowIndex((Pane) mouseEvent.getTarget());
                boardGrid.add(temp, col, row);
                Piece tempPiece = gameController.movePiece(temp, col, row);
                if(tempPiece != null) {
                    boardGrid.getChildren().remove(tempPiece.getImage());
                }
                temp = null;
                tempPane.setStyle(getPaneDefaultColor(tempPane) == PieceColor.BLACK ? "-fx-background-color: #7ebffe;" : "-fx-background-color: #ffffff;");
            }
        }
    }

    private void highlightPane(Pane pane) {

    }

    private PieceColor getPaneDefaultColor(Pane pane) {
        Integer col = GridPane.getColumnIndex(pane), row = GridPane.getRowIndex(pane);
        return col % 2 == row % 2 ? PieceColor.WHITE : PieceColor.BLACK;
    }
}