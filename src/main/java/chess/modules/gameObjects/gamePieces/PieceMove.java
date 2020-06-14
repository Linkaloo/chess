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
}
