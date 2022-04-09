package chess.dao;

import chess.db.DBConnector;
import chess.dto.GameDto;
import chess.dto.GameStatusDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameDaoImpl implements GameDao {

    private final DBConnector dbConnector;

    public GameDaoImpl(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    @Override
    public void removeAll() {
        final String sql = "delete from game";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(GameDto gameDto) {
        final String sql = "insert into game (turn, status) values (?, ?)";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, gameDto.getTurn());
            statement.setString(2, gameDto.getStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(GameDto gameDto) {
        final String sql = "update game set turn = ?, status = ?";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, gameDto.getTurn());
            statement.setString(2, gameDto.getStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStatus(GameStatusDto statusDto) {
        final String sql = "update game set status = ?";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, statusDto.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public GameDto find() {
        final String sql = "select * from game";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return new GameDto(null, "ready");
            }
            return new GameDto(resultSet.getString("turn"), resultSet.getString("status"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
