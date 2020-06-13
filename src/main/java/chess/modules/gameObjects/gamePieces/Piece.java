package chess.modules.gameObjects.gamePieces;

public abstract class Piece {

    private int columnPos;
    private int rowPos;

    public Piece(int columnPos, int rowPos) {
        this.columnPos = columnPos;
        this.rowPos = rowPos;
    }

    public abstract void move();
    public abstract void checkMove();
}
