package chess.modules.gameObjects;

import chess.modules.gameObjects.gamePieces.Pawn;
import chess.modules.gameObjects.gamePieces.Piece;
import chess.modules.gameObjects.gamePieces.PieceColor;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class Board {

    @FXML
    private GridPane boardGrid;

    private ArrayList<Piece> pieces;

    public Board(GridPane boardGrid) {
        this.boardGrid = boardGrid;
        Pawn pawn1 = new Pawn(5,5, PieceColor.BLACK);
        boardGrid.add(pawn1.getImage(), pawn1.getColumnPos(), pawn1.getRowPos());
    }

}
