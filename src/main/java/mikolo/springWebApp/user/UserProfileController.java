package mikolo.springWebApp.user;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.AllArgsConstructor;
import mikolo.springWebApp.utils.UserUtilities;
import mikolo.springWebApp.validators.ChangePasswordValidator;
import mikolo.springWebApp.validators.UpdateUserValidator;

@AllArgsConstructor
@Controller
public class UserProfileController {

	private final static Logger LOG = LoggerFactory.getLogger(UserProfileController.class);
	private UserUtilities userUtilities;
	private UserService userService;
	private MessageSource messageSource;
	private ChangePasswordValidator changePasswordValidator;
	private UpdateUserValidator updateUserValidator;

	@GetMapping(value = "/profile")
	public String showUserProfile(Model model) {
		User user = userUtilities.getUserByUserName(); // znajduję email zologowanego usera, szukam usera po emailu
		int nrRoli = UserUtilities.getNrRoli(user);
		user.setNrRoli(nrRoli);
		String role = nrRoli == 1 ? "word.admin" : "word.user";
		String active = user.getActive() == 1 ? "word.yes" : "word.no";
		String roleFontColor = nrRoli == 1 ? "purple" : "green";
		String activeFontColor = user.getActive() == 0 ? "red" : "green";
		model.addAttribute("user", user);
		model.addAttribute("role", role);
		model.addAttribute("active", active);
		model.addAttribute("roleFontColor", roleFontColor);
		model.addAttribute("activeFontColor", activeFontColor);
		return "profile";
	}

	@GetMapping(value = "/editpassword")
	public String editPassword(Model model) {
		User user = userUtilities.getUserByUserName();
		model.addAttribute("user", user);
		return "editpassword";
	}

	@PostMapping(value = "/updatepass")
	public String changeUserPassword(User user, BindingResult result, Model model, Locale locale) {
		// BindingResult - do walidacj - może zwrócić błędy z walidatora,
		// model - określający działania po update - np zwracający coś na stronę po
		// zapisie,
		// Local - pozwala skorzytać z MessageSource i
		// skojarzyć z lokalnym plikiem messages.properties

		User UserWithNoChanges = userUtilities.getUserByUserName();;

		String oldPasswordFromDb = UserWithNoChanges.getPassword();
		String returnPage = "/";
		changePasswordValidator.validate(user, result);
		changePasswordValidator.checkOldPassword(user.getOldPassword(), oldPasswordFromDb, result);

		if (result.hasErrors()) {
			LOG.info(">>>>>>>>>>>>>>> Błąd przy zmianie hasła: "+ result.getFieldErrors());
			returnPage = "editpassword";
		} else {
			userService.updatePassword(user.getNewPassword(), UserWithNoChanges.getEmail());
			model.addAttribute("message", messageSource.getMessage("passwordChange.success", null, locale));
			returnPage = "index";
		}
		return returnPage;
	}

	@GetMapping(value = "/editprofile")
	public String editProfile(Model model) {
		User user = userUtilities.getUserByUserName();
		model.addAttribute("user", user);
		return "editprofile";
	}

	@PostMapping(value = "/updateprofile")
	public String updateProfile(User user, BindingResult result, Model model, Locale locale) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User enteredUser = user;
		String returnPage = "/";
		user = userUtilities.getUserByUserName();
		user.setNewEmail(enteredUser.getEmail());

		if (!user.getEmail().equals(user.getNewEmail())) {
			User existingUser = userService.findByEmail(user.getNewEmail());
			updateUserValidator.validateEmailExist(existingUser, result);
		}

		updateUserValidator.validate(enteredUser, result);

		if (result.hasErrors()) {
			LOG.info(">>>>>>>>>>>>>>>> Błąd przy zmianie profilu: "+ result.getFieldErrors());
			returnPage = "editprofile";
		} else {
			userService.updateUser(user.getNewEmail(), enteredUser.getName(), enteredUser.getLastName(), user.getId());
			model.addAttribute("message", messageSource.getMessage("profilEdit.success", null, locale));
			auth.setAuthenticated(false);
			returnPage = "afteredit";
		}
		return returnPage;
	}

}
