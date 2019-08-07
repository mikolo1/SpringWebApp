package mikolo.springWebApp.mainController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	//cały proces logowanie przejmuje SpringSecurity po przechwyceniu akcji "/login" określonej w pliku jsp
	@GetMapping(value = "/login")
	public String showLoginPage() {
		return "loginpage";
	}
	
}
