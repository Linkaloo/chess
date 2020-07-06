package chess.fxControllers;

import chess.modules.gameObjects.Board;
import chess.modules.gameObjects.gamePieces.*;
import chess.modules.GameController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

        if(mouseEvent.getTarget() instanceof ImageView) {
            Integer col = GridPane.getColumnIndex((ImageView) mouseEvent.getTarget()), row = GridPane.getRowIndex((ImageView) mouseEvent.getTarget());
            Pane clickedPane = (Pane) boardGrid.getChildren().get(row * 8 + col);
            Piece clickedPiece = board.getPieceOnBoard(col, row);

            handlePieceClicked(clickedPiece, clickedPane, col, row);
        }
        else if(mouseEvent.getTarget() instanceof Pane){
            Integer col = GridPane.getColumnIndex((Pane) mouseEvent.getTarget()), row = GridPane.getRowIndex((Pane) mouseEvent.getTarget());
            Pane clickedPane = (Pane) mouseEvent.getTarget();
            Piece clickedPiece = board.getPieceOnBoard(col, row);
            PieceMove tempPieceMove = new PieceMove(col, row);

            if(clickedPiece != null) {
                handlePieceClicked(clickedPiece, clickedPane, col, row);
            }
            else if(currPiece != null && gameController.isLegalMove(tempPieceMove, currPiece)) {
                movePiece(tempPieceMove);
            }
        }
    }

    private void handlePieceClicked(Piece clickedPiece, Pane clickedPane, int col, int row) {
        if(currPiece == null && clickedPiece.getPieceColor().equals(gameController.checkTurn())) {
            currPiece = clickedPiece;
            highlightedPanes.add(clickedPane);
            this.legalMoves = gameController.getLegalMoves(currPiece);
            legalMoves.forEach(legalMove -> highlightedPanes.add((Pane) boardGrid.getChildren().get(legalMove.getRowPos() * 8 + legalMove.getColumnPos())));
            highlightPanes(highlightedPanes.toArray(new Pane[]{}));
        }
        else if(currPiece != null && clickedPiece.getPieceColor().equals(gameController.checkTurn())) {
            resetBoard();
            currPiece = clickedPiece;
            highlightedPanes.add(clickedPane);
            this.legalMoves = gameController.getLegalMoves(currPiece);
            legalMoves.forEach(legalMove -> highlightedPanes.add((Pane) boardGrid.getChildren().get(legalMove.getRowPos() * 8 + legalMove.getColumnPos())));
            highlightPanes(highlightedPanes.toArray(new Pane[]{}));
        }
        else if(currPiece != null && !clickedPiece.getPieceColor().equals(gameController.checkTurn()) && gameController.isLegalMove(new PieceMove(col, row), currPiece)) {
            movePiece(new PieceMove(col, row));
        }
    }

    private void movePiece(PieceMove pieceMove) {
        boardGrid.getChildren().remove(currPiece.getImage());
        boardGrid.add(currPiece.getImage(), pieceMove.getColumnPos(), pieceMove.getRowPos());
        Piece tempPiece = gameController.movePiece(currPiece.getImage(), pieceMove.getColumnPos(), pieceMove.getRowPos());
        if(currPiece instanceof Pawn) {
            handlePawnPromotion(currPiece);
        }
        else
            resetBoard();
        if(tempPiece != null) {
            boardGrid.getChildren().remove(tempPiece.getImage());
        }
    }

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

    private PieceColor getPaneDefaultColor(Pane pane) {
        Integer col = GridPane.getColumnIndex(pane), row = GridPane.getRowIndex(pane);
        return col % 2 == row % 2 ? PieceColor.WHITE : PieceColor.BLACK;
    }
}