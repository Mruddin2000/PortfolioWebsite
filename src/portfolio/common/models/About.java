package portfolio.common.models;

public class About {
	private String name;
	private String email;
	private String contactNumber;
	private String qualification;

	private String occupation;
	private String image;

	// Constructor
	public About(String name, String email, String contactNumber2, String qualification, String occupation,
			String imageDataBase64) {
		this.name = name;
		this.email = email;
		this.contactNumber = contactNumber2;
		this.qualification = qualification;
		this.occupation = occupation;
		this.image = imageDataBase64;
	}
	// Getters and setters for id, title, details, and imageData

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public String getQualification() {
		return qualification;
	}

	public String getOccupation() {
		return occupation;
	}

	public String getImage() {
		return image;
	}

}
