package chess.modules.gameObjects;

import chess.modules.gameObjects.gamePieces.*;
import chess.modules.gameObjects.pieceMove.EnPassantPieceMove;
import chess.modules.gameObjects.pieceMove.PieceMove;
import chess.modules.gameObjects.pieceMove.TakePieceMove;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public void movePiece(PieceMove pieceMove) {

        if(pieceMove instanceof TakePieceMove)
            removePieceFromBoard(((TakePieceMove) pieceMove).getTakePiece());
        else if(pieceMove instanceof EnPassantPieceMove)
            removePieceFromBoard(((EnPassantPieceMove) pieceMove).getOpponentPawn());

        pieceMove.getCurrPiece().move(pieceMove);
    }

    public void undoMove(PieceMove pieceMove, boolean isEnpassantable) {

        if(pieceMove instanceof TakePieceMove)
            pieces.add(((TakePieceMove) pieceMove).getTakePiece());
        else if(pieceMove instanceof EnPassantPieceMove)
            pieces.add(((EnPassantPieceMove) pieceMove).getOpponentPawn());

        pieceMove.getCurrPiece().move(new PieceMove(pieceMove.getInitialColumnPos(), pieceMove.getInitialRowPos()));

        if(pieceMove.getCurrPiece() instanceof Pawn) {
            ((Pawn) pieceMove.getCurrPiece()).setEnpassantable(isEnpassantable);
            if(pieceMove.isInitialMove())
                ((Pawn) pieceMove.getCurrPiece()).setInitialMove(true);
        }
        else if(pieceMove.isInitialMove() && pieceMove.getCurrPiece() instanceof King)
            ((King) pieceMove.getCurrPiece()).setInitialMove(true);
    }

    public void removePieceFromBoard(Piece piece) {
        this.pieces.remove(piece);
    }

    public void addPiecesToBoard(Piece... pieces) {
        for(Piece piece : pieces) {
            boardGrid.add(piece.getImage(), piece.getColumnPos(), piece.getRowPos());
            this.pieces.add(piece);
        }
    }

    public Piece getPieceOnBoard(int col, int row) {
        for (Piece piece : pieces) {
            if(piece.getColumnPos() == col && piece.getRowPos() == row)
                return piece;
        }
        return null;
    }

    public List<Pawn> getPawns(PieceColor pieceColor) {
        return  pieces.stream().filter(piece -> piece instanceof Pawn && piece.getPieceColor().equals(pieceColor)).map(piece -> (Pawn) piece).collect(Collectors.toList());
    }

    public List<Piece> getOppositeColorPieces(PieceColor pieceColor) {
        return pieces.stream().filter(piece -> !(piece instanceof King) && piece.getPieceColor() != pieceColor).collect(Collectors.toList());
    }

    public Rook getCastlingRook(int rookColPos, PieceColor ownColor) {
        return (Rook) pieces.stream().filter(piece -> piece instanceof Rook && piece.getPieceColor().equals(ownColor)
                && piece.getColumnPos() == rookColPos).findFirst().orElse(null);
    }

    // both isKingInCheck methods used to force either king to move while in check or allow a piece to block the check
    public boolean isKingInCheck(King king) {

        for (Piece piece : pieces) {
            if (piece.getPieceColor() == king.getPieceColor())
                continue;
            List<PieceMove> opponentLegalMoves = piece.getAllPossibleMoves(this);
            if (opponentLegalMoves.contains(new PieceMove(king.getColumnPos(), king.getRowPos()))) {
                return true;
            }
        }

        return false;
    }

    // moves piece to temporary new position, checks if that position causes king to be in check, then undoes that movement,
    // and returns if the king is in check due to that movement
    public boolean isKingInCheck(King king, PieceMove pieceMove) {

        boolean isKingInCheck;
        boolean isEnPassantable = pieceMove.isEnpassantable();

        movePiece(pieceMove);
        isKingInCheck = isKingInCheck(king);
        undoMove(pieceMove, isEnPassantable);

        return isKingInCheck;
    }

    public King getKing(PieceColor color) {
        return (King) pieces.stream().filter(piece -> piece instanceof King && piece.getPieceColor() == color).findFirst().orElse(null);
    }

    public King getOppKing(PieceColor color) {
        return (King) pieces.stream().filter(piece -> piece instanceof King && piece.getPieceColor() != color).findFirst().orElse(null);
    }
}
