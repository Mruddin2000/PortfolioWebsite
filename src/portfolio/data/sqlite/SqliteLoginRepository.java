package portfolio.data.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import portfolio.common.LoginRepository;
import portfolio.common.models.Login;

public class SqliteLoginRepository implements LoginRepository {

	private SqliteConnection provider;

	public SqliteLoginRepository(SqliteConnection provider) {
		this.provider = provider;
	}

	private Login resultSetToLogin(ResultSet resultSet) throws SQLException {
		String username = resultSet.getString("username");
		String password = resultSet.getString("password");

		return new Login(username, password);
	}

	public boolean checkLoginDetails(String username, String password) {
		String query = "SELECT * FROM User WHERE username = ? AND password = ?";
		try (Connection connection = provider.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.next(); // If the query returns a result, login details are correct
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; // Return false if an exception occurs or no result is found
	}

	@Override
	public void updatePassword(String username, String password) {
		String query = "UPDATE User SET password = ? WHERE username = ?";
		try (Connection connection = provider.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, password); // Set password
			preparedStatement.setString(2, username); // Set username

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Password updated successfully for username: " + username);
			} else {
				System.out.println("Failed to update password for username: " + username);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Failed to update password for username: " + username, e);
		}
	}

	@Override
	public Login getUsername(String username) {
		String query = "SELECT * FROM User WHERE username = ?";
		try (Connection connection = provider.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, username); // Set username

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSetToLogin(resultSet);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; // User not found or credentials do not match
	}

	@Override
	public Login getPassword(String password) {
		String query = "SELECT * FROM User WHERE password = ?";
		try (Connection connection = provider.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, password); // Set password

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSetToLogin(resultSet);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; // User not found or credentials do not match
	}

	@Override
	public void updateUser(Login user) {
		String query = "UPDATE User SET password = ? WHERE username = ?";
		try (Connection connection = provider.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, user.getPassword()); // Set new password
			preparedStatement.setString(2, user.getUsername()); // Set username

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Password updated successfully for username: " + user.getUsername());
			} else {
				System.out.println("Failed to update password for username: " + user.getUsername());
			}
		} catch (SQLException e) {
			throw new RuntimeException("Failed to update password for username: " + user.getUsername(), e);
		}
	}

}
