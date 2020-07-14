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
        return getPossibleMoves(board);
    }

    @Override
    public List<PieceMove> getAllPossibleMoves(Board board) {
        return getPossibleMoves(board);
    }

    @Override
    public void move(PieceMove move) {
        super.move(move);
        initialMove = false;
    }

    private boolean checkNextToKing(PieceMove pieceMove) {
        return pieceMove.getColumnPos() == columnPos && pieceMove.getRowPos() == rowPos || pieceMove.getColumnPos() == columnPos + 1 && pieceMove.getRowPos() == rowPos || pieceMove.getColumnPos() == columnPos - 1 && pieceMove.getRowPos() == rowPos ||
                pieceMove.getColumnPos() == columnPos && pieceMove.getRowPos() == rowPos + 1 || pieceMove.getColumnPos() == columnPos + 1 && pieceMove.getRowPos() == rowPos + 1 || pieceMove.getColumnPos() == columnPos - 1 && pieceMove.getRowPos() == rowPos + 1 ||
                pieceMove.getColumnPos() == columnPos && pieceMove.getRowPos() == rowPos - 1 || pieceMove.getColumnPos() == columnPos + 1 && pieceMove.getRowPos() == rowPos - 1 || pieceMove.getColumnPos() == columnPos - 1 && pieceMove.getRowPos() == rowPos - 1;
    }

    private List<PieceMove> getPossibleMoves(Board board) {
        List<Piece> oppositePieces = board.getOppositeColorPieces(pieceColor);
        List<PieceMove> oppositePiecesCheckingMoves = new ArrayList<>();
        List<PieceMove> possibleMoves = new ArrayList<>();

        for (Piece piece: oppositePieces) {
            List<PieceMove> oppPieceLegalMoves = piece.getAllPossibleMoves(board);
            for (PieceMove oppPieceMove: oppPieceLegalMoves){
                if (checkNextToKing(oppPieceMove))
                    oppositePiecesCheckingMoves.add(oppPieceMove);
            }
        }

        possibleMoves.add(getPossibleMove(new PieceMove(columnPos, rowPos + 1, this), oppositePiecesCheckingMoves, board));
        possibleMoves.add(getPossibleMove(new PieceMove(columnPos + 1, rowPos + 1, this), oppositePiecesCheckingMoves, board));
        possibleMoves.add(getPossibleMove(new PieceMove(columnPos - 1, rowPos + 1, this), oppositePiecesCheckingMoves, board));

        possibleMoves.add(getPossibleMove(new PieceMove(columnPos, rowPos - 1, this), oppositePiecesCheckingMoves, board));
        possibleMoves.add(getPossibleMove(new PieceMove(columnPos + 1, rowPos - 1, this), oppositePiecesCheckingMoves, board));
        possibleMoves.add(getPossibleMove(new PieceMove(columnPos - 1, rowPos - 1, this), oppositePiecesCheckingMoves, board));

        possibleMoves.add(getPossibleMove(new PieceMove(columnPos + 1, rowPos, this), oppositePiecesCheckingMoves, board));
        possibleMoves.add(getPossibleMove(new PieceMove(columnPos - 1, rowPos, this), oppositePiecesCheckingMoves, board));

        if(initialMove) {
            possibleMoves.add(getPossibleMove(new PieceMove(columnPos + 2, rowPos, this), oppositePiecesCheckingMoves, board));
            possibleMoves.add(getPossibleMove(new PieceMove(columnPos - 2, rowPos, this), oppositePiecesCheckingMoves, board));
        }

        return possibleMoves.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private boolean containsOwnColor(PieceMove testMove, Board board) {
        return board.getPieceOnBoard(testMove.getColumnPos(), testMove.getRowPos()) != null && board.getPieceOnBoard(testMove.getColumnPos(), testMove.getRowPos()).getPieceColor() == pieceColor;
    }

    private PieceMove getPossibleMove(PieceMove testMove, List<PieceMove> oppPiecesMoves, Board board) {
        if(testMove.getColumnPos() > 7 || testMove.getColumnPos() < 0 || testMove.getRowPos() > 7 || testMove.getRowPos() < 0)
            return null;
        else if(testMove.getColumnPos() >= 0 && testMove.getColumnPos() <= 7 && testMove.getRowPos() >= 0 && testMove.getRowPos() <= 7 && !oppPiecesMoves.contains(testMove) && !containsOwnColor(testMove, board))
            return testMove;
        else
            return null;
    }
}
