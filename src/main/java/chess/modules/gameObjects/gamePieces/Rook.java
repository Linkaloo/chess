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
        return getPossibleMoves(board);
    }

    private boolean moveIsBlocked(PieceMove testMove, Board board) {
        return board.getPieceOnBoard(testMove.getColumnPos(),testMove.getRowPos()) != null;
    }


    private List<PieceMove> getPossibleMoves(Board board) {
        ArrayList<PieceMove> possibleMoves = new ArrayList<>();
        boolean isBlocked = false;
        int moveColumn = columnPos;
        int moveRow = rowPos;
        PieceMove testMove;


        while(!isBlocked && moveRow >= 0) {
            testMove = new PieceMove(columnPos, moveRow - 1);
            isBlocked = moveIsBlocked(testMove, board);
            if(!isBlocked || !(board.getPieceOnBoard(testMove.getColumnPos(),testMove.getRowPos()).getPieceColor().equals(pieceColor)))
                possibleMoves.add(testMove);
            moveRow--;
        }

        moveRow = rowPos;
        isBlocked = false;
        while(!isBlocked && moveRow <= 7) {
            testMove = new PieceMove(columnPos, moveRow + 1);
            isBlocked = moveIsBlocked(testMove, board);
            if(!isBlocked || !(board.getPieceOnBoard(testMove.getColumnPos(),testMove.getRowPos()).getPieceColor().equals(pieceColor)))
                possibleMoves.add(testMove);
            moveRow++;
        }

        isBlocked = false;
        while(!isBlocked && moveColumn >= 0) {
            testMove = new PieceMove(moveColumn - 1, rowPos);
            isBlocked = moveIsBlocked(testMove, board);
            if(!isBlocked || !(board.getPieceOnBoard(testMove.getColumnPos(),testMove.getRowPos()).getPieceColor().equals(pieceColor)))
                possibleMoves.add(testMove);
            moveColumn--;
        }

        moveColumn = columnPos;
        isBlocked = false;
        while(!isBlocked && moveColumn <= 7) {
            testMove = new PieceMove(moveColumn + 1, rowPos);
            isBlocked = moveIsBlocked(testMove, board);
            if(!isBlocked || !(board.getPieceOnBoard(testMove.getColumnPos(),testMove.getRowPos()).getPieceColor().equals(pieceColor)))
                possibleMoves.add(testMove);
            moveColumn++;
        }

        return possibleMoves
                .stream()
                .filter(pieceMove -> pieceMove.getColumnPos() >= 0 && pieceMove.getColumnPos() <= 7 && pieceMove.getRowPos() >= 0 && pieceMove.getRowPos() <= 7)
                .collect(Collectors.toList());
    }

}
