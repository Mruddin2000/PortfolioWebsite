package portfolio.logic;

import portfolio.common.LoginRepository;
import portfolio.common.models.Login;

public class LoginLogic {

	private final LoginRepository repository;

	public LoginLogic(LoginRepository repository) {
		this.repository = repository;
	}

	public boolean checkLoginDetails(String username, String password) {
		// Check if the provided username and password match the credentials in the
		// repository
		Login user = repository.getUsername(username);
		return user != null && user.getPassword().equals(password);
	}

	public void updatePassword(String username, String password) {
		// Update the password for the given username in the repository
		Login user = repository.getUsername(username);
		if (user != null) {
			// If the user exists, update the password
			user.setPassword(password);
			repository.updateUser(user); // Update the user in the repository
		} else {
			// Handle the case where the username does not exist
			System.out.println("Username does not exist.");
		}
	}

	public boolean checkUsernameExists(String username) {
		// Check if the username already exists in the repository
		return repository.getUsername(username) != null;
	}
}
