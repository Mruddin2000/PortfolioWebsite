package portfolio.presentation.controllers;

import java.util.List;
import java.util.Map;

import portfolio.common.models.About;
import portfolio.logic.AboutLogic;
import portfolio.presentation.View;

public class AboutController {

    private final AboutLogic aboutLogic;

	public AboutController(AboutLogic aboutLogic) {
		this.aboutLogic = aboutLogic;

	}

	public View index() {
		List<About> about = aboutLogic.getAll();

		return new View("about", Map.of("about", about));
	}

}