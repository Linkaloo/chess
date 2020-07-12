package chess.modules;

import chess.modules.gameObjects.Board;
import chess.modules.gameObjects.Player;
import chess.modules.gameObjects.gamePieces.*;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameController {

    private Player player1;
    private Player player2;

    private int playersMove;
    private int turnNumber;

    private Board board;

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

    public void updatePawnsEnpassant() {
        List<Pawn> whitePawns = board.getPawns(PieceColor.WHITE);
        List<Pawn> blackPawns = board.getPawns(PieceColor.BLACK);

        if(playersMove % 2 == 0) {
            whitePawns.forEach(pawn -> pawn.setEnpassantable(false));
        }
        if(playersMove % 2 == 1) {
            blackPawns.forEach(pawn -> pawn.setEnpassantable(false));
        }
    }

    public void endOfTurn(PieceColor pieceColor) {
        if(pieceColor.equals(PieceColor.BLACK))
            turnNumber++;
    }

    public List<PieceMove> getLegalMoves(Piece piece) {
        return piece.getLegalMoves(board);
    }

    public boolean isLegalMove(PieceMove pieceMove, Piece piece) {
        List<PieceMove> legalMoves = piece.getLegalMoves(board);

        return legalMoves.stream().anyMatch(move -> (move.getColumnPos() == pieceMove.getColumnPos() && move.getRowPos() == pieceMove.getRowPos()));
    }

    public boolean isPawnPromoted(Pawn pawn) {
        return pawn.isPromoted();
    }

    public PieceColor checkTurn() {
        if(playersMove % 2 == 0) {
            updatePawnsEnpassant();
            return player1.getColor();
        }
        else {
            updatePawnsEnpassant();
            return player2.getColor();
        }
    }

}
