package portfolio.common.models;

public class Experience {
	private int id;
	private String title;
	private String details;
	// private Blob image;

	public Experience(int id, String title, String details) {
		this.id = id;
		this.title = title;
		this.details = details;
		// this.image = image;

	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDetails() {
		return details;
	}
	// public Blob getImage() { return image; };

}