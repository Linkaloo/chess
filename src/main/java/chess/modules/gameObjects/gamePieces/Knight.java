package chess.modules.gameObjects.gamePieces;

import chess.modules.gameObjects.Board;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Knight extends Piece {
    public Knight(int columnPos, int rowPos, PieceColor pieceColor) {
        super(columnPos, rowPos, pieceColor);

        File file;
        switch (pieceColor) {
            case BLACK: file = new File(String.valueOf(getClass().getClassLoader().getResource("assets/B.Kn.png")));
                break;
            case WHITE: file = new File(String.valueOf(getClass().getClassLoader().getResource("assets/W.Kn.png")));
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

        possibleMoves.add(new PieceMove(columnPos + 2, rowPos + 1));
        possibleMoves.add(new PieceMove(columnPos - 2, rowPos + 1));

        possibleMoves.add(new PieceMove(columnPos + 2, rowPos - 1));
        possibleMoves.add(new PieceMove(columnPos - 2, rowPos - 1));

        possibleMoves.add(new PieceMove(columnPos + 1, rowPos + 2));
        possibleMoves.add(new PieceMove(columnPos - 1, rowPos + 2));

        possibleMoves.add(new PieceMove(columnPos + 1, rowPos - 2));
        possibleMoves.add(new PieceMove(columnPos - 1, rowPos - 2));

        return possibleMoves
                .stream()
                .filter(pieceMove -> pieceMove.getColumnPos() >= 0 && pieceMove.getColumnPos() <= 7 && pieceMove.getRowPos() >= 0 && pieceMove.getRowPos() <= 7)
                .collect(Collectors.toList());
    }

}
