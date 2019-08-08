package mikolo.springWebApp.user;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.AllArgsConstructor;
import mikolo.springWebApp.emailservice.EmailService;
import mikolo.springWebApp.utils.Utilities;
import mikolo.springWebApp.validators.RegisterValidator;

@AllArgsConstructor
@Controller
public class RegistryController {
	
	private final static Logger LOG = LoggerFactory.getLogger(RegistryController.class);
	private UserService userService;
	private MessageSource messageSource;
	private RegisterValidator registerValidator;
	private EmailService emailService;


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
			user.setEmailActivationCode(Utilities.activationCodeGenerator());
			userService.saveUser(user);
			String content = messageSource.getMessage("user.register.email.content", null, locale) +
					"\nhttp://localhost:8080/activationlink/" + user.getEmailActivationCode();
			emailService.sendEmail(user.getEmail(), messageSource.getMessage("register.confirm", null, locale), content);
			model.addAttribute("message", messageSource.getMessage("user.register.success.email", null, locale));
			LOG.info(">>>>>>>>>>>>>> Dodano użytkownika, email: "+user.getEmail());
			
			returnPage = "index";
		}
		return returnPage;
	}
	
	@GetMapping(value = "activationlink/{activationcode}")
	public String accountActivation(@PathVariable("activationcode") String activationcode, Model model, Locale locale) {

		User user = userService.findUserByActivationCode(activationcode);
		if(user == null) {
			model.addAttribute("message", messageSource.getMessage("user.registerActivation.fail", null, locale));
			LOG.info(">>>>>>>>>>>>>> Błędnie podano kod aktywacyjny: "+ activationcode);
			return "index";
		}
		if(user.getActive() == 1) {
			model.addAttribute("message", messageSource.getMessage("user.alreadyActive", null, locale));
			return "index";
		}
		userService.updateUserActivation(1, activationcode);
		LOG.info(">>>>>>>>>>>>>> Potwierdzono rejestrację użytkownika, email: "+user.getEmail());
		model.addAttribute("message", messageSource.getMessage("user.register.success", null, locale));
		
		return "index";
	}
}
