package portfolio.presentation;

import java.util.Map;

public interface HttpResult {
	int getStatusCode();

	Map<String, String> getHeaders();

	byte[] getBody();
}