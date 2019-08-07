package mikolo.springWebApp.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	public User findByEmail(String email);
	
	@Modifying
	@Query("UPDATE User SET password = :newPassword WHERE email = :email") //named query
	public void updatePassword(@Param("newPassword") String password, @Param("email") String email);

	@Modifying
	@Query(value="UPDATE users SET email = :newEmail , name = :newName , last_name = :newLastName WHERE user_id = :id", nativeQuery = true)
	public void updateUser(@Param("newEmail") String newEmail, @Param("newName") String newName, @Param("newLastName") String newLastName, @Param("id") long id);
	
}
