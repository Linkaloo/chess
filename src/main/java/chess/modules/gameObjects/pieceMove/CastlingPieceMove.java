package chess.modules.gameObjects.pieceMove;

import chess.modules.gameObjects.gamePieces.Piece;
import chess.modules.gameObjects.gamePieces.Rook;

public class CastlingPieceMove extends PieceMove {

    private Rook ownRook;

    public CastlingPieceMove(int columnPos, int rowPos, Piece currPiece, Rook ownRook) {
        super(columnPos, rowPos, currPiece);
        this.ownRook = ownRook;
    }

    public Rook getOwnRook() {
        return ownRook;
    }
}
