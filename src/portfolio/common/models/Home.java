package portfolio.common.models;

import java.util.List;

public class Home {

	private Experience experience;
	private List<Portfolio> portfolio;

	public Home(Experience experience, List<Portfolio> portfolio) {
		this.experience = experience;
		this.portfolio = portfolio;
	}

	public Experience getexperience() {
		return experience;
	}

	public List<Portfolio> getportfolio() {
		return portfolio;
	}
}
