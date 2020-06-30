package chess.modules.gameObjects.gamePieces;

import chess.modules.gameObjects.Board;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<PieceMove> getLegalMoves(Board board) {
        // TODO check which bishop moves are valid
        ArrayList<PieceMove> legalMoves = new ArrayList<>();
        //checks possible moves and stores into legal moves
        List<PieceMove> possibleMoves = getPossibleMoves(board);


        return possibleMoves;
    }

    private List<PieceMove> getPossibleMoves(Board board) {
        ArrayList<PieceMove> possibleMoves = new ArrayList<>();

        for(int i = 1; i < 8; i++) {
            possibleMoves.add(new PieceMove(columnPos + i, rowPos));
            possibleMoves.add(new PieceMove(columnPos - i, rowPos));

            possibleMoves.add(new PieceMove(columnPos, rowPos + i));
            possibleMoves.add(new PieceMove(columnPos , rowPos - i));

            possibleMoves.add(new PieceMove(columnPos + i, rowPos + i));
            possibleMoves.add(new PieceMove(columnPos - i, rowPos + i));

            possibleMoves.add(new PieceMove(columnPos + i, rowPos - i));
            possibleMoves.add(new PieceMove(columnPos - i, rowPos - i));
        }

        return possibleMoves
                .stream()
                .filter(pieceMove -> pieceMove.getColumnPos() >= 0 && pieceMove.getColumnPos() <= 7 && pieceMove.getRowPos() >= 0 && pieceMove.getRowPos() <= 7)
                .collect(Collectors.toList());
    }
}
