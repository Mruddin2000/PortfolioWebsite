package portfolio.logic;

import java.util.List;

import portfolio.common.PortfolioRepository;
import portfolio.common.models.Portfolio;

public class HomeLogic {
	private PortfolioRepository repository;

	public HomeLogic(PortfolioRepository repository) {
		this.repository = repository;
	}

	public List<Portfolio> getAll() {
		return repository.getAll();
	}
}
