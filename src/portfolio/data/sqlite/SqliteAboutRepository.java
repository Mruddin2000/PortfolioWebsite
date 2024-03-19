package portfolio.data.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import portfolio.common.AboutRepository;
import portfolio.common.models.About;

public class SqliteAboutRepository implements AboutRepository {

	private SqliteConnection provider;

	public SqliteAboutRepository(SqliteConnection provider) {
		this.provider = provider;
	}

	private ResultSet executeQuery(String query) throws SQLException {
		Connection connection = provider.getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		return statement.executeQuery();
	}
	/*
	 * private Portfolio resultSetToPortfolio(ResultSet resultSet) throws
	 * SQLException { int id = resultSet.getInt("id"); String title =
	 * resultSet.getString("title"); String details =
	 * resultSet.getString("details");
	 * 
	 * // Retrieve the image data as a byte array Blob blob =
	 * resultSet.getBlob("image"); byte[] image = blob.getBytes(1, (int)
	 * blob.length());
	 * 
	 * return new Portfolio(id, title, details, image); }
	 */

	private About resultSetToAbout(ResultSet resultSet) throws SQLException {
		String name = resultSet.getString("name");
		String email = resultSet.getString("email");
		String contactNumber = resultSet.getString("contactNumber");
		String qualification = resultSet.getString("qualification");
		String occupation = resultSet.getString("occupation");

		// Retrieve the image data as a byte array
		byte[] imageData = resultSet.getBytes("image");

		// Encode the image data as a Base64 string
		String imageDataBase64 = Base64.getEncoder().encodeToString(imageData);

		return new About(name, email, contactNumber, qualification, occupation, imageDataBase64);
	}

	@Override
	public List<About> getAll() {
		List<About> about = new ArrayList<>();
		String query = "SELECT * FROM About";

		try (ResultSet resultSet = executeQuery(query)) {
			while (resultSet.next()) {
				about.add(resultSetToAbout(resultSet));
			}
		} catch (SQLException e) {
			throw new RuntimeException("Failed to get all portfolios", e);
		}

		return about;
	}

	@Override
	public void updateAbout(String name, String email, String contactNumber, String qualification, String occupation) {

		// Portfolio found, proceed with updating title and description
		String query = "UPDATE About SET name=?, email=?,contactNumber=?,qualification=?, occupation=?";
		try (Connection connection = provider.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, name);
			preparedStatement.setString(2, email);
			preparedStatement.setString(3, contactNumber);
			preparedStatement.setString(4, qualification);
			preparedStatement.setString(5, occupation);

			int rowsAffected = preparedStatement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Record updated successfully.");
			} else {
				System.out.println("Failed to update portfolio.");
			}
		} catch (SQLException e) {
			throw new RuntimeException("Failed to update portfolio", e);

		}
	}

}