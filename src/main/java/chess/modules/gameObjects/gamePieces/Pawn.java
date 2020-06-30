package chess.modules.gameObjects.gamePieces;

import chess.modules.gameObjects.Board;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    private boolean initialMove;

    public Pawn(int columnPos, int rowPos, PieceColor pieceColor) {
        super(columnPos, rowPos, pieceColor);
        initialMove = true;

        File file;
        switch (pieceColor) {
            case BLACK: file = new File(String.valueOf(getClass().getClassLoader().getResource("assets/B.P.png")));
            break;
            case WHITE: file = new File(String.valueOf(getClass().getClassLoader().getResource("assets/W.P.png")));
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + pieceColor);
        }
        Image imageFile = new Image(file.getPath());
        super.image = new ImageView();
        super.image.setImage(imageFile);
    }

    @Override
    public void move(PieceMove move) {
        super.move(move);
        initialMove = false;
    }

    @Override
    public List<PieceMove> getLegalMoves(Board board) {
        ArrayList<PieceMove> legalMoves = new ArrayList<>();
        //checks possible moves and stores into legal moves
        List<PieceMove> possibleMoves = getPossibleMoves(board);



        return legalMoves;
    }

    private void checkMove() {

    }

    private List<PieceMove> getPossibleMoves(Board board) {

        ArrayList<PieceMove> possibleMoves = new ArrayList<>();

        //possibleMoves
        if(pieceColor.equals(PieceColor.WHITE) && rowPos > 0) {
            possibleMoves.add(new PieceMove(columnPos, rowPos - 1));

            if (initialMove) {
                possibleMoves.add(new PieceMove(columnPos, rowPos - 2));
            }

            if(columnPos != 0 && columnPos != 7) {
                possibleMoves.add(new PieceMove(columnPos - 1, rowPos - 1));
                possibleMoves.add(new PieceMove(columnPos + 1, rowPos - 1));
            }
            else if(columnPos == 0)
                possibleMoves.add(new PieceMove(columnPos + 1, rowPos - 1));
            else
                possibleMoves.add(new PieceMove(columnPos - 1, rowPos - 1));
        }
        else if (pieceColor.equals(PieceColor.BLACK) && rowPos < 7){
            possibleMoves.add(new PieceMove(columnPos, rowPos + 1));

            if (initialMove) {
                possibleMoves.add(new PieceMove(columnPos, rowPos + 2));
            }

            if(columnPos != 0 && columnPos != 7) {
                possibleMoves.add(new PieceMove(columnPos - 1, rowPos + 1));
                possibleMoves.add(new PieceMove(columnPos + 1, rowPos + 1));
            }
            else if(columnPos == 0)
                possibleMoves.add(new PieceMove(columnPos + 1, rowPos + 1));
            else
                possibleMoves.add(new PieceMove(columnPos - 1, rowPos + 1));
        }

        return possibleMoves;
    }

}
