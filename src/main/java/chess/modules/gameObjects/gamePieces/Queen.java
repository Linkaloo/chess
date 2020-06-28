package chess.modules.gameObjects.gamePieces;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.List;

public class Queen extends Piece {
    public Queen(int columnPos, int rowPos, PieceColor pieceColor) {
        super(columnPos, rowPos, pieceColor);

        File file;
        switch (pieceColor) {
            case BLACK: file = new File(String.valueOf(getClass().getClassLoader().getResource("assets/B.Q.png")));
                break;
            case WHITE: file = new File(String.valueOf(getClass().getClassLoader().getResource("assets/W.Q.png")));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + pieceColor);
        }
        Image imageFile = new Image(file.getPath());
        super.image = new ImageView();
        super.image.setImage(imageFile);
    }

    @Override
    public List<PieceMove> checkMove() {
        // TODO check which queen moves are valid
        return null;
    }
}
