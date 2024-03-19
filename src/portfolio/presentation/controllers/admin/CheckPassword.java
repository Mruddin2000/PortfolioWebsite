package portfolio.presentation.controllers.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;

import portfolio.logic.LoginLogic;

public class CheckPassword {

	private LoginLogic loginLogic;

	public CheckPassword(LoginLogic loginLogic) {
		this.loginLogic = loginLogic;
	}

	// Other methods...

	public void processLoginForm(HttpExchange exchange) throws IOException {
		InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
		BufferedReader br = new BufferedReader(isr);
		String formData = br.readLine();

		if (formData != null && !formData.isEmpty()) {
			String[] keyValuePairs = formData.split("&");
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
				boolean loginSuccessful = loginLogic.checkLoginDetails(username, password);

				if (loginSuccessful) {
					// Send redirect response to admin page
					exchange.getResponseHeaders().set("Location", "/admin");
					exchange.sendResponseHeaders(302, -1); // Redirect status code
				} else {
					// Prepare the HTML response with an error message
					String errorMessage = "Invalid username or password!";
					String htmlResponse = "<html><head><title>Login Error</title></head><body><h1>Error</h1><p>"
							+ errorMessage + "</p></body></html>";

					// Send the HTML response
					exchange.getResponseHeaders().set("Content-Type", "text/html");
					exchange.sendResponseHeaders(200, htmlResponse.length());
					OutputStream os = exchange.getResponseBody();
					os.write(htmlResponse.getBytes());
					os.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Form data is null or empty");
		}
	}
}
