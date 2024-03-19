package portfolio.common;

import java.util.List;

import portfolio.common.models.Portfolio;

public interface PortfolioRepository {

	List<Portfolio> getAll();

	Portfolio getById(int id);

	void updatePortfolio(int id, String title, String details);

}
