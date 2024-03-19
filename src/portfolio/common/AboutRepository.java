package portfolio.common;

import java.util.List;

import portfolio.common.models.About;

public interface AboutRepository {

	List<About> getAll();

	void updateAbout(String name, String email, String contactNumber, String qualification, String occupation);

}
