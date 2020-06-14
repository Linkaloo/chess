package chess.modules.gameObjects.gamePieces;

import javafx.scene.image.ImageView;

import java.util.List;

public abstract class Piece {

    protected PieceColor pieceColor;
    protected int columnPos;
    protected int rowPos;
    protected ImageView image;

    public Piece(int columnPos, int rowPos, PieceColor pieceColor) {
        this.pieceColor = pieceColor;
        this.columnPos = columnPos;
        this.rowPos = rowPos;
    }

    public abstract void move();
    public abstract List<PieceMove> checkMove();

    public int getColumnPos() {
        return columnPos;
    }

    public int getRowPos() {
        return rowPos;
    }

    public ImageView getImage() {
        return image;
    }
}
