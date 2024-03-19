package portfolio.data.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import portfolio.common.PortfolioRepository;
import portfolio.common.models.Portfolio;

public class SqlitePortfolioRepository implements PortfolioRepository {

	private SqliteConnection provider;

	public SqlitePortfolioRepository(SqliteConnection provider) {
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

	private Portfolio resultSetToPortfolio(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id");
		String title = resultSet.getString("title");
		String details = resultSet.getString("details");

		// Retrieve the image data as a byte array
		byte[] imageData = resultSet.getBytes("image");

		// Encode the image data as a Base64 string
		String imageDataBase64 = Base64.getEncoder().encodeToString(imageData);

		return new Portfolio(id, title, details, imageDataBase64);
	}

	@Override
	public List<Portfolio> getAll() {
		List<Portfolio> portfolios = new ArrayList<>();
		String query = "SELECT * FROM Portfolio";

		try (ResultSet resultSet = executeQuery(query)) {
			while (resultSet.next()) {
				portfolios.add(resultSetToPortfolio(resultSet));
			}
		} catch (SQLException e) {
			throw new RuntimeException("Failed to get all portfolios", e);
		}

		return portfolios;
	}

	@Override
	public Portfolio getById(int id) {
		String query = "SELECT * FROM Portfolio WHERE id = ?";
		try (Connection connection = provider.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setInt(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSetToPortfolio(resultSet);
				} else {
					return null; // No record found
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Failed to get portfolio by ID", e);
		}
	}

	@Override
	public void updatePortfolio(int id, String title, String details) {
		// Retrieve the portfolio by ID
		Portfolio portfolioToUpdate = getById(id);

		if (portfolioToUpdate != null) {
			// Portfolio found, proceed with updating title and description
			String query = "UPDATE Portfolio SET title=?, details=? WHERE id=?";
			try (Connection connection = provider.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(query)) {

				preparedStatement.setString(1, title);
				preparedStatement.setString(2, details);
				preparedStatement.setInt(3, id); // Set the ID parameter

				int rowsAffected = preparedStatement.executeUpdate();

				if (rowsAffected > 0) {
					System.out.println("Record updated successfully.");
				} else {
					System.out.println("Failed to update portfolio.");
				}
			} catch (SQLException e) {
				throw new RuntimeException("Failed to update portfolio", e);
			}
		} else {
			// Portfolio not found with the specified ID
			System.out.println("No record found with the specified ID: " + id);
		}
	}

}