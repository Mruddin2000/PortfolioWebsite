package portfolio.presentation.controllers;

import java.util.List;
import java.util.Map;

import portfolio.common.models.About;
import portfolio.common.models.Experience;
import portfolio.common.models.Portfolio;
import portfolio.logic.AboutLogic;
import portfolio.logic.ExperienceLogic;
import portfolio.logic.HomeLogic;
import portfolio.logic.PortfolioLogic;
import portfolio.presentation.View;

public class HomeController {
	private PortfolioLogic portfolioLogic;
	private ExperienceLogic experienceLogic;
	private AboutLogic aboutLogic;

	public HomeController(HomeLogic homeLogic, AboutLogic aboutLogic, PortfolioLogic portfolioLogic,
			ExperienceLogic experienceLogic) {
		this.aboutLogic = aboutLogic;
		this.portfolioLogic = portfolioLogic;
		this.experienceLogic = experienceLogic;
	}

	public View index() {
		List<About> about = aboutLogic.getAll();
		List<Portfolio> portfolios = portfolioLogic.getAll();
		List<Experience> experiencesFrontEnd = experienceLogic.getgetAllFrontEndAll();
		List<Experience> experiencesBackEnd = experienceLogic.getgetAllBackEndAll();

		return new View("home", Map.of("about", about, "portfolios", portfolios, "experiencesFrontEnd",
				experiencesFrontEnd, "experiencesBackEnd", experiencesBackEnd));
	}

}
