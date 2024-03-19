package portfolio.presentation.controllers;

import java.util.List;
import java.util.Map;

import portfolio.common.models.Experience;
import portfolio.logic.ExperienceLogic;
import portfolio.presentation.View;

public class ExperienceBackEndController {

	private ExperienceLogic experienceLogic;

	public ExperienceBackEndController(ExperienceLogic experienceLogic) {
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
		Experience experienceBackEnd = experienceLogic.getByBackEndId(id);

		return new View("experienceBackEnd", Map.of("experienceBackEnd", experienceBackEnd));
	}

}
