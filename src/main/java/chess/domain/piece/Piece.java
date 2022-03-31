package chess.domain.piece;

import chess.domain.Color;
import chess.domain.Movement;
import chess.domain.position.Position;
import java.util.List;

public abstract class Piece {

    protected final Color color;
    protected final List<Movement> movements;

    public Piece(Color color) {
        this.color = color;
        this.movements = chooseMovements();
    }

    public String signature() {
        return color.correctSignature(baseSignature());
    }

    public boolean isSameColor(Color color) {
        return this.color == color;
    }

    protected abstract List<Movement> chooseMovements();

    protected abstract String baseSignature();

    public abstract boolean isCorrectMovement(Position source, Position target, boolean hasTargetPiece);

    public abstract boolean canJumpOverPieces();

    public abstract double score();

    public abstract boolean isPawn();

    public abstract boolean isKing();
}
