package portfolio.presentation.controllers.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.sun.net.httpserver.HttpExchange;

import portfolio.presentation.View;

public class backendEndpointController {

	public void processSubmitForm(HttpExchange exchange) throws IOException {
		// Extract data from the form
		InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
		BufferedReader br = new BufferedReader(isr);
		String formData = br.readLine();

		// Check if formData is null or empty
		if (formData != null && !formData.isEmpty()) {
			String decodedFormData = URLDecoder.decode(formData, "UTF-8");

			String[] keyValuePairs = decodedFormData.split("&");
			String value1 = null, value2 = null, value3 = null;

			for (String pair : keyValuePairs) {
				String[] entry = pair.split("=");
				String key = entry[0];
				String value = entry.length > 1 ? entry[1] : "";

				// Assign values to the variables based on the key
				switch (key) {
				case "name":
					value1 = value;
					break;
				case "email":
					value2 = value;
					break;
				case "message":
					value3 = value;
					break;
				// Add more cases for other form fields if needed
				}
			}

			// Now, you have the form data, and you can perform any additional processing or
			// store it in the database
			// For example, store data in the SQLite database
			try {
				Connection connection = DriverManager.getConnection("portfolioDb.sqlite");
				String query = "INSERT INTO Contact (name, email, message) VALUES (?, ?, ?)";
				try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
					// Set values based on your form data
					preparedStatement.setString(1, value1);
					preparedStatement.setString(2, value2);
					preparedStatement.setString(3, value3);

					preparedStatement.executeUpdate();
				}
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace(); // Handle the exception appropriately
			}

			// Send a response back to the client
			String response = "Data submitted successfully!";
			exchange.sendResponseHeaders(200, response.length());
			OutputStream os = exchange.getResponseBody();
			OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
			osw.write(response);
			osw.close();
		} else {
			// Handle the case where formData is null or empty
			System.out.println("Form data is null or empty");
		}
	}

	public View index() {

		return new View("admin");

	}

}