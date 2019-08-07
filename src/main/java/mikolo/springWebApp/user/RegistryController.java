package mikolo.springWebApp.user;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.AllArgsConstructor;
import mikolo.springWebApp.validators.RegisterValidator;

@AllArgsConstructor
@Controller
public class RegistryController {
	
	private final static Logger LOG = LoggerFactory.getLogger(RegistryController.class);
	private UserService userService;
	private MessageSource messageSource;
	private RegisterValidator registerValidator;

	@GetMapping(value="/register")
	public String registerForm(Model model) {
		User u = new User();
		model.addAttribute("user", u);
		return "register";
	}
	
	@PostMapping(value="/adduser")
	public String userRegistry(User user, BindingResult bResult, Model model, Locale locale) {
		String returnPage = "/";
		User existingUser = userService.findByEmail(user.getEmail());
		
		registerValidator.validateEmailExist(existingUser, bResult);
		registerValidator.validate(user, bResult);		//wywołanie walidatora
		
		if(bResult.hasErrors()) {
			returnPage = "register";
		}else {
			userService.saveUser(user);
			model.addAttribute("message", messageSource.getMessage("user.register.success", null, locale));
			model.addAttribute("user", new User());
			
			LOG.info(">>>>>>>>>>>>>> Dodano użytkownika, email: "+user.getEmail());
			
			returnPage = "register";
		}
		return returnPage;
	}
}
