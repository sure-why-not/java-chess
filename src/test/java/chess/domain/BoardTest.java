package chess.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.command.MoveCommand;
import chess.domain.piece.King;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.Queen;
import chess.domain.position.Column;
import chess.domain.position.Position;
import chess.domain.position.Row;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    @DisplayName("King 이 모두 살아있는 경우 true를 반환하는지")
    void checkAliveAllKings() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(Position.of(Column.C, Row.RANK_3), new King(Color.BLACK));
        pieces.put(Position.of(Column.H, Row.RANK_4), new Pawn(Color.BLACK));
        pieces.put(Position.of(Column.H, Row.RANK_3), new Pawn(Color.BLACK));
        pieces.put(Position.of(Column.C, Row.RANK_1), new King(Color.WHITE));
        pieces.put(Position.of(Column.A, Row.RANK_7), new Pawn(Color.WHITE));

        Board board = new Board(pieces);

        assertThat(board.hasBothKings()).isTrue();
    }

    @Test
    @DisplayName("King 이 하나라도 없는 경우 false를 반환하는지")
    void checkDeadKing() {
        Map<Position, Piece> pieces = new HashMap<>();
        pieces.put(Position.of(Column.C, Row.RANK_2), new Queen(Color.BLACK));
        pieces.put(Position.of(Column.C, Row.RANK_6), new Pawn(Color.BLACK));
        pieces.put(Position.of(Column.C, Row.RANK_1), new King(Color.WHITE));
        pieces.put(Position.of(Column.A, Row.RANK_2), new Pawn(Color.WHITE));

        Board board = new Board(pieces);

        assertThat(board.hasBothKings()).isFalse();
    }

    @Test
    @DisplayName("기물을 정상적으로 이동시키는 경우")
    void movePiece() {
        Board board = new Board(PiecesUtil.createChessPieces());
        MoveCommand moveCommand = MoveCommand.of("move e2 e4");
        Position targetPosition = Position.of("e4");
        Map<Position, Piece> beforePieces = board.getPieces();

        board.movePiece(Color.WHITE, moveCommand);
        Map<Position, Piece> afterPieces = board.getPieces();

        assertAll(
                () -> assertThat(beforePieces.get(targetPosition)).isNull(),
                () -> assertThat(afterPieces.get(targetPosition)).isNotNull(),
                () -> assertThat(afterPieces.get(targetPosition).isPawn()).isTrue(),
                () -> assertThat(afterPieces.get(targetPosition).isSameColor(Color.WHITE)).isTrue()
        );
    }

    @Test
    @DisplayName("source 을 비어있는 곳을 선택한 경우")
    void choiceEmptySource() {
        Board board = new Board(PiecesUtil.createChessPieces());
        MoveCommand moveCommand = MoveCommand.of("move e5 e4");

        Assertions.assertThatThrownBy(() -> board.movePiece(Color.BLACK, moveCommand))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("source 위치에 기물이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("source 을 상대편 기물을 선택한 경우")
    void choiceEnemyColorSource() {
        Board board = new Board(PiecesUtil.createChessPieces());
        MoveCommand moveCommand = MoveCommand.of("move e2 e4");

        Assertions.assertThatThrownBy(() -> board.movePiece(Color.BLACK, moveCommand))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("현재 순서 진영의 기물이 아닙니다.");
    }

    @Test
    @DisplayName("target 이 같은 색 기물인 경우")
    void choiceSameColorTarget() {
        Board board = new Board(PiecesUtil.createChessPieces());
        MoveCommand moveCommand = MoveCommand.of("move e1 e2");

        Assertions.assertThatThrownBy(() -> board.movePiece(Color.WHITE, moveCommand))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("target 위치에 자신의 기물이 있습니다.");
    }


    @Test
    @DisplayName("기물이 이동할 수 없는 행마법인 경우")
    void choiceInvalidMovement() {
        Board board = new Board(PiecesUtil.createChessPieces());
        MoveCommand moveCommand = MoveCommand.of("move e2 d3");

        Assertions.assertThatThrownBy(() -> board.movePiece(Color.WHITE, moveCommand))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 기물이 움직일 수 있는 행마법이 아닙니다.");
    }

    @Test
    @DisplayName("이동 경로에 기물이 존재하는 경우")
    void notEmptyOnRoute() {
        Board board = new Board(PiecesUtil.createChessPieces());
        MoveCommand moveCommand = MoveCommand.of("move d1 d4");

        Assertions.assertThatThrownBy(() -> board.movePiece(Color.WHITE, moveCommand))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동 경로에 다른 기물이 존재합니다.");
    }
}