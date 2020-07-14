package chess.modules.gameObjects.pieceMove;

import chess.modules.gameObjects.gamePieces.Pawn;
import chess.modules.gameObjects.gamePieces.Piece;

public class EnPassantPieceMove extends PieceMove{

    private Pawn opponentPawn;

    public EnPassantPieceMove(int columnPos, int rowPos, Piece currPiece, Pawn opponentPawn) {
        super(columnPos, rowPos, currPiece);
        this.opponentPawn = opponentPawn;
    }

    public Pawn getOpponentPawn() {
        return opponentPawn;
    }
}
