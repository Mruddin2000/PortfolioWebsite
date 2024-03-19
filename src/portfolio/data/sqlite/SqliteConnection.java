package portfolio.data.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteConnection {

	private String filename;
	private Connection connection;

	public SqliteConnection(String filename) {
		this.filename = filename;
	}

	public Connection getConnection() throws SQLException {
		if (this.connection != null && !this.connection.isClosed()) {
			return connection;
		}
		connection = getNewConnection();
		return connection;
	}

	public Connection getNewConnection() throws SQLException {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Could not find class: org.sqlite.JDBC");
		}

		return DriverManager.getConnection("jdbc:sqlite:" + filename);
	}

	public ResultSet execute(String query) throws SQLException {
		Connection connection = getConnection();
		Statement statement = connection.createStatement();
		return statement.executeQuery(query);
	}

}