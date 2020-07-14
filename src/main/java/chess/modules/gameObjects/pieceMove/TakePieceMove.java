package chess.modules.gameObjects.pieceMove;

import chess.modules.gameObjects.gamePieces.Piece;

public class TakePieceMove extends PieceMove{

    private Piece takePiece;

    public TakePieceMove(int columnPos, int rowPos, Piece currPiece, Piece takePiece) {
        super(columnPos, rowPos, currPiece);
        this.takePiece = takePiece;
    }

    public Piece getTakePiece() {
        return takePiece;
    }
}
