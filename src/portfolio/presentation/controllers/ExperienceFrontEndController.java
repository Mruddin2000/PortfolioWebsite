package portfolio.presentation.controllers;

import java.util.List;
import java.util.Map;

import portfolio.common.models.Experience;
import portfolio.logic.ExperienceLogic;
import portfolio.presentation.View;

public class ExperienceFrontEndController {

	private ExperienceLogic experienceLogic;

	public ExperienceFrontEndController(ExperienceLogic experienceLogic) {
		this.experienceLogic = experienceLogic;

	}

	public View index() {
		List<Experience> experiencesFrontEnd = experienceLogic.getgetAllFrontEndAll();
		List<Experience> experiencesBackEnd = experienceLogic.getgetAllBackEndAll();

		return new View("experiences",
				Map.of("experiencesFrontEnd", experiencesFrontEnd, "experiencesBackEnd", experiencesBackEnd

				));
	}

	public View get(int id) {
		Experience experienceFrontEnd = experienceLogic.getByFrontEndId(id);

		return new View("experienceFrontEnd", Map.of("experienceFrontEnd", experienceFrontEnd));
	}

}
