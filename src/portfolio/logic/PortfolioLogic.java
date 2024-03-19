package portfolio.logic;

import java.util.List;

import portfolio.common.PortfolioRepository;
import portfolio.common.models.Portfolio;

public class PortfolioLogic {

	private PortfolioRepository repository;

	public PortfolioLogic(PortfolioRepository repository) {
		this.repository = repository;
	}

	public Portfolio getById(int id) {
		return repository.getById(id);
	}

	public List<Portfolio> getAll() {
		return repository.getAll();
	}

}
