package chess.modules.gameObjects;

import chess.modules.gameObjects.gamePieces.PieceColor;

public class Player {

    private PieceColor playerColor;
    private boolean isTurn;
    private boolean hasWon;

    public Player(PieceColor playerColor, boolean isTurn, boolean hasWon) {
        this.playerColor = playerColor;
        this.isTurn = isTurn;
        this.hasWon = hasWon;
    }

    public void setIsTurn(boolean isTurn) {
        this.isTurn = isTurn;
    }

    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }

    public boolean getIsTurn() {
        return isTurn;
    }

    public boolean getHasWon() {
        return hasWon;
    }

    public PieceColor getColor() {
        return playerColor;
    }
}
