package chess.modules.gameObjects;

public class Player {

    private boolean isWhite;
    private boolean isTurn;
    private boolean hasWon;

    public Player(boolean isWhite, boolean isTurn, boolean hasWon) {
        this.isWhite = isWhite;
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
}
