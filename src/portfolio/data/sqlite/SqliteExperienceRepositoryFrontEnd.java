package portfolio.data.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import portfolio.common.ExperienceRepositoryFrontend;
import portfolio.common.models.Experience;

public class SqliteExperienceRepositoryFrontEnd implements ExperienceRepositoryFrontend {

	private SqliteConnection provider;

	public SqliteExperienceRepositoryFrontEnd(SqliteConnection provider) {
		this.provider = provider;
	}

	private ResultSet executeQuery(String query) throws SQLException {
		Connection connection = provider.getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		return statement.executeQuery();
	}

	private Experience resultSetToExperience(ResultSet resultSet) throws SQLException {
		return new Experience(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("details"));
	}

	@Override
	public List<Experience> getAllFrontEnd() {
		List<Experience> experiencesFrontEnd = new ArrayList<>();
		String query = "SELECT * FROM Experience_FrontEnd";

		try (ResultSet resultSet = executeQuery(query)) {
			while (resultSet.next()) {
				experiencesFrontEnd.add(resultSetToExperience(resultSet));
			}
		} catch (SQLException e) {
			throw new RuntimeException("Failed to get all experiences", e);
		}

		return experiencesFrontEnd;
	}

	@Override
	public Experience getByFrontEndId(int id) {
		String query = "SELECT * FROM Experience_FrontEnd WHERE id = ?";
		try (Connection connection = provider.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSetToExperience(resultSet);
				} else {
					return null; // No record found
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Failed to get Experience by ID", e);
		}
	}

	@Override
	public void updateExperience(int id, String title, String details) {
		String query = "UPDATE Experience_FrontEnd SET title=?, details=? WHERE id=?";

		try {
			Connection connection = provider.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, title);
			preparedStatement.setString(2, details);
			preparedStatement.setInt(3, id); // Set the ID parameter

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Experience record updated successfully.");
			} else {
				System.out.println("No experience record found with the specified ID: " + id);
			}

			preparedStatement.close();
			connection.close();
		} catch (SQLException e) {
			throw new RuntimeException("Failed to update experience record", e);
		}
	}

}