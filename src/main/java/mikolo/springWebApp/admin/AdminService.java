package mikolo.springWebApp.admin;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import mikolo.springWebApp.user.User;

public interface AdminService {

	Page<User> findAll(Pageable pageable);
	User findUserById(long id);
	void updateUser(long id, int active, int nrRoli);
	Page<User> findUsersBySearchWord(String searchWord, Pageable pageable);

	void saveAll(List<User> userList);
	void insertInBatch(List<User> userList);
	void deleteUserById(long id);

}
