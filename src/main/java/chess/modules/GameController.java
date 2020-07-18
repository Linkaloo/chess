package chess.modules;

import chess.modules.gameObjects.Board;
import chess.modules.gameObjects.Player;
import chess.modules.gameObjects.gamePieces.*;
import chess.modules.gameObjects.pieceMove.PieceMove;
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

    private List<PieceMove> legalMoves = new ArrayList<>();

    @FXML
    private GridPane boardGrid;

    public GameController(Board board) {
        player1 = new Player(PieceColor.WHITE,false);
        player2 = new Player(PieceColor.BLACK,false);

        this.board = board;

        playersMove = 0;
        turnNumber = 1;
    }

    public void movePiece(PieceMove pieceMove) {
        playersMove++;
        board.movePiece(pieceMove);

    }

    // updates the value isInCheck inside king
    public void updateKingInCheck(Piece movedPiece) {
        King oppKing = board.getOppKing(movedPiece.getPieceColor());
        List<PieceMove> movedPieceLegalMoves = movedPiece.getLegalMoves(board);

        oppKing.setIsInCheck(movedPieceLegalMoves.contains(new PieceMove(oppKing.getColumnPos(), oppKing.getRowPos())));
    }

    public void updatePawnsEnpassant() {
        if(playersMove % 2 == 0) {
            List<Pawn> whitePawns = board.getPawns(PieceColor.WHITE);
            whitePawns.forEach(pawn -> pawn.setEnpassantable(false));
        }
        if(playersMove % 2 == 1) {
            List<Pawn> blackPawns = board.getPawns(PieceColor.BLACK);
            blackPawns.forEach(pawn -> pawn.setEnpassantable(false));
        }
    }

    public List<PieceMove> getLegalMoves(Piece piece) {
        this.legalMoves = piece.getLegalMoves(board);
        return this.legalMoves;
    }

    public PieceMove getLegalMove(int row, int col) {
        return this.legalMoves.stream().filter(move -> move.getRowPos() == row && move.getColumnPos() == col).findFirst().orElse(null);
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
