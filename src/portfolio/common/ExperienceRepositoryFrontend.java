package portfolio.common;

import java.util.List;

import portfolio.common.models.Experience;

public interface ExperienceRepositoryFrontend {
	List<Experience> getAllFrontEnd();

	Experience getByFrontEndId(int id);

	void updateExperience(int id, String title, String details);
}