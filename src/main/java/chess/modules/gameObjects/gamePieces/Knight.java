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
        return getPossibleMoves(board);
    }


    private boolean moveIsBlocked(PieceMove testMove, Board board) {
        Piece pieceOnBoard = board.getPieceOnBoard(testMove.getColumnPos(),testMove.getRowPos());
        return pieceOnBoard != null;
    }

    private List<PieceMove> getPossibleMoves(Board board) {
        ArrayList<PieceMove> possibleMoves = new ArrayList<>();
        boolean isBlocked;
        PieceMove testMove;

        testMove = new PieceMove(columnPos + 2, rowPos + 1);
        isBlocked = moveIsBlocked(testMove, board);
        if((testMove.getColumnPos() >= 0 && testMove.getColumnPos() <= 7 && testMove.getRowPos() >= 0 && testMove.getRowPos() <= 7) &&
                (!isBlocked || !(board.getPieceOnBoard(testMove.getColumnPos(),testMove.getRowPos()).getPieceColor().equals(pieceColor))))
            possibleMoves.add(testMove);

        testMove = new PieceMove(columnPos - 2, rowPos + 1);
        isBlocked = moveIsBlocked(testMove, board);
        if((testMove.getColumnPos() >= 0 && testMove.getColumnPos() <= 7 && testMove.getRowPos() >= 0 && testMove.getRowPos() <= 7) &&
                (!isBlocked || !(board.getPieceOnBoard(testMove.getColumnPos(),testMove.getRowPos()).getPieceColor().equals(pieceColor))))
            possibleMoves.add(testMove);

        testMove = new PieceMove(columnPos + 2, rowPos - 1);
        isBlocked = moveIsBlocked(testMove, board);
        if((testMove.getColumnPos() >= 0 && testMove.getColumnPos() <= 7 && testMove.getRowPos() >= 0 && testMove.getRowPos() <= 7) &&
                (!isBlocked || !(board.getPieceOnBoard(testMove.getColumnPos(),testMove.getRowPos()).getPieceColor().equals(pieceColor))))
            possibleMoves.add(testMove);

        testMove = new PieceMove(columnPos - 2, rowPos - 1);
        isBlocked = moveIsBlocked(testMove, board);
        if((testMove.getColumnPos() >= 0 && testMove.getColumnPos() <= 7 && testMove.getRowPos() >= 0 && testMove.getRowPos() <= 7) &&
                (!isBlocked || !(board.getPieceOnBoard(testMove.getColumnPos(),testMove.getRowPos()).getPieceColor().equals(pieceColor))))
            possibleMoves.add(testMove);

        testMove = new PieceMove(columnPos + 1, rowPos + 2);
        isBlocked = moveIsBlocked(testMove, board);
        if((testMove.getColumnPos() >= 0 && testMove.getColumnPos() <= 7 && testMove.getRowPos() >= 0 && testMove.getRowPos() <= 7) &&
                (!isBlocked || !(board.getPieceOnBoard(testMove.getColumnPos(),testMove.getRowPos()).getPieceColor().equals(pieceColor))))
            possibleMoves.add(testMove);

        testMove = new PieceMove(columnPos - 1, rowPos + 2);
        isBlocked = moveIsBlocked(testMove, board);
        if((testMove.getColumnPos() >= 0 && testMove.getColumnPos() <= 7 && testMove.getRowPos() >= 0 && testMove.getRowPos() <= 7) &&
                (!isBlocked || !(board.getPieceOnBoard(testMove.getColumnPos(),testMove.getRowPos()).getPieceColor().equals(pieceColor))))
            possibleMoves.add(testMove);

        testMove = new PieceMove(columnPos + 1, rowPos - 2);
        isBlocked = moveIsBlocked(testMove, board);
        if((testMove.getColumnPos() >= 0 && testMove.getColumnPos() <= 7 && testMove.getRowPos() >= 0 && testMove.getRowPos() <= 7) &&
                (!isBlocked || !(board.getPieceOnBoard(testMove.getColumnPos(),testMove.getRowPos()).getPieceColor().equals(pieceColor))))
            possibleMoves.add(testMove);

        testMove = new PieceMove(columnPos - 1, rowPos - 2);
        isBlocked = moveIsBlocked(testMove, board);
        if((testMove.getColumnPos() >= 0 && testMove.getColumnPos() <= 7 && testMove.getRowPos() >= 0 && testMove.getRowPos() <= 7) &&
                (!isBlocked || !(board.getPieceOnBoard(testMove.getColumnPos(),testMove.getRowPos()).getPieceColor().equals(pieceColor))))
            possibleMoves.add(testMove);

        return possibleMoves
                .stream()
                .filter(pieceMove -> pieceMove.getColumnPos() >= 0 && pieceMove.getColumnPos() <= 7 && pieceMove.getRowPos() >= 0 && pieceMove.getRowPos() <= 7)
                .collect(Collectors.toList());
    }

}
