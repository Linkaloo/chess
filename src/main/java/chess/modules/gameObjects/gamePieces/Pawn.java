package chess.modules.gameObjects.gamePieces;

import chess.modules.GameController;
import chess.modules.gameObjects.Board;
import chess.modules.gameObjects.pieceMove.EnPassantPieceMove;
import chess.modules.gameObjects.pieceMove.PawnInitialMove;
import chess.modules.gameObjects.pieceMove.PieceMove;
import chess.modules.gameObjects.pieceMove.TakePieceMove;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    private boolean initialMove;
    private boolean promoted;
    private boolean enpassantable;
    private GameController gameController;

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

        enpassantable = (pieceColor == PieceColor.WHITE && rowPos + 2 == currRow) || (pieceColor == PieceColor.BLACK && rowPos - 2 == currRow);
        initialMove = false;
        promoted = (pieceColor == PieceColor.WHITE && rowPos == 0) || (pieceColor == PieceColor.BLACK && rowPos == 7);
    }

    public void setEnpassantable(boolean enpassantable) {
        this.enpassantable = enpassantable;
    }

    @Override
    public List<PieceMove> getLegalMoves(Board board) {
        List<PieceMove> possibleMoves = getPossibleMoves(board);
        possibleMoves.removeIf(pieceMove -> pieceMove instanceof TakePieceMove && pieceMove.getCurrPiece().getPieceColor() == ((TakePieceMove) pieceMove).getTakePiece().getPieceColor());
        return possibleMoves;
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

            PieceMove testMove = new PieceMove(columnPos, rowPos - 1, this);
            if(!moveIsBlocked(testMove, board))
                possibleMoves.add(testMove);

            if (initialMove && !moveIsBlocked(testMove, board)) {
                testMove = new PieceMove(columnPos, rowPos - 2, this);
                if(!moveIsBlocked(testMove, board))
                    possibleMoves.add(new PawnInitialMove(testMove.getColumnPos(), testMove.getRowPos(), this));
            }


            if (columnPos >= 0 && rightPiece != null) {
                possibleMoves.add(new TakePieceMove(columnPos + 1, rowPos - 1, this, rightPiece));
            }

            if(rightPawn instanceof Pawn && ((Pawn) rightPawn).isEnpassantable()) {
                possibleMoves.add(new EnPassantPieceMove(columnPos + 1, rowPos - 1, this, (Pawn) rightPawn));
            }

            if (columnPos <= 7 && leftPiece != null) {
                possibleMoves.add(new TakePieceMove(columnPos - 1, rowPos - 1, this, leftPiece));
            }

            if(leftPawn instanceof Pawn && ((Pawn) leftPawn).isEnpassantable()) {
                possibleMoves.add(new EnPassantPieceMove(columnPos - 1, rowPos - 1, this, (Pawn) leftPawn));
            }

        } else if (pieceColor.equals(PieceColor.BLACK) && rowPos < 7) {

            Piece rightPiece = board.getPieceOnBoard(columnPos + 1, rowPos + 1);
            Piece leftPiece = board.getPieceOnBoard(columnPos - 1, rowPos + 1);

            Piece rightPawn = board.getPieceOnBoard(columnPos + 1, rowPos);
            Piece leftPawn = board.getPieceOnBoard(columnPos - 1, rowPos);

            PieceMove testMove = new PieceMove(columnPos, rowPos + 1, this);
            if(!moveIsBlocked(testMove, board))
                possibleMoves.add(testMove);

            if (initialMove && !moveIsBlocked(testMove, board)) {
                testMove = new PieceMove(columnPos, rowPos + 2, this);
                if(!moveIsBlocked(testMove, board))
                    possibleMoves.add(new PawnInitialMove(testMove.getColumnPos(), testMove.getRowPos(), this));
            }

            if (columnPos >= 0 && rightPiece != null) {
                possibleMoves.add(new TakePieceMove(columnPos + 1, rowPos + 1, this, rightPiece));
            }

            if(rightPawn instanceof Pawn && ((Pawn) rightPawn).isEnpassantable()) {
                possibleMoves.add(new EnPassantPieceMove(columnPos + 1, rowPos + 1, this, (Pawn) rightPawn));
            }

            if (columnPos <= 7 && leftPiece != null) {
                possibleMoves.add(new TakePieceMove(columnPos - 1, rowPos + 1, this, leftPiece));
            }

            if(leftPawn instanceof Pawn && ((Pawn) leftPawn).isEnpassantable()) {
                possibleMoves.add(new EnPassantPieceMove(columnPos - 1, rowPos + 1, this, (Pawn) leftPawn));
            }

        }
        return possibleMoves;
    }
}

