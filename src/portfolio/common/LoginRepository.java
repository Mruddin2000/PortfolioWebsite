package portfolio.common;

import portfolio.common.models.Login;

public interface LoginRepository {

	Login getUsername(String username);

	void updatePassword(String username, String password);

	Login getPassword(String password); // Add getPassword method declaration

	void updateUser(Login user);
}
