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

import portfolio.common.models.Experience;
import portfolio.data.sqlite.SqliteConnection;
import portfolio.data.sqlite.SqliteExperienceRepositoryFrontEnd;
import portfolio.logic.ExperienceLogic;
import portfolio.presentation.View;

public class ExperienceFrontEndAdmin {

	private ExperienceLogic experienceLogic;

	public ExperienceFrontEndAdmin(ExperienceLogic experienceLogic) {
		this.experienceLogic = experienceLogic;

	}

	public void processSubmitFormExperience(HttpExchange exchange) throws IOException {
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
			int id = 0; // Initialize as default values
			String title = null, details = null;

			for (String pair : keyValuePairs) {
				String[] entry = pair.split("=");
				String key = entry[0];
				String value = entry.length > 1 ? entry[1] : "";

				// Assign values to the variables based on the key
				switch (key) {
				case "id":
					// Parse the ID as an integer, but do not use it for updating
					try {
						id = Integer.parseInt(value);
					} catch (NumberFormatException e) {
						// Handle parsing error
						e.printStackTrace();
					}
					break;

				case "title":
					title = value;
					break;
				case "details":
					details = value;
					break;
				// Add more cases for other form fields if needed
				}
			}

			// Now, you have the form data, and you can perform the update operation
			// For example, update data in the SQLite database
			try {
				SqliteConnection sqliteConnection = new SqliteConnection("portfolioDb.sqlite");
				SqliteExperienceRepositoryFrontEnd experienceControl = new SqliteExperienceRepositoryFrontEnd(
						sqliteConnection);

				// Pass "title" and "details" values for updating
				experienceControl.updateExperience(id, title, details);
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
		List<Experience> experiencesFrontEndAdmin = experienceLogic.getgetAllFrontEndAll();
		List<Experience> experiencesBackEndAdmin = experienceLogic.getgetAllBackEndAll();

		return new View("experiencesAdmin", Map.of("experiencesFrontEndAdmin", experiencesFrontEndAdmin,
				"experiencesBackEndAdmin", experiencesBackEndAdmin

		));
	}

	public View get(int id) {
		Experience experienceFrontEndAdmin = experienceLogic.getByFrontEndId(id);

		return new View("experienceFrontEndAdmin", Map.of("experienceFrontEndAdmin", experienceFrontEndAdmin));
	}

}