package mikolo.springWebApp.admin;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.GsonBuilder;

import lombok.AllArgsConstructor;
import mikolo.springWebApp.user.User;
import mikolo.springWebApp.utils.UserUtilities;

@Secured(value = { "ROLE_ADMIN" })
@AllArgsConstructor
@Controller
@RequestMapping("admin")
public class AdminPageController {

	private final static Logger LOG = LoggerFactory.getLogger(AdminPageController.class);
	private final static int NUMBER_OF_ELEMENTS_ON_PAGE = 5;
	private MessageSource messageSource;
	private UserUtilities userUtilities;
	private AdminService adminService;
	private RestTemplate restTemplate;

	@GetMapping(value = "")
	public String showAdminPage(Model model) {
		User user = userUtilities.getUserByUserName();
		model.addAttribute("user", user);
		LOG.info(">>>>>>>>>>>>>>> Użytkownik: "+ user.getEmail()+ " zalogował się jako administrator.");
		return "admin/admin";
	}

	@GetMapping(value = "/users/{page}")
	public String ShowAllUsersPAge(Model model, @PathVariable("page") int page) {
		return showUserSearchPage("", page, model);
	}
	
	@GetMapping(value = "/users/{page}/{textToSearch}")
	public String showUserSearchPage(@PathVariable("textToSearch") String textToSearch, @PathVariable("page") int page,	Model model) {
		User user = userUtilities.getUserByUserName();
		Page <User> userPage = getAllUsersPageable(page -1, textToSearch);
		int totalPages = userPage.getTotalPages(); 
		int currentPage = userPage.getNumber() + 1; 
		int userIndex = (currentPage - 1) * NUMBER_OF_ELEMENTS_ON_PAGE + 1;
		List<User> findedUsers = userPage.getContent(); 
		
		if (findedUsers.isEmpty()) {
			totalPages = 0;
			currentPage = 0;
		} 
		
		model.addAttribute("totalPages", totalPages); 
		model.addAttribute("currentPage", currentPage); 
		model.addAttribute("findedUsers", findedUsers);
		model.addAttribute("userIndex", userIndex);
		model.addAttribute("oryginalTextToSearch", textToSearch);
		model.addAttribute("user", user);

		return "admin/usersearch";
	}
	

	@GetMapping(value = "/users/edit/{id}")
	public String ShowUserEditPAge(Model model, @PathVariable("id") long id) {
		User admin = userUtilities.getUserByUserName();
		User userToEdit = adminService.findUserById(id);
		if (userToEdit == null) {
			return "redirect:/admin/users/1";
		}

		Map<Integer, String> roleMap = prepareRoleMap();
		Map<Integer, String> activityMap = prepareActivityMap();

		int nrRoli = UserUtilities.getNrRoli(userToEdit);
		userToEdit.setNrRoli(nrRoli);

		model.addAttribute("user", admin);
		model.addAttribute("userToEdit", userToEdit);
		model.addAttribute("roleMap", roleMap);
		model.addAttribute("activityMap", activityMap);
		LOG.info(">>>>>>>>>>>>>>> Zmieniono dane użytkownika o ID: "+ id+ ", email: " + userToEdit.getEmail());

		return "admin/useredit";
	}

	@PostMapping(value = "/updateuser/{id}")
	public String userUpdate(User userUpdated, @PathVariable(value = "id") long id) {
		adminService.updateUser(id, userUpdated.getActive(), userUpdated.getNrRoli());
		return "redirect:/admin/users/1";
	}
		
	@PostMapping(value="/usersload", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String importXMLUsers(@RequestParam("filename") MultipartFile mFile, Model model, Locale locale) {
		String uploadDir = System.getProperty("user.dir") + "/uploads";
		File file;
		
		try {
			file = new File(uploadDir);
			if(!file.exists()) {
				file.mkdir();
			}
			
			Path fileAndPath = Paths.get(uploadDir, mFile.getOriginalFilename());
			Files.write(fileAndPath, mFile.getBytes());	//zapisuję plik na dysku
			file = fileAndPath.toFile(); //pobieram go jako plik do przekazania do metody parsującej z xml
			
			List<User>userList = UserUtilities.usersDataLoader(file);			
//			adminService.insertInBatch(userList);
//			restTemplate.postForObject("http://localhost:8300/service/users/adduserslist1", userList, String.class); //to wystarczy
			
			String jsonUserList = new GsonBuilder().setPrettyPrinting().create().toJson(userList);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
			
			@SuppressWarnings({ "rawtypes", "unchecked" })
			HttpEntity request = new HttpEntity(jsonUserList, headers);
			restTemplate.postForObject("http://localhost:8300/service/users/adduserslist", request, String.class);
			file.delete();
			
			if(userList.size() > 0) {
				model.addAttribute("message", messageSource.getMessage("XMLUserListUpload.success", null, locale));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		User admin = userUtilities.getUserByUserName();
		model.addAttribute("user", admin);
		return "admin/admin";
	}
	
	@GetMapping(value = "/removeuser/{id}")
	public String removeUser(@PathVariable(value = "id") long id, Model model, Locale locale) {
		User userToRemove = adminService.findUserById(id);
		User admin = userUtilities.getUserByUserName();
		int nrRoli = UserUtilities.getNrRoli(userToRemove);
		userToRemove.setNrRoli(nrRoli);
		if (userToRemove.getNrRoli() == 2) {
			adminService.deleteUserById(id);
			LOG.info(">>>>>>>>>>>>>>> Admin " + admin.getEmail() + " usunął z bazy użytkownika "+ userToRemove.getEmail() + ".");
			model.addAttribute("message", messageSource.getMessage("deleteUser.success", null, locale) + id + ".");			
		} else {
			LOG.info(">>>>>>>>>>>>>>> Admin " + admin.getEmail() + " próbował usunąć z bazy Administratora "+ userToRemove.getEmail() + ".");
			model.addAttribute("message", messageSource.getMessage("deleteUser.unauthorised", null, locale));
		}	
		model.addAttribute("user", admin);
		return "admin/admin";
	}

	public Map<Integer, String> prepareRoleMap() {
		Map<Integer, String> preparedRoleMap = new HashMap<Integer, String>();
		Locale locale = Locale.getDefault();
		preparedRoleMap.put(1, messageSource.getMessage("word.admin", null, locale));
		preparedRoleMap.put(2, messageSource.getMessage("word.user", null, locale));
		return preparedRoleMap;
	}

	public Map<Integer, String> prepareActivityMap() {
		Map<Integer, String> preparedActivityMap = new HashMap<Integer, String>();
		Locale locale = Locale.getDefault();
		preparedActivityMap.put(0, messageSource.getMessage("word.no", null, locale));
		preparedActivityMap.put(1, messageSource.getMessage("word.yes", null, locale));
		return preparedActivityMap;
	}

		private Page<User> getAllUsersPageable(int page, String textToSearch) { 
		Page<User> pageOfUsers = StringUtils.isBlank(textToSearch)
				? adminService.findAll(PageRequest.of(page, NUMBER_OF_ELEMENTS_ON_PAGE, Sort.by("id")))
				: adminService.findUsersBySearchWord(textToSearch, PageRequest.of(page, NUMBER_OF_ELEMENTS_ON_PAGE, Sort.by("user_id")));
		
		pageOfUsers.forEach(u -> {
			int numerRoli = UserUtilities.getNrRoli(u); 
			u.setNrRoli(numerRoli);
		});
		
		return pageOfUsers;		
	}
		
}
