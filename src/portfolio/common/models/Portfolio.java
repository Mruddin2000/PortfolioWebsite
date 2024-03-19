package portfolio.common.models;

public class Portfolio {
	private int id;
	private String title;
	private String details;
	private String image;

	// Constructor
	public Portfolio(int id, String title, String details, String imageDataBase64) {
		this.id = id;
		this.title = title;
		this.details = details;
		this.image = imageDataBase64;
	}
	// Getters and setters for id, title, details, and imageData

	public int getId() {
		return id;
	}

	// public int getPortfolioId() { return portfoliId; }
	public String getTitle() {
		return title;
	}

	public String getDetails() {
		return details;
	}

	public String getImage() {
		return image;
	}

}
