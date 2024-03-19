package portfolio.presentation;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

public class View implements HttpResult {

	private Map<String, Object> variables;
	private PebbleTemplate compiledTemplate;

	public View(String name) {
		this(name, null);
	}

	public View(String name, Map<String, Object> variables) {
		PebbleEngine engine = new PebbleEngine.Builder().build();
		compiledTemplate = engine.getTemplate("portfolio/presentation/views/" + name + ".html");
		this.variables = variables;
	}

	@Override
	public int getStatusCode() {
		return 200;
	}

	@Override
	public Map<String, String> getHeaders() {
		return Map.of("Content-Type", "text/html");
	}

	@Override
	public byte[] getBody() {
		try {
			Writer writer = new StringWriter();
			compiledTemplate.evaluate(writer, variables);
			return writer.toString().getBytes();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}