package chess.modules.gameObjects.gamePieces;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.ArrayList;

public class Pawn extends Piece {

    private boolean initialMove;

    public Pawn(int columnPos, int rowPos, PieceColor pieceColor) {
        super(columnPos, rowPos, pieceColor);
        initialMove = true;

        File file;
        switch (pieceColor) {
            case BLACK: file = new File(String.valueOf(getClass().getClassLoader().getResource("assets/B.P.png")));
            break;
            case WHITE: file = new File(String.valueOf(getClass().getClassLoader().getResource("assets/W.P.png")));
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + pieceColor);
        }
        Image imageFile = new Image(file.getPath());
        super.image = new ImageView();
        super.image.setImage(imageFile);
    }

    @Override
    public ArrayList<PieceMove> getPossibleMoves() {
        ArrayList<PieceMove> pieceMoves = new ArrayList<>();

        if(pieceColor == PieceColor.WHITE) {
            pieceMoves.add(new PieceMove(columnPos, rowPos - 1));

            if (initialMove) {
                pieceMoves.add(new PieceMove(columnPos, rowPos - 2));
            } else {
                if (pieceMoves.size() == 2) {
                    pieceMoves.remove(1);
                    initialMove = false;
                }
            }
        }
        else {
            pieceMoves.add(new PieceMove(columnPos, rowPos + 1));

            if (initialMove) {
                pieceMoves.add(new PieceMove(columnPos, rowPos + 2));
            } else {
                if (pieceMoves.size() == 2) {
                    pieceMoves.remove(1);
                    initialMove = false;
                }
            }
        }

        return pieceMoves;
    }
}
