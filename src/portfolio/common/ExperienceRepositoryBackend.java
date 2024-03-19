package portfolio.common;

import java.util.List;

import portfolio.common.models.Experience;

public interface ExperienceRepositoryBackend {
	List<Experience> getAllBackEnd();

	void updateExperience(int id, String title, String details);

	Experience getByBackEndId(int id);
}