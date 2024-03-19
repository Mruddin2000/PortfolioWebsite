package portfolio.logic;

import java.util.List;

import portfolio.common.ExperienceRepositoryBackend;
import portfolio.common.ExperienceRepositoryFrontend;
import portfolio.common.models.Experience;

public class ExperienceLogic {

	private ExperienceRepositoryFrontend frontEndRepository;
	private ExperienceRepositoryBackend backEndRepository;

	public ExperienceLogic(ExperienceRepositoryFrontend frontEndRepository,
			ExperienceRepositoryBackend backEndRepository) {
		this.frontEndRepository = frontEndRepository;
		this.backEndRepository = backEndRepository;
	}

	public Experience getByFrontEndId(int id) {
		return frontEndRepository.getByFrontEndId(id);
	}

	public Experience getByBackEndId(int id) {
		return backEndRepository.getByBackEndId(id);
	}

	public List<Experience> getgetAllFrontEndAll() {
		return frontEndRepository.getAllFrontEnd();

	}

	public List<Experience> getgetAllBackEndAll() {
		return backEndRepository.getAllBackEnd();

	}
}
