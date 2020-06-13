package chess.modules.gameObjects;

public class Piece {

    private boolean active;
    private int columnPos;
    private int rowPos;

    public Piece(int columnPos, int rowPos) {
        active = true;
        this.columnPos = columnPos;
        this.rowPos = rowPos;
    }

    public void updatePos(int columnPos, int rowPos) {
        this.columnPos = columnPos;
        this.rowPos = rowPos;
    }

    public void updateActive(boolean active) {
        this.active = active;
    }

}
