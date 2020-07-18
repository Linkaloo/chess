package chess.fxControllers;

import chess.modules.gameObjects.Board;
import chess.modules.gameObjects.gamePieces.*;
import chess.modules.GameController;
import chess.modules.gameObjects.pieceMove.CastlingPieceMove;
import chess.modules.gameObjects.pieceMove.EnPassantPieceMove;
import chess.modules.gameObjects.pieceMove.PieceMove;
import chess.modules.gameObjects.pieceMove.TakePieceMove;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameScreenController {

    private Board board;
    private GameController gameController;
    private List<PieceMove> legalMoves;
    private List<Piece> promotionPieces = new ArrayList<>();
    private Piece currPiece;
    private List<Pane> highlightedPanes = new ArrayList<>();
    private boolean isPawnPromoting = false;

    @FXML
    private GridPane boardGrid;


    public void initialize() {
        board = new Board(boardGrid);
        gameController = new GameController(board);
    }

    public void mousePressed(MouseEvent mouseEvent) {

        if(isPawnPromoting) {
            Integer col = GridPane.getColumnIndex((Node) mouseEvent.getTarget()), row = GridPane.getRowIndex((Node) mouseEvent.getTarget());

            PieceMove move = new PieceMove(col, row);
            if(legalMoves.contains(move)) {
                boardGrid.getChildren().removeAll(promotionPieces.stream().map(Piece::getImage).collect(Collectors.toList()));
                Piece clickedPiece = promotionPieces.stream().filter(piece -> piece.getRowPos() == move.getRowPos()).findFirst().orElse(new Queen(0, 0, PieceColor.BLACK));
                clickedPiece.setRowPos(currPiece.getRowPos());
                board.addPiecesToBoard(clickedPiece);

                isPawnPromoting = false;
                this.promotionPieces.clear();
                resetBoard();
            }
            return;
        }

        // user clicks on any piece
        if(mouseEvent.getTarget() instanceof ImageView) {
            Integer col = GridPane.getColumnIndex((ImageView) mouseEvent.getTarget()), row = GridPane.getRowIndex((ImageView) mouseEvent.getTarget());
            Pane clickedPane = (Pane) boardGrid.getChildren().get(row * 8 + col);
            Piece clickedPiece = board.getPieceOnBoard(col, row);

            handlePieceClicked(clickedPiece, clickedPane, col, row); // handles event once a piece is clicked
        }

        // user clicks on any pane
        else if(mouseEvent.getTarget() instanceof Pane){
            Integer col = GridPane.getColumnIndex((Pane) mouseEvent.getTarget()), row = GridPane.getRowIndex((Pane) mouseEvent.getTarget());
            Pane clickedPane = (Pane) mouseEvent.getTarget();
            Piece clickedPiece = board.getPieceOnBoard(col, row);
            PieceMove tempPieceMove = this.gameController.getLegalMove(row, col);   // returns the position of pane as a PieceMove if pane clicked is a legal move for that piece

            // handles event if there is a piece on the pane that is clicked
            if(clickedPiece != null) {
                handlePieceClicked(clickedPiece, clickedPane, col, row);
            }

            // handles event if there is no piece on the pane that is clicked
            else if(currPiece != null && tempPieceMove != null) {
                movePiece(tempPieceMove);
            }
        }
    }

    private void handlePieceClicked(Piece clickedPiece, Pane clickedPane, int col, int row) {

        // returns the position of pane as a PieceMove if pane clicked is a legal move for that piece
        PieceMove pieceMove = gameController.getLegalMove(row, col);

        // highlights piece that is clicked and its possible moves, when a piece is initially clicked
        if(currPiece == null && clickedPiece.getPieceColor().equals(gameController.checkTurn())) {
            currPiece = clickedPiece;
            highlightedPanes.add(clickedPane);
            this.legalMoves = gameController.getLegalMoves(currPiece);
            legalMoves.forEach(legalMove -> highlightedPanes.add((Pane) boardGrid.getChildren().get(legalMove.getRowPos() * 8 + legalMove.getColumnPos())));
            highlightPanes(highlightedPanes.toArray(new Pane[]{}));
        }

        // highlights new piece that is clicked and its possible moves, after a piece has already been clicked
        else if(currPiece != null && clickedPiece.getPieceColor().equals(gameController.checkTurn())) {
            resetBoard();
            currPiece = clickedPiece;
            highlightedPanes.add(clickedPane);
            this.legalMoves = gameController.getLegalMoves(currPiece);
            legalMoves.forEach(legalMove -> highlightedPanes.add((Pane) boardGrid.getChildren().get(legalMove.getRowPos() * 8 + legalMove.getColumnPos())));
            highlightPanes(highlightedPanes.toArray(new Pane[]{}));
        }

        // moves piece to clicked location, if its move is legal and it is that color's turn
        else if(currPiece != null && !clickedPiece.getPieceColor().equals(gameController.checkTurn()) && pieceMove != null) {
            movePiece(pieceMove);
        }
    }

    private void movePiece(PieceMove pieceMove) {

        Piece currPiece = pieceMove.getCurrPiece();

        // removes the image of the piece that is moved current position, places the image on the new position it is at, and updates the pieces position
        boardGrid.getChildren().remove(currPiece.getImage());
        boardGrid.add(currPiece.getImage(), pieceMove.getColumnPos(), pieceMove.getRowPos());
        gameController.movePiece(pieceMove);

        // handles special moves if they occur
        if(pieceMove instanceof EnPassantPieceMove) {
            boardGrid.getChildren().remove(((EnPassantPieceMove)pieceMove).getOpponentPawn().getImage());
        }
        else if(pieceMove instanceof TakePieceMove) {
            boardGrid.getChildren().remove(((TakePieceMove) pieceMove).getTakePiece().getImage());
        }
        else if(pieceMove instanceof CastlingPieceMove) {
            handleCastling(currPiece, pieceMove);
        }

        if(currPiece instanceof Pawn) {
            handlePawnPromotion(currPiece);
        }
        else
            resetBoard();

        gameController.updateKingInCheck(currPiece);
    }

    private void handleCastling(Piece currPiece, PieceMove pieceMove) {
        Rook currRook = ((CastlingPieceMove) pieceMove).getOwnRook();
        PieceMove tempMove;

        if(currRook.getColumnPos() < currPiece.getColumnPos())
            tempMove = new PieceMove(currRook.getColumnPos() + 3, currRook.getRowPos());
        else
            tempMove = new PieceMove(currRook.getColumnPos() - 2, currRook.getRowPos());

        // moves the rook to new position, deletes the image at the old position and places the image at the new position
        currRook.move(tempMove);
        boardGrid.getChildren().remove(currRook.getImage());
        boardGrid.add(currRook.getImage(), currRook.getColumnPos(),currRook.getRowPos());
    }

    // creates 1 new piece of each type, vertically, at the position which a pawn reaches its promotion,
    // and only allows clicking 1 of the new pieces and converts the pawn to the clicked piece
    private void handlePawnPromotion(Piece currPiece) {
        resetBoard();

        if(gameController.isPawnPromoted((Pawn)currPiece)) {
            int direction = currPiece.getPieceColor() == PieceColor.WHITE ? 1 : -1;
            Piece tempQueen = new Queen(currPiece.getColumnPos(), currPiece.getRowPos(), currPiece.getPieceColor());
            Piece tempRook = new Rook(currPiece.getColumnPos(), currPiece.getRowPos() + direction, currPiece.getPieceColor());
            Piece tempKnight = new Knight(currPiece.getColumnPos(), currPiece.getRowPos() + direction * 2, currPiece.getPieceColor());
            Piece tempBishop = new Bishop(currPiece.getColumnPos(), currPiece.getRowPos() + direction * 3, currPiece.getPieceColor());
            promotionPieces.addAll(Arrays.asList(tempQueen, tempBishop, tempKnight, tempRook));
            boardGrid.getChildren().remove(currPiece.getImage());
            board.removePieceFromBoard(currPiece);
            boardGrid.add(tempQueen.getImage(), tempQueen.getColumnPos(), tempQueen.getRowPos());
            boardGrid.add(tempRook.getImage(), tempRook.getColumnPos(), tempRook.getRowPos());
            boardGrid.add(tempKnight.getImage(), tempKnight.getColumnPos(), tempKnight.getRowPos());
            boardGrid.add(tempBishop.getImage(), tempBishop.getColumnPos(), tempBishop.getRowPos());
            legalMoves.addAll(Arrays.asList(
                    new PieceMove(currPiece.getColumnPos(), currPiece.getRowPos()),
                    new PieceMove(currPiece.getColumnPos(), currPiece.getRowPos() + direction),
                    new PieceMove(currPiece.getColumnPos(), currPiece.getRowPos() + direction * 2),
                    new PieceMove(currPiece.getColumnPos(), currPiece.getRowPos() + direction * 3)));

            legalMoves.forEach(legalMove -> highlightedPanes.add((Pane) boardGrid.getChildren().get(legalMove.getRowPos() * 8 + legalMove.getColumnPos())));
            highlightPanes(highlightedPanes.toArray(new Pane[]{}));
            isPawnPromoting = true;
            this.currPiece = currPiece;
        }
    }

    private void highlightPanes(Pane... panes) {
        for (Pane pane : panes) {
            pane.setStyle("-fx-background-color:#abee8d;");
        }
    }

    private void resetPanes(Pane... panes) {
        for (Pane pane : panes) {
            pane.setStyle(getPaneDefaultColor(pane) == PieceColor.BLACK ? "-fx-background-color: #7ebffe;" : "-fx-background-color: #ffffff;");
        }
    }

    private void resetBoard() {
        resetPanes(highlightedPanes.toArray(new Pane[]{}));
        highlightedPanes.clear();
        legalMoves.clear();
        currPiece = null;
    }

    //used to revert a highlighted pane to its base color
    private PieceColor getPaneDefaultColor(Pane pane) {
        Integer col = GridPane.getColumnIndex(pane), row = GridPane.getRowIndex(pane);
        return col % 2 == row % 2 ? PieceColor.WHITE : PieceColor.BLACK;
    }
}