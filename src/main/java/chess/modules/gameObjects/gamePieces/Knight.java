package chess.modules.gameObjects.gamePieces;

import chess.modules.gameObjects.Board;
import chess.modules.gameObjects.pieceMove.PieceMove;
import chess.modules.gameObjects.pieceMove.TakePieceMove;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    public List<PieceMove> getAllPossibleMoves(Board board) {
        return getPossibleMoves(board);
    }

    private boolean moveIsBlocked(PieceMove testMove, Board board) {
        Piece pieceOnBoard = board.getPieceOnBoard(testMove.getColumnPos(),testMove.getRowPos());
        return pieceOnBoard != null;
    }

    protected List<PieceMove> getPossibleMoves(Board board) {
        ArrayList<PieceMove> possibleMoves = new ArrayList<>();

        possibleMoves.add(getPossibleMove(new PieceMove(columnPos + 2, rowPos + 1, this), board));
        possibleMoves.add(getPossibleMove(new PieceMove(columnPos - 2, rowPos + 1, this), board));
        possibleMoves.add(getPossibleMove(new PieceMove(columnPos + 1, rowPos + 2, this), board));
        possibleMoves.add(getPossibleMove(new PieceMove(columnPos - 1, rowPos + 2, this), board));
        possibleMoves.add(getPossibleMove(new PieceMove(columnPos + 2, rowPos - 1, this), board));
        possibleMoves.add(getPossibleMove(new PieceMove(columnPos - 2, rowPos - 1, this), board));
        possibleMoves.add(getPossibleMove(new PieceMove(columnPos + 1, rowPos - 2, this), board));
        possibleMoves.add(getPossibleMove(new PieceMove(columnPos - 1, rowPos - 2, this), board));

        return possibleMoves.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private PieceMove getPossibleMove(PieceMove testMove, Board board) {
        if(testMove.getColumnPos() > 7 || testMove.getColumnPos() < 0 || testMove.getRowPos() > 7 || testMove.getRowPos() < 0)
            return null;
        boolean isBlocked = moveIsBlocked(testMove, board);
        if(testMove.getColumnPos() >= 0 && testMove.getColumnPos() <= 7 && testMove.getRowPos() >= 0 && testMove.getRowPos() <= 7
                && !isBlocked)
            return testMove;
        else if(isBlocked)
            return new TakePieceMove(testMove.getColumnPos(), testMove.getRowPos(), this, board.getPieceOnBoard(testMove.getColumnPos(), testMove.getRowPos()));
        return null;
    }
}
