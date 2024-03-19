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

import portfolio.common.models.About;
import portfolio.data.sqlite.SqliteAboutRepository;
import portfolio.data.sqlite.SqliteConnection;
import portfolio.logic.AboutLogic;
import portfolio.presentation.View;

public class AboutAdmin {

	private AboutLogic aboutLogic;

	public AboutAdmin(AboutLogic aboutLogic) {
		this.aboutLogic = aboutLogic;

	}

	public void processSubmitFormAbout(HttpExchange exchange) throws IOException {
		// Extract data from the form
		InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
		BufferedReader br = new BufferedReader(isr);
		String formPortfolioData = br.readLine();

		// Check if formData is null or empty
		if (formPortfolioData != null && !formPortfolioData.isEmpty()) {

			String decodedFormData = URLDecoder.decode(formPortfolioData, "UTF-8");

			String[] keyValuePairs = decodedFormData.split("&");
			String value1 = null;
			String value2 = null;
			String value3 = null;
			String value4 = null;
			String value5 = null;

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
				case "contactNumber":
					value3 = value;
					break;
				case "qualification":
					value4 = value;
					break;
				case "occupation":
					value5 = value;
					break;

				// Add more cases for other form fields if needed
				}
			}

			// Now, you have the form data, and you can perform the update operation
			// For example, update data in the SQLite database
			try {
				// String filename = "jobwebsite.sqlite";
				// Assuming you have a valid instance of SqliteConnection
				SqliteConnection sqliteConnection = new SqliteConnection("portfolioDb.sqlite");
				SqliteAboutRepository aboutControl = new SqliteAboutRepository(sqliteConnection);

				// Pass "title" and "details" values for updating
				aboutControl.updateAbout(value1, value2, value3, value4, value5);

				// System.out.println("data added");
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
		List<About> aboutAdmin = aboutLogic.getAll();

		return new View("aboutAdmin", Map.of("aboutAdmin", aboutAdmin));
	}

}