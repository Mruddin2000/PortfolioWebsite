package portfolio.presentation.controllers.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLDecoder;

import com.sun.net.httpserver.HttpExchange;

import portfolio.logic.LoginLogic;
import portfolio.presentation.View;

public class ResetMe {
	private LoginLogic loginLogic;

	public ResetMe(LoginLogic loginLogic) {
		this.loginLogic = loginLogic;
	}

	public void processResetForm(HttpExchange exchange) throws IOException {
		InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
		BufferedReader br = new BufferedReader(isr);
		String formData = br.readLine();

		if (formData != null && !formData.isEmpty()) {

			String decodedFormData = URLDecoder.decode(formData, "UTF-8");

			String[] keyValuePairs = decodedFormData.split("&");
			String username = null, password = null;

			for (String pair : keyValuePairs) {
				String[] entry = pair.split("=");
				String key = entry[0];
				String value = entry.length > 1 ? entry[1] : "";

				switch (key) {
				case "username":
					username = value;
					break;
				case "password":
					password = value;
					break;
				}
			}

			try {
				// Check if the username exists in the database
				boolean usernameExists = loginLogic.checkUsernameExists(username);

				if (usernameExists) {
					// Update the password for the given username
					loginLogic.updatePassword(username, password);
					System.out.println("password updaed");

					// Send a success message
					String successMessage = "Password updated successfully!";
					exchange.getResponseHeaders().set("Content-Type", "text/plain");
					exchange.sendResponseHeaders(200, successMessage.length());
					OutputStream os = exchange.getResponseBody();
					os.write(successMessage.getBytes());
					os.close();
				} else {
					// Send a failure message if the username doesn't exist
					String errorMessage = "Username does not exist!";
					exchange.getResponseHeaders().set("Content-Type", "text/plain");
					exchange.sendResponseHeaders(400, errorMessage.length());
					OutputStream os = exchange.getResponseBody();
					os.write(errorMessage.getBytes());
					os.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Form data is null or empty");
		}
	}

	public View index() {
		return new View("resetPassword");
	}
}
