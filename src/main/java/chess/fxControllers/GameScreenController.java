package chess.fxControllers;

import chess.modules.gameObjects.Board;
import chess.modules.gameObjects.gamePieces.Piece;
import chess.modules.gameObjects.gamePieces.PieceColor;
import chess.modules.gameObjects.gamePieces.PieceMove;
import chess.modules.GameController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class GameScreenController {

    private Board board;
    private GameController gameController;
    private List<PieceMove> legalMoves;
    private Piece currPiece;
    private List<Pane> highlightedPanes = new ArrayList<>();

    @FXML
    private GridPane boardGrid;


    public void initialize() {
        board = new Board(boardGrid);
        gameController = new GameController(board);
    }

    public void mousePressed(MouseEvent mouseEvent) {

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
            else if(currPiece != null && gameController.isLegalMove(tempPieceMove)) {
                movePiece(tempPieceMove);
            }
        }
    }

    private void handlePieceClicked(Piece clickedPiece, Pane clickedPane, int col, int row) {
        if(currPiece == null && clickedPiece.getPieceColor().equals(gameController.checkTurn())) {
            currPiece = clickedPiece;
            highlightedPanes.add(clickedPane);
            this.legalMoves = gameController.checkLegalMoves(currPiece);
            legalMoves.forEach(legalMove -> highlightedPanes.add((Pane) boardGrid.getChildren().get(row * 8 + col)));
            highlightPanes(highlightedPanes.toArray(new Pane[]{}));
        }
        else if(currPiece != null && clickedPiece.getPieceColor().equals(gameController.checkTurn())) {
            resetBoard();
            currPiece = clickedPiece;
            highlightedPanes.add(clickedPane);
            this.legalMoves = gameController.checkLegalMoves(currPiece);
            legalMoves.forEach(legalMove -> highlightedPanes.add((Pane) boardGrid.getChildren().get(row * 8 + col)));
            highlightPanes(highlightedPanes.toArray(new Pane[]{}));
        }
        else if(currPiece != null && !clickedPiece.getPieceColor().equals(gameController.checkTurn()) && gameController.isLegalMove(new PieceMove(col, row))) {
            movePiece(new PieceMove(col, row));
        }
    }

    private void movePiece(PieceMove pieceMove) {
        boardGrid.getChildren().remove(currPiece.getImage());
        boardGrid.add(currPiece.getImage(), pieceMove.getColumnPos(), pieceMove.getRowPos());
        Piece tempPiece = gameController.movePiece(currPiece.getImage(), pieceMove.getColumnPos(), pieceMove.getRowPos());
        if(tempPiece != null) {
            boardGrid.getChildren().remove(tempPiece.getImage());
        }
        resetBoard();
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