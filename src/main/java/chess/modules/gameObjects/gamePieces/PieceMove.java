package chess.modules.gameObjects.gamePieces;

public class PieceMove {

    private int columnPos;
    private int rowPos;

    public PieceMove(int columnPos, int rowPos) {
        this.columnPos = columnPos;
        this.rowPos = rowPos;
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
