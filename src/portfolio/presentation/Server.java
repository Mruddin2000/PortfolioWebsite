package portfolio.presentation;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server {

	private Services services;

	public Server() {
		services = new Services();
	}

	public void start() throws IOException {

		InetSocketAddress address = new InetSocketAddress(8000);
		HttpServer server = HttpServer.create(address, 0);
		server.createContext("/", new Handler());
		server.setExecutor(null);
		server.start();
		System.out.println("READY. Listening on port " + 8000);
	}

	class Handler implements HttpHandler {

		Pattern aboutIndexRoute = Pattern.compile("/about/?");
		Pattern resetIndexRoute = Pattern.compile("/resetPassword/?");

		Pattern ExperienceIndexRoute = Pattern.compile("/experience/?");
		Pattern ExperienceFrontEndGetRoute = Pattern.compile("/experienceFrontEnd/(\\d+)/?");
		Pattern ExperienceBackEndGetRoute = Pattern.compile("/experienceBackEnd/(\\d+)/?");
		Pattern PortfolioIndexRoute = Pattern.compile("/portfolio/?");
		Pattern PortfolioGetRoute = Pattern.compile("/portfolio/(\\d+)/?");
		Pattern AdminIndexRoute = Pattern.compile("/admin/?");
		Pattern SubmiteFormHandler = Pattern.compile("/submitForm");
		Pattern SubmitPortfolioFormHandler = Pattern.compile("/submitPortfolioForm");
		Pattern PortfolioAdminIndex = Pattern.compile("/portfolioAdmin");
		Pattern PortfolioAdminGetRoute = Pattern.compile("/portfolioAdmin/(\\d+)/?");
		Pattern SubmitExperienceFrontEndFormHandler = Pattern.compile("/submitExperienceFrontEndForm");
		Pattern SubmitExperienceBackEndFormHandler = Pattern.compile("/submitExperieneBackEndForm");

		Pattern ExperienceAdminIndex = Pattern.compile("/experienceAdmin");
		Pattern ExperienceAdminGetRoute = Pattern.compile("/experienceAdmin/(\\d+)/?");
		Pattern ExperienceFrontEndAdminIndexRoute = Pattern.compile("/experienceFrontEndAdmin/?");
		Pattern ExperienceBackEndAdminIndexRoute = Pattern.compile("/experienceBackEndAdmin/?");
		Pattern ExperienceFrontEndAdminGetRoute = Pattern.compile("/experienceFrontEndAdmin/(\\d+)/?");
		Pattern ExperienceBackEndAdminGetRoute = Pattern.compile("/experienceBackEndAdmin/(\\d+)/?");

		Pattern SubmitaddPortfolioForm = Pattern.compile("/addPortfolioForm");

		Pattern AboutAdminIndex = Pattern.compile("/aboutAdmin");

		Pattern SubmitAboutFormHandler = Pattern.compile("/submitAboutForm");

		Pattern AdminIndex = Pattern.compile("/adminLogin");

		Pattern SubmitAdminLoginFormHandler = Pattern.compile("/submitAdminLoginForm");
		Pattern SubmitResetRequestFormHandler = Pattern.compile("/SubmitResetRequestForm");

		private Matcher getMatches(Pattern pattern, String string) {
			Matcher m = pattern.matcher(string);
			m.find();
			return m;
		}

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			try {
				URI uri = exchange.getRequestURI();
				logRequest(uri);

				String route = uri.getPath();
				HttpResult result = null;
				System.out.println("ROUTE: " + route);

				if (aboutIndexRoute.matcher(route).matches()) {
					result = services.aboutController.index();
				}

				else if (AdminIndex.matcher(route).matches()) {
					result = services.adminController.index();
				} else if (resetIndexRoute.matcher(route).matches()) {
					result = services.resetPassword.index();
				} else if (SubmitResetRequestFormHandler.matcher(route).matches()) {
					// backendEndpointController adminController = new backendEndpointController();
					services.resetPassword.processResetForm(exchange);
				} else if (SubmitAdminLoginFormHandler.matcher(route).matches()) {
					// backendEndpointController adminController = new backendEndpointController();
					services.checkPasswordLogin.processLoginForm(exchange);
				} else if (AboutAdminIndex.matcher(route).matches()) {
					result = services.aboutAdminController.index();
				}

				else if (SubmitAboutFormHandler.matcher(route).matches()) {
					// backendEndpointController adminController = new backendEndpointController();
					services.aboutAdminController.processSubmitFormAbout(exchange);
				} else if (ExperienceIndexRoute.matcher(route).matches()) {
					result = services.experienceController.index();
				} else if (ExperienceFrontEndGetRoute.matcher(route).matches()) {
					Matcher m = getMatches(ExperienceFrontEndGetRoute, route);
					int id = Integer.parseInt(m.group(1));
					result = services.experienceController.get(id);
				} else if (ExperienceBackEndGetRoute.matcher(route).matches()) {
					Matcher m = getMatches(ExperienceBackEndGetRoute, route);
					int id = Integer.parseInt(m.group(1));
					result = services.experienceBackEndController.get(id);
				} else if (ExperienceFrontEndAdminIndexRoute.matcher(route).matches()) {
					result = services.experienceFrontEndAdminController.index();
				}

				else if (ExperienceFrontEndAdminGetRoute.matcher(route).matches()) {
					Matcher m = getMatches(ExperienceFrontEndAdminGetRoute, route);
					int id = Integer.parseInt(m.group(1));
					result = services.experienceFrontEndAdminController.get(id);
				} else if (ExperienceBackEndAdminIndexRoute.matcher(route).matches()) {
					result = services.experienceBackEndAdminController.index();
				} else if (ExperienceBackEndAdminGetRoute.matcher(route).matches()) {
					Matcher m = getMatches(ExperienceBackEndAdminGetRoute, route);
					int id = Integer.parseInt(m.group(1));
					result = services.experienceBackEndAdminController.get(id);
				}

				else if (PortfolioIndexRoute.matcher(route).matches()) {
					result = services.portfolioController.index();
				} else if (PortfolioGetRoute.matcher(route).matches()) {
					Matcher m = getMatches(PortfolioGetRoute, route);
					int id = Integer.parseInt(m.group(1));
					result = services.portfolioController.get(id);
				} else if (AdminIndexRoute.matcher(route).matches()) {
					result = services.backendEndpointController.index();
				}

				else if (SubmiteFormHandler.matcher(route).matches()) {
					// backendEndpointController adminController = new backendEndpointController();
					services.backendEndpointController.processSubmitForm(exchange);
				} else if (PortfolioAdminIndex.matcher(route).matches()) {
					result = services.portfolioAdminController.index();

				} else if (PortfolioAdminGetRoute.matcher(route).matches()) {
					Matcher m = getMatches(PortfolioAdminGetRoute, route);
					int id = Integer.parseInt(m.group(1));
					result = services.portfolioAdminController.get(id);
					System.out.println("Path acessed");
				}

				else if (SubmitPortfolioFormHandler.matcher(route).matches()) {
					// backendEndpointController adminController = new backendEndpointController();
					services.portfolioAdminController.processSubmitFormPortfolio(exchange);
				}

				else if (ExperienceAdminIndex.matcher(route).matches()) {
					result = services.experienceFrontEndAdminController.index();

				} else if (ExperienceAdminGetRoute.matcher(route).matches()) {
					Matcher m = getMatches(ExperienceAdminGetRoute, route);
					int id = Integer.parseInt(m.group(1));
					result = services.experienceFrontEndAdminController.get(id);
				}

				else if (SubmitExperienceFrontEndFormHandler.matcher(route).matches()) {
					// backendEndpointController adminController = new backendEndpointController();
					services.experienceFrontEndAdminController.processSubmitFormExperience(exchange);
				} else if (SubmitExperienceBackEndFormHandler.matcher(route).matches()) {
					// backendEndpointController adminController = new backendEndpointController();
					services.experienceBackEndAdminController.processSubmitFormExperienceBackEnd(exchange);
				} else if (SubmitaddPortfolioForm.matcher(route).matches()) {
					// backendEndpointController adminController = new backendEndpointController();
					services.portfolioAdminController.processSubmitFormPortfolio(exchange);
				}

				// processSubmitForm(exchange);

				else {
					result = services.homeController.index();
				}

				if (result == null) {
					sendResponse(exchange, 404, "Not found");
				} else {
					logResponse(result.getBody().toString(), result.getStatusCode());
					for (Map.Entry<String, String> entry : result.getHeaders().entrySet()) {
						exchange.getResponseHeaders().set(entry.getKey(), entry.getValue());
					}
					exchange.sendResponseHeaders(result.getStatusCode(), result.getBody().length);
					OutputStream os = exchange.getResponseBody();
					os.write(result.getBody());
					os.close();
				}
			} catch (Exception e) {
				System.err.println("ERROR: " + e.getMessage());
				e.printStackTrace();
				sendResponse(exchange, 500, e.getMessage());
			}
		}

		private void logRequest(URI uri) {
			Date date = new Date();
			String dateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(date);
			System.out.println("REQUEST [" + dateTime + "]: " + uri);
		}

		protected void sendResponse(HttpExchange exchange, int statusCode, String body) throws IOException {
			if (body == null) {
				body = "";
			}
			logResponse(body, statusCode);
			exchange.sendResponseHeaders(statusCode, body.length());
			OutputStream os = exchange.getResponseBody();
			os.write(body.getBytes());
			os.close();
		}

		protected void logResponse(String body, int statusCode) {
			String bodySummary = body;

			if (bodySummary.length() > 40) {
				bodySummary = bodySummary.substring(0, 37) + "...";
			}

			String dateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
			System.out.println("RESPONSE [" + dateTime + "]: " + statusCode + " " + bodySummary);
		}
	}
}
