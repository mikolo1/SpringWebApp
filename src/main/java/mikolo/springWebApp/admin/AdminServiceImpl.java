package mikolo.springWebApp.admin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import mikolo.springWebApp.user.Role;
import mikolo.springWebApp.user.RoleRepository;
import mikolo.springWebApp.user.User;

@AllArgsConstructor
@Transactional
@Service
public class AdminServiceImpl implements AdminService {

	private final static Logger LOG = LoggerFactory.getLogger(AdminServiceImpl.class);
	private AdminRepository adminRepository;
	private RoleRepository roleRepository;
	private JpaContext jpaContext;
	private BCryptPasswordEncoder bcpe;

	@Override
	public Page<User> findAll(Pageable pageable) {
		return adminRepository.findAll(pageable);
	}

	@Override
	public User findUserById(long id) {
		return adminRepository.findUserById(id);
	}

	@Override
	public void updateUser(long id, int active, int nrRoli) {
		adminRepository.updateUserActivity(id, active);
		adminRepository.updateUserRole(id, nrRoli);
	}

	@Override
	public Page<User> findUsersBySearchWord(String searchWord, Pageable pageable) {
		return adminRepository.findUsersBySearchWord(searchWord, pageable);
	}

	@Override
	public void saveAll(List<User> userList) {
		Role role = roleRepository.findByRole("ROLE_USER");
		userList.forEach(u -> {
			u.setRoles(new HashSet<>(Arrays.asList(role)));
			u.setPassword(bcpe.encode(u.getPassword()));
		});
		adminRepository.saveAll(userList);
	}

	/**
	 * ładowanie do bazy porcjami, brak ryzyka przekroczenia pamięci
	 */
	@Override
	public void insertInBatch(List<User> userList) {
		EntityManager em = jpaContext.getEntityManagerByManagedType(User.class);
		Role role = roleRepository.findByRole("ROLE_USER");
		userList.forEach(user -> {
			user.setRoles(new HashSet<>(Arrays.asList(role)));
			user.setPassword(bcpe.encode(user.getPassword()));
			em.merge(user);
			if (userList.indexOf(user) > 0 && userList.indexOf(user) % 50 == 0) {
				em.flush();
				em.clear();				
			}			
		});
		LOG.info(">>>>>>>>>>>>>>>Załadowano " + userList.size() + " rekordów z plku XML.");
	}

	@Override
	public void deleteUserById(long id) {
		adminRepository.deleteFromUserRoleById(id);
		adminRepository.deleteFromUserById(id);	
	}

}
