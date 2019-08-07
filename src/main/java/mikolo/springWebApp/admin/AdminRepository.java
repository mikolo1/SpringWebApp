package mikolo.springWebApp.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mikolo.springWebApp.user.User;

@Repository
public interface AdminRepository extends JpaRepository<User, Long>{

	public User findUserById(long id);
	
	@Modifying
	@Query(value = "UPDATE users SET active = :active WHERE user_id = :id", nativeQuery = true)
	void updateUserActivity(@Param("id") long id, @Param("active") int active);
	
	@Modifying
	@Query(value = "UPDATE user_role SET role_id = :roleId WHERE user_id = :id", nativeQuery = true)
	void updateUserRole(@Param("id") long id, @Param("roleId") int role);
	
	@Query(value = "SELECT * FROM users WHERE name LIKE %:searchWord% OR email LIKE %:searchWord% OR last_name LIKE %:searchWord%", nativeQuery = true)
	Page <User> findUsersBySearchWord(@Param("searchWord") String searchWord, Pageable pageable);

	@Modifying
	@Query(value = "DELETE FROM user_role WHERE user_id = :id", nativeQuery = true)
	public void deleteFromUserRoleById(@Param("id") long id);

	@Modifying
	@Query(value = "DELETE FROM users WHERE user_id = :id", nativeQuery = true)
	public void deleteFromUserById(@Param("id") long id);
	
}
