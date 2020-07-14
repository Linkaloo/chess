package chess.modules.gameObjects.gamePieces;

import chess.modules.gameObjects.Board;
import chess.modules.gameObjects.pieceMove.PieceMove;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class King extends Piece {

    private boolean initialMove;

    public King(int columnPos, int rowPos, PieceColor pieceColor) {
        super(columnPos, rowPos, pieceColor);
        initialMove = true;

        File file;
        switch (pieceColor) {
            case BLACK: file = new File(String.valueOf(getClass().getClassLoader().getResource("assets/B.K.png")));
                break;
            case WHITE: file = new File(String.valueOf(getClass().getClassLoader().getResource("assets/W.K.png")));
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
        List<PieceMove> possibleMoves = getPossibleMoves(board);

        return possibleMoves
                .stream()
                .filter(pieceMove -> checkMove(pieceMove, board)).collect(Collectors.toList());
    }

    private boolean checkMove(PieceMove pieceMove, Board board) {
        Piece boardPiece = board.getPieceOnBoard(pieceMove.getColumnPos(), pieceMove.getRowPos());
        if(boardPiece == null)
            return true;
        else if(boardPiece != null && boardPiece.getPieceColor() != pieceColor)
            return true;
        else
            return false;
    }

    @Override
    public void move(PieceMove move) {
        super.move(move);
        initialMove = false;
    }

    private List<PieceMove> getPossibleMoves(Board board) {
        ArrayList<PieceMove> possibleMoves = new ArrayList<>();

        possibleMoves.add(new PieceMove(columnPos, rowPos + 1, this));
        possibleMoves.add(new PieceMove(columnPos + 1, rowPos + 1, this));
        possibleMoves.add(new PieceMove(columnPos - 1, rowPos + 1, this));

        possibleMoves.add(new PieceMove(columnPos, rowPos - 1, this));
        possibleMoves.add(new PieceMove(columnPos + 1, rowPos - 1, this));
        possibleMoves.add(new PieceMove(columnPos - 1, rowPos - 1, this));

        possibleMoves.add(new PieceMove(columnPos + 1, rowPos, this));
        possibleMoves.add(new PieceMove(columnPos - 1, rowPos, this));

        if(initialMove) {
            possibleMoves.add(new PieceMove(columnPos + 2, rowPos, this));
            possibleMoves.add(new PieceMove(columnPos - 2, rowPos, this));
        }

        return possibleMoves
                .stream()
                .filter(pieceMove -> pieceMove.getColumnPos() >= 0 && pieceMove.getColumnPos() <= 7 && pieceMove.getRowPos() >= 0 && pieceMove.getRowPos() <= 7)
                .collect(Collectors.toList());
    }

}
