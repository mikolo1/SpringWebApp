package mikolo.springWebApp.user;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder bcpe;

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(bcpe.encode(user.getPassword()));
		user.setActive(0);
		Role role = roleRepository.findByRole("ROLE_USER");
		user.setRoles(new HashSet<>(Arrays.asList(role)));

		userRepository.save(user);
	}

	@Override
	public void updatePassword(String newPassword, String email) {
		userRepository.updatePassword(bcpe.encode(newPassword), email);
	}

	@Override
	public void updateUser(String newEmail, String newName, String newLastName, long id) {
		userRepository.updateUser(newEmail, newName, newLastName, id);

	}

	@Override
	public void updateUserActivation(int active, String activationCode) {
		userRepository.updateUserActivity(active, activationCode);
	}

	@Override
	public User findUserByActivationCode(String activationcode) {
		return userRepository.findUserByActivationCode(activationcode);
	}

}
