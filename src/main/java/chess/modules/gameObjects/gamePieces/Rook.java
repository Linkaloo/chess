package chess.modules.gameObjects.gamePieces;

import chess.modules.gameObjects.Board;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Rook extends Piece{

    public Rook(int columnPos, int rowPos, PieceColor pieceColor) {
        super(columnPos, rowPos, pieceColor);

        File file;
        switch (pieceColor) {
            case BLACK: file = new File(String.valueOf(getClass().getClassLoader().getResource("assets/B.R.png")));
                break;
            case WHITE: file = new File(String.valueOf(getClass().getClassLoader().getResource("assets/W.R.png")));
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
                .filter(pieceMove -> checkMove(pieceMove, board))
                .collect(Collectors.toList());
    }


    private boolean checkMove(PieceMove pieceMove, Board board) {
        boolean moveIsBlocked = false;
        Piece boardPiece = board.getPieceOnBoard(pieceMove.getColumnPos(), pieceMove.getRowPos());
        moveIsBlocked = checkBlocked(pieceMove, boardPiece);

        if(boardPiece == null) {
            return true;
        }
        else if(boardPiece.getPieceColor() != pieceColor)
            return true;
        else
            return false;
    }

    private boolean checkBlocked(PieceMove pieceMove, Piece boardPiece) {
        return true;
    }

    private List<PieceMove> getPossibleMoves(Board board) {
        ArrayList<PieceMove> possibleMoves = new ArrayList<>();

        for(int i = 1; i < 8; i++) {
            possibleMoves.add(new PieceMove(columnPos + i, rowPos));
            possibleMoves.add(new PieceMove(columnPos - i, rowPos));

            possibleMoves.add(new PieceMove(columnPos, rowPos + i));
            possibleMoves.add(new PieceMove(columnPos , rowPos - i));
        }

        return possibleMoves
                .stream()
                .filter(pieceMove -> pieceMove.getColumnPos() >= 0 && pieceMove.getColumnPos() <= 7 && pieceMove.getRowPos() >= 0 && pieceMove.getRowPos() <= 7)
                .collect(Collectors.toList());
    }

}
