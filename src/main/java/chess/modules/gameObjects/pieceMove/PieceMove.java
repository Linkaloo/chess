package chess.modules.gameObjects.pieceMove;


import chess.modules.gameObjects.gamePieces.King;
import chess.modules.gameObjects.gamePieces.Pawn;
import chess.modules.gameObjects.gamePieces.Piece;

public class PieceMove {

    private int columnPos;
    private int rowPos;
    private int initialColumnPos;
    private int initialRowPos;
    private Piece currPiece;
    private boolean initialMove = false;
    private boolean enpassantable = false;

    public PieceMove(int columnPos, int rowPos) {
        this.columnPos = columnPos;
        this.rowPos = rowPos;
    }

    public PieceMove(int columnPos, int rowPos, Piece piece) {
        this.columnPos = columnPos;
        this.rowPos = rowPos;
        this.currPiece = piece;
        this.initialColumnPos = piece.getColumnPos();
        this.initialRowPos = piece.getRowPos();
        if(piece instanceof King)
            this.initialMove = ((King) piece).isInitialMove();
        else if(piece instanceof Pawn) {
            this.initialMove = ((Pawn) piece).isInitialMove();
            this.enpassantable = ((Pawn) piece).isEnpassantable();
        }
    }

    public void setColumnPos(int columnPos) {
        this.columnPos = columnPos;
    }

    public void setRowPos(int rowPos) {
        this.rowPos = rowPos;
    }

    public int getColumnPos() {
        return columnPos;
    }

    public int getRowPos() {
        return rowPos;
    }

    public int getInitialColumnPos() {
        return initialColumnPos;
    }

    public int getInitialRowPos() {
        return initialRowPos;
    }

    public boolean isInitialMove() {
        return initialMove;
    }

    public Piece getCurrPiece() {
        return currPiece;
    }

    public boolean isEnpassantable() {
        return enpassantable;
    }

    @Override
    public boolean equals(Object pieceMove) {
        if(pieceMove == this)
            return true;
        else if(!(pieceMove instanceof PieceMove))
            return false;
        else
            return ((PieceMove) pieceMove).columnPos == this.columnPos && ((PieceMove) pieceMove).rowPos == this.rowPos;
    }
}
