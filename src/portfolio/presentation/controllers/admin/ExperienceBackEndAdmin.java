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
import portfolio.data.sqlite.SqliteExperienceRepositoryBackEnd;
import portfolio.logic.ExperienceLogic;
import portfolio.presentation.View;

public class ExperienceBackEndAdmin {

	private ExperienceLogic experienceLogic;

	public ExperienceBackEndAdmin(ExperienceLogic experienceLogic) {
		this.experienceLogic = experienceLogic;

	}

	public void processSubmitFormExperienceBackEnd(HttpExchange exchange) throws IOException {
		// Extract data from the form
		InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
		BufferedReader br = new BufferedReader(isr);
		String formExperienceData = br.readLine();

		// Check if formData is null or empty
		if (formExperienceData != null && !formExperienceData.isEmpty()) {

			String decodedFormData = URLDecoder.decode(formExperienceData, "UTF-8");

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
				// String filename = "jobwebsite.sqlite";
				// Assuming you have a valid instance of SqliteConnection
				SqliteConnection sqliteConnection = new SqliteConnection("portfolioDb.sqlite");
				SqliteExperienceRepositoryBackEnd experienceControl = new SqliteExperienceRepositoryBackEnd(
						sqliteConnection);

				// Pass "title" and "details" values for updating
				experienceControl.updateExperience(id, title, details);
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
		List<Experience> experiencesFrontEndAdmin = experienceLogic.getgetAllFrontEndAll();
		List<Experience> experiencesBackEndAdmin = experienceLogic.getgetAllBackEndAll();

		return new View("experiencesAdmin", Map.of("experiencesFrontEndAdmin", experiencesFrontEndAdmin,
				"experiencesBackEndAdmin", experiencesBackEndAdmin

		));
	}

	public View get(int id) {
		Experience experienceBackEndAdmin = experienceLogic.getByBackEndId(id);

		return new View("experienceBackEndAdmin", Map.of("experienceBackEndAdmin", experienceBackEndAdmin));
	}

}