package portfolio.presentation.controllers;

import java.util.List;
import java.util.Map;

import portfolio.common.models.Portfolio;
import portfolio.logic.PortfolioLogic;
import portfolio.presentation.View;

public class PortfolioController {

	private PortfolioLogic portfolioLogic;

	public PortfolioController(PortfolioLogic portfolioLogic) {
		this.portfolioLogic = portfolioLogic;

	}

	public View index() {
		List<Portfolio> portfolios = portfolioLogic.getAll();

		return new View("portfolios", Map.of("portfolios", portfolios));
	}

	public View get(int id) {
		Portfolio portfolio = portfolioLogic.getById(id);

		return new View("portfolio", Map.of("portfolio", portfolio));
	}

}
