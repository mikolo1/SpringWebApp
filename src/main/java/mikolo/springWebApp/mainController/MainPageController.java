package mikolo.springWebApp.mainController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.AllArgsConstructor;
import mikolo.springWebApp.user.User;
import mikolo.springWebApp.utils.UserUtilities;

@AllArgsConstructor
@Controller
public class MainPageController {

	private UserUtilities userUtilities;

	@GetMapping(value = { "/", "/index" })
	public String showMainPage(Model model) {
		User user = userUtilities.getUserByUserName();
		model.addAttribute("user", user);
		return "index";
	}

	@GetMapping(value = "/denied")
	public String showAccesDenidedPage() {
		return "denied";
	}
}
