package chess.modules.gameObjects.gamePieces;

import javafx.scene.image.ImageView;

public abstract class Piece {

    private int columnPos;
    private int rowPos;
    private ImageView image;

    public Piece(int columnPos, int rowPos) {
        this.columnPos = columnPos;
        this.rowPos = rowPos;
    }

    public abstract void move();
    public abstract void checkMove();
}
