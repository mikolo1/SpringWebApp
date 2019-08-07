package mikolo.springWebApp.user;

public interface UserService {
	
	public User findByEmail(String email);
	public void saveUser(User user);
	public void updatePassword(String newPassword, String email);
	public void updateUser(String newEmail, String newName, String newLastName, long id);
	
}
