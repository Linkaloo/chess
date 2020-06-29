package chess.modules;

import chess.modules.gameObjects.Board;
import chess.modules.gameObjects.Player;
import chess.modules.gameObjects.gamePieces.Piece;
import chess.modules.gameObjects.gamePieces.PieceColor;
import chess.modules.gameObjects.gamePieces.PieceMove;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    private Player player1;
    private Player player2;

    private int playersMove;
    private int turnNumber;

    private Board board;

    private List<PieceMove> moves;

    @FXML
    private GridPane boardGrid;

    public GameController(Board board) {
        player1 = new Player(PieceColor.WHITE,false);
        player2 = new Player(PieceColor.BLACK,false);

        this.board = board;

        playersMove = 0;
        turnNumber = 1;
    }

    public Piece movePiece(ImageView temp, Integer col, Integer row) {
        playersMove++;
        return board.movePiece(temp, col, row);

    }

    public List<PieceMove> checkLegalMoves(Piece piece) {
        return new ArrayList<>();
    }

    public boolean isLegalMove(PieceMove pieceMove) {
        return true;
    }

    public PieceColor checkTurn() {
        if(playersMove % 2 == 0) {
            return player1.getColor();
        }
        else {
            return player2.getColor();
        }
    }

}
