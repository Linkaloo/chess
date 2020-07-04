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
        List<PieceMove> legalMoves;

        Piece blockingPiece1 = null;
        Piece blockingPiece2 = null;
        Piece blockingPiece3 = null;
        Piece blockingPiece4 = null;

        for (int i = 0; i < possibleMoves.size(); i++) {
            if (board.getPieceOnBoard(possibleMoves.get(i).getColumnPos(), possibleMoves.get(i).getRowPos()) != null && possibleMoves.get(i).getColumnPos() > columnPos) { // piece on left
                blockingPiece1 = board.getPieceOnBoard(possibleMoves.get(i).getColumnPos(), possibleMoves.get(i).getRowPos());
                break;
            }
        }

        for (int i = 0; i < possibleMoves.size(); i++) {
            if (board.getPieceOnBoard(possibleMoves.get(i).getColumnPos(), possibleMoves.get(i).getRowPos()) != null && possibleMoves.get(i).getColumnPos() < columnPos) { // piece on right
                blockingPiece2 = board.getPieceOnBoard(possibleMoves.get(i).getColumnPos(), possibleMoves.get(i).getRowPos());
                break;
            }
        }

        for (int i = 0; i < possibleMoves.size(); i++) {
            if (board.getPieceOnBoard(possibleMoves.get(i).getColumnPos(), possibleMoves.get(i).getRowPos()) != null && possibleMoves.get(i).getRowPos() > rowPos) { // piece above
                blockingPiece3 = board.getPieceOnBoard(possibleMoves.get(i).getColumnPos(), possibleMoves.get(i).getRowPos());
                break;
            }
        }

        for (int i = 0; i < possibleMoves.size(); i++) {
            if (board.getPieceOnBoard(possibleMoves.get(i).getColumnPos(), possibleMoves.get(i).getRowPos()) != null && possibleMoves.get(i).getRowPos() < rowPos) { //piece below
                blockingPiece4 = board.getPieceOnBoard(possibleMoves.get(i).getColumnPos(), possibleMoves.get(i).getRowPos());
                break;
            }
        }

        Piece finalBlockingPiece1 = blockingPiece1;
        Piece finalBlockingPiece2 = blockingPiece2;
        Piece finalBlockingPiece3 = blockingPiece3;
        Piece finalBlockingPiece4 = blockingPiece4;
        legalMoves = possibleMoves.stream().filter(pieceMove -> checkMove(pieceMove, finalBlockingPiece1, finalBlockingPiece2, finalBlockingPiece3, finalBlockingPiece4)).collect(Collectors.toList());

        return legalMoves;
    }

    private boolean checkMove(PieceMove pieceMove, Piece finalBlockingPiece1, Piece finalBlockingPiece2, Piece finalBlockingPiece3, Piece finalBlockingPiece4) {
        if (finalBlockingPiece1 != null && (pieceMove.getColumnPos() > finalBlockingPiece1.getColumnPos() ||
                (pieceMove.getColumnPos() == finalBlockingPiece1.getColumnPos() && finalBlockingPiece1.getPieceColor().equals(pieceColor))))
            return false;
        if (finalBlockingPiece2 != null && (pieceMove.getColumnPos() < finalBlockingPiece2.getColumnPos() ||
                (pieceMove.getColumnPos() == finalBlockingPiece2.getColumnPos() && finalBlockingPiece2.getPieceColor().equals(pieceColor))))
            return false;
        if (finalBlockingPiece3 != null && (pieceMove.getRowPos() > finalBlockingPiece3.getRowPos() ||
                (pieceMove.getRowPos() == finalBlockingPiece3.getRowPos() && finalBlockingPiece3.getPieceColor().equals(pieceColor))))
            return false;
        if (finalBlockingPiece4 != null && (pieceMove.getRowPos() < finalBlockingPiece4.getRowPos() ||
                (pieceMove.getRowPos() == finalBlockingPiece4.getRowPos() && finalBlockingPiece4.getPieceColor().equals(pieceColor))))
            return false;

        return true;
    }

    private List<PieceMove> getPossibleMoves (Board board){
        ArrayList<PieceMove> possibleMoves = new ArrayList<>();

        for (int i = 1; i < 8; i++) {
            possibleMoves.add(new PieceMove(columnPos + i, rowPos));
            possibleMoves.add(new PieceMove(columnPos - i, rowPos));

            possibleMoves.add(new PieceMove(columnPos, rowPos + i));
            possibleMoves.add(new PieceMove(columnPos, rowPos - i));
        }

        return possibleMoves
                .stream()
                .filter(pieceMove -> pieceMove.getColumnPos() >= 0 && pieceMove.getColumnPos() <= 7 && pieceMove.getRowPos() >= 0 && pieceMove.getRowPos() <= 7)
                .collect(Collectors.toList());
    }

}
