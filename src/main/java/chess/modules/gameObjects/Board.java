package chess.modules.gameObjects;

import chess.modules.gameObjects.gamePieces.*;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class Board {

    @FXML
    private GridPane boardGrid;

    private ArrayList<Piece> pieces;

    public Board(GridPane boardGrid) {

        this.boardGrid = boardGrid;

        pieces = new ArrayList<>();

        // pawns
        for(int i = 0; i < 8; i++) {
            Pawn tempPawn = new Pawn(i, 1, PieceColor.BLACK);
            Pawn tempPawn2 = new Pawn(i, 6, PieceColor.WHITE);
            addPiecesToBoard(tempPawn, tempPawn2);
        }

        // rooks
        Rook rook1 = new Rook(0,0,PieceColor.BLACK);
        Rook rook2 = new Rook(7,0,PieceColor.BLACK);
        Rook rook3 = new Rook(0,7,PieceColor.WHITE);
        Rook rook4 = new Rook(7,7,PieceColor.WHITE);
        addPiecesToBoard(rook1, rook2, rook3, rook4);

        // knights
        Knight knight1 = new Knight(1,0,PieceColor.BLACK);
        Knight knight2 = new Knight(6,0,PieceColor.BLACK);
        Knight knight3 = new Knight(1,7,PieceColor.WHITE);
        Knight knight4 = new Knight(6,7,PieceColor.WHITE);
        addPiecesToBoard(knight1, knight2, knight3, knight4);

        // bishops
        Bishop bishop1 = new Bishop(2, 0, PieceColor.BLACK);
        Bishop bishop2 = new Bishop(5, 0, PieceColor.BLACK);
        Bishop bishop3 = new Bishop(2, 7, PieceColor.WHITE);
        Bishop bishop4 = new Bishop(5, 7, PieceColor.WHITE);
        addPiecesToBoard(bishop1, bishop2, bishop3, bishop4);

        // queens
        Queen queen1 = new Queen(3, 0, PieceColor.BLACK);
        Queen queen2 = new Queen(3, 7, PieceColor.WHITE);
        addPiecesToBoard(queen1, queen2);

        // kings
        King king1 = new King(4, 0, PieceColor.BLACK);
        King king2 = new King(4, 7, PieceColor.WHITE);
        addPiecesToBoard(king1, king2);

    }

    public void movePiece(ImageView temp, Integer col, Integer row) {
        for (Piece piece : pieces) {
            if (piece.getImage().equals(temp)) {
                piece.move(new PieceMove(col, row));
            }
        }
    }

    public Piece getPieceFromImage(ImageView temp) {
        int piece = 0;
        for(int i = 0; i < pieces.size(); i++) {
            if(pieces.get(i).getImage().equals(temp))
                piece = i;
        }
        return pieces.get(piece);
    }

    public void addPiecesToBoard(Piece... pieces) {
        for(Piece piece : pieces) {
            boardGrid.add(piece.getImage(), piece.getColumnPos(), piece.getRowPos());
            this.pieces.add(piece);
        }
    }

}
