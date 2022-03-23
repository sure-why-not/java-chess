package chess.domain.piece;

import chess.domain.Color;
import chess.domain.position.Position;
import chess.domain.position.PositionX;
import chess.domain.position.PositionY;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class BishopTest {
    @ParameterizedTest
    @CsvSource(value = {"BLACK:B", "WHITE:b"}, delimiter = ':')
    @DisplayName("Bishop 의 색깔에 맞는 이름을 반환하는지")
    void checkNameByColor(Color color, String pieceName) {
        Bishop bishop = new Bishop(color);

        assertThat(bishop.signature()).isEqualTo(pieceName);
    }

    @Test
    @DisplayName("Bishop 이 움직일 수 있는 위치이면 true를 반환하는지")
    void isMovable() {
        Bishop bishop = new Bishop(Color.BLACK);
        Position source = new Position(PositionX.C, PositionY.RANK_5);
        Position target = new Position(PositionX.F, PositionY.RANK_2);
        assertThat(bishop.isMovable(source, target)).isTrue();
    }

    @Test
    @DisplayName("Bishop 이 움직일 수 없는 위치이면 false를 반환하는지")
    void isNotMovable() {
        Bishop bishop = new Bishop(Color.BLACK);
        Position source = new Position(PositionX.C, PositionY.RANK_5);
        Position target = new Position(PositionX.G, PositionY.RANK_6);
        assertThat(bishop.isMovable(source, target)).isFalse();
    }
}
