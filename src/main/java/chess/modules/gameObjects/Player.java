package chess.modules.gameObjects;

import chess.modules.gameObjects.gamePieces.PieceColor;

public class Player {

    private PieceColor playerColor;
    private boolean hasWon;

    public Player(PieceColor playerColor, boolean hasWon) {
        this.playerColor = playerColor;
        this.hasWon = hasWon;
    }

    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }

    public boolean getHasWon() {
        return hasWon;
    }

    public PieceColor getColor() {
        return playerColor;
    }
}
