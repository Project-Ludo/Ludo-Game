package io.github.ludogame.pawn;

import java.io.Serializable;

public class PawnMoveData implements Serializable {

    private final int id;
    private final PawnColor pawnColor;
    private final int diceResult;

    public PawnMoveData(int id, PawnColor pawnColor, int diceResult) {
        this.id = id;
        this.pawnColor = pawnColor;
        this.diceResult = diceResult;
    }

    public int getId() {
        return id;
    }

    public PawnColor getPawnColor() {
        return pawnColor;
    }

    public int getDiceResult() {
        return diceResult;
    }
}
