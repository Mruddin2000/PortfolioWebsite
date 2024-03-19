package portfolio.presentation.controllers.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import portfolio.common.models.Portfolio;
import portfolio.data.sqlite.SqliteConnection;
import portfolio.data.sqlite.SqlitePortfolioRepository;
import portfolio.logic.PortfolioLogic;
import portfolio.presentation.View;

public class PortfolioAdmin {

	private PortfolioLogic portfolioLogic;

	public PortfolioAdmin(PortfolioLogic portfolioLogic) {
		this.portfolioLogic = portfolioLogic;

	}

	public void processSubmitFormPortfolio(HttpExchange exchange) throws IOException {
		// Extract data from the form
		InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
		BufferedReader br = new BufferedReader(isr);
		String formData = br.readLine();

		// Check if formData is null or empty
		if (formData != null && !formData.isEmpty()) {
			// Decode the form data
			String decodedFormData = URLDecoder.decode(formData, "UTF-8");

			// Split the form data into key-value pairs (assuming a simple format like
			// key1=value1&key2=value2)
			String[] keyValuePairs = decodedFormData.split("&");
			int value1 = 0; // Initialize as default values
			String value2 = null, value3 = null;

			for (String pair : keyValuePairs) {
				String[] entry = pair.split("=");
				String key = entry[0];
				String value = entry.length > 1 ? entry[1] : "";

				// Assign values to the variables based on the key
				switch (key) {
				case "id":
					// Parse the ID as an integer, but do not use it for updating
					try {
						value1 = Integer.parseInt(value);
					} catch (NumberFormatException e) {
						// Handle parsing error
						e.printStackTrace();
					}
					break;

				case "title":
					value2 = value;
					break;
				case "details":
					value3 = value;
					break;

				// Add more cases for other form fields if needed
				}
			}

			// Now, you have the form data, and you can perform the update operation
			// For example, update data in the SQLite database
			try {
				SqliteConnection sqliteConnection = new SqliteConnection("portfolioDb.sqlite");
				SqlitePortfolioRepository portfolioControl = new SqlitePortfolioRepository(sqliteConnection);

				// Pass "title" and "details" values for updating
				portfolioControl.updatePortfolio(value1, value2, value3);
			} catch (Exception e) {
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
		List<Portfolio> portfoliosAdmin = portfolioLogic.getAll();

		return new View("portfoliosAdmin", Map.of("portfoliosAdmin", portfoliosAdmin));
	}

	public View get(int id) {
		Portfolio portfolioAdmin = portfolioLogic.getById(id);

		return new View("portfolioAdmin", Map.of("portfolioAdmin", portfolioAdmin));
	}

}