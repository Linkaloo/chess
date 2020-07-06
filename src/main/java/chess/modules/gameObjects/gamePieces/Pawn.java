package chess.modules.gameObjects.gamePieces;

import chess.modules.gameObjects.Board;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Pawn extends Piece {

    private boolean initialMove;
    private boolean promoted;
    private boolean enpassantable;

    public Pawn(int columnPos, int rowPos, PieceColor pieceColor) {
        super(columnPos, rowPos, pieceColor);
        initialMove = true;
        promoted = false;
        enpassantable = false;

        File file;
        switch (pieceColor) {
            case BLACK:
                file = new File(String.valueOf(getClass().getClassLoader().getResource("assets/B.P.png")));
                break;
            case WHITE:
                file = new File(String.valueOf(getClass().getClassLoader().getResource("assets/W.P.png")));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + pieceColor);
        }
        Image imageFile = new Image(file.getPath());
        super.image = new ImageView();
        super.image.setImage(imageFile);
    }

    public boolean isPromoted() {
        return promoted;
    }

    public boolean isEnpassantable() {
        return enpassantable;
    }

    @Override
    public void move(PieceMove move) {
        int currRow = rowPos;
        super.move(move);
        initialMove = false;

        if (pieceColor == PieceColor.WHITE && rowPos + 2 == currRow)
            enpassantable = true;
        else if (pieceColor == PieceColor.BLACK && rowPos - 2 == currRow)
            enpassantable = true;
        else enpassantable = false;

        if (pieceColor == PieceColor.WHITE && rowPos == 0)
            promoted = true;
        else promoted = pieceColor == PieceColor.BLACK && rowPos == 7;
    }

    @Override
    public List<PieceMove> getLegalMoves(Board board) {
        return getPossibleMoves(board);
    }

    private boolean moveIsBlocked(PieceMove testMove, Board board) {
        return board.getPieceOnBoard(testMove.getColumnPos(), testMove.getRowPos()) != null;
    }

    private List<PieceMove> getPossibleMoves(Board board) {
        ArrayList<PieceMove> possibleMoves = new ArrayList<>();

        if (pieceColor.equals(PieceColor.WHITE) && rowPos > 0) {
            Piece rightPiece = board.getPieceOnBoard(columnPos + 1, rowPos - 1);
            Piece leftPiece = board.getPieceOnBoard(columnPos - 1, rowPos - 1);

            Piece rightPawn = board.getPieceOnBoard(columnPos + 1, rowPos);
            Piece leftPawn = board.getPieceOnBoard(columnPos - 1, rowPos);

            PieceMove testMove = new PieceMove(columnPos, rowPos - 1);
            if(!moveIsBlocked(testMove, board))
                possibleMoves.add(testMove);

            if (initialMove && !moveIsBlocked(testMove, board)) {
                testMove = new PieceMove(columnPos, rowPos - 2);
                if(!moveIsBlocked(testMove, board))
                    possibleMoves.add(testMove);
            }


            if (columnPos >= 0 && rightPiece != null && !rightPiece.getPieceColor().equals(pieceColor) || (rightPawn instanceof Pawn && ((Pawn) rightPawn).isEnpassantable())) {
                possibleMoves.add(new PieceMove(columnPos + 1, rowPos - 1));
            }

            if (columnPos <= 7 && leftPiece != null && !leftPiece.getPieceColor().equals(pieceColor) || (leftPawn instanceof Pawn && ((Pawn) leftPawn).isEnpassantable())) {
                possibleMoves.add(new PieceMove(columnPos - 1, rowPos - 1));
            }
        } else if (pieceColor.equals(PieceColor.BLACK) && rowPos < 7) {
            Piece rightPiece = board.getPieceOnBoard(columnPos + 1, rowPos + 1);
            Piece leftPiece = board.getPieceOnBoard(columnPos - 1, rowPos + 1);

            Piece rightPawn = board.getPieceOnBoard(columnPos + 1, rowPos);
            Piece leftPawn = board.getPieceOnBoard(columnPos - 1, rowPos);

            PieceMove testMove = new PieceMove(columnPos, rowPos + 1);
            if(!moveIsBlocked(testMove, board))
                possibleMoves.add(testMove);

            if (initialMove && !moveIsBlocked(testMove, board)) {
                testMove = new PieceMove(columnPos, rowPos + 2);
                if(!moveIsBlocked(testMove, board))
                    possibleMoves.add(testMove);
            }

            if (initialMove) {
                possibleMoves.add(new PieceMove(columnPos, rowPos + 2));
            }

            if (columnPos >= 0 && rightPiece != null && !rightPiece.getPieceColor().equals(pieceColor) || (rightPawn instanceof Pawn && ((Pawn) rightPawn).isEnpassantable())) {
                possibleMoves.add(new PieceMove(columnPos + 1, rowPos + 1));
            }

            if (columnPos <= 7 && leftPiece != null && !leftPiece.getPieceColor().equals(pieceColor) || (leftPawn instanceof Pawn && ((Pawn) leftPawn).isEnpassantable())) {
                possibleMoves.add(new PieceMove(columnPos - 1, rowPos + 1));
            }
        }
        return possibleMoves;
    }
}

