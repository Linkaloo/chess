package chess.modules.gameObjects.gamePieces;

import chess.modules.gameObjects.Board;
import chess.modules.gameObjects.pieceMove.PieceMove;
import chess.modules.gameObjects.pieceMove.TakePieceMove;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Bishop extends Piece {

    public Bishop(int columnPos, int rowPos, PieceColor pieceColor) {
        super(columnPos, rowPos, pieceColor);

        File file;
        switch (pieceColor) {
            case BLACK: file = new File(String.valueOf(getClass().getClassLoader().getResource("assets/B.B.png")));
                break;
            case WHITE: file = new File(String.valueOf(getClass().getClassLoader().getResource("assets/W.B.png")));
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
        boolean isBlocked = false;
        int moveColumn = columnPos;
        int moveRow = rowPos;
        PieceMove testMove;

        while(!isBlocked && moveColumn >= 0 && moveRow >= 0) {
            testMove = new PieceMove(moveColumn - 1, moveRow - 1, this);
            isBlocked = moveIsBlocked(testMove, board);
            if(!isBlocked)
                possibleMoves.add(testMove);
            else
                possibleMoves.add(new TakePieceMove(testMove.getColumnPos(), testMove.getRowPos(), this, board.getPieceOnBoard(testMove.getColumnPos(), testMove.getRowPos())));
            moveColumn--;
            moveRow--;
        }


        moveColumn = columnPos;
        moveRow = rowPos;
        isBlocked = false;
        while(!isBlocked && moveColumn <= 7 && moveRow >= 0) {
            testMove = new PieceMove(moveColumn + 1, moveRow - 1, this);
            isBlocked = moveIsBlocked(testMove, board);
            if(!isBlocked)
                possibleMoves.add(testMove);
            else
                possibleMoves.add(new TakePieceMove(testMove.getColumnPos(), testMove.getRowPos(), this, board.getPieceOnBoard(testMove.getColumnPos(), testMove.getRowPos())));
            moveColumn++;
            moveRow--;
        }

        moveColumn = columnPos;
        moveRow = rowPos;
        isBlocked = false;
        while(!isBlocked && moveColumn >= 0 && moveRow <= 7) {
            testMove = new PieceMove(moveColumn - 1, moveRow + 1, this);
            isBlocked = moveIsBlocked(testMove, board);
            if(!isBlocked)
                possibleMoves.add(testMove);
            else
                possibleMoves.add(new TakePieceMove(testMove.getColumnPos(), testMove.getRowPos(), this, board.getPieceOnBoard(testMove.getColumnPos(), testMove.getRowPos())));
            moveColumn--;
            moveRow++;
        }

        moveColumn = columnPos;
        moveRow = rowPos;
        isBlocked = false;
        while(!isBlocked && moveColumn <= 7 && moveRow <= 7) {
            testMove = new PieceMove(moveColumn + 1, moveRow + 1, this);
            isBlocked = moveIsBlocked(testMove, board);
            if(!isBlocked)
                possibleMoves.add(testMove);
            else
                possibleMoves.add(new TakePieceMove(testMove.getColumnPos(), testMove.getRowPos(), this, board.getPieceOnBoard(testMove.getColumnPos(), testMove.getRowPos())));
            moveColumn++;
            moveRow++;
        }

        return possibleMoves
                .stream()
                .filter(pieceMove -> pieceMove.getColumnPos() >= 0 && pieceMove.getColumnPos() <= 7 && pieceMove.getRowPos() >= 0 && pieceMove.getRowPos() <= 7)
                .collect(Collectors.toList());
    }
}
