package portfolio.logic;

import java.util.List;

import portfolio.common.AboutRepository;
import portfolio.common.models.About;

public class AboutLogic {

	private final AboutRepository repository;

	public AboutLogic(AboutRepository repository) {
		this.repository = repository;
	}

	public List<About> getAll() {
		return repository.getAll();
	}
}
