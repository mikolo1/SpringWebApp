package mikolo.springWebApp.validators;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import mikolo.springWebApp.constants.ConstantsData;
import mikolo.springWebApp.user.User;
import mikolo.springWebApp.utils.Utilities;

@Service
public class RegisterValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz); // jako argument przyjmuje klasę, która ma zawierać pola encji do
													// walidacji
	}

	@Override
	public void validate(Object target, Errors errors) {
		User u = (User) target; // przkazuje obiekt klasy User do zwalidowania, musi być skastowany na obiekt

		//ValidationUtils.rejectIfEmpty(errors, "name", "error.userName.empty"); // errors bierze z formularza: <sf:errors
																				// path="name"/>, a drugi argument musi
																				// się zgadzać z path z formularza i
																				// nazwą pola w klasie User
		//ValidationUtils.rejectIfEmpty(errors, "lastName", "error.userLastName.empty");
		//ValidationUtils.rejectIfEmpty(errors, "email", "error.userEmail.empty");
		//ValidationUtils.rejectIfEmpty(errors, "password", "error.userPassword.empty");

		if (StringUtils.isNotBlank(u.getEmail())) {
			boolean isMatch = Utilities.validateEmailOrPassword(ConstantsData.EMAIL_PATTERN, u.getEmail());
			if (!isMatch) {
				errors.rejectValue("email", "error.userEmailIsNotMatch"); // wyrzuca błąd z jego nazwą
			}
		} else {
			errors.rejectValue("email", "error.userEmail.empty");
		}

		if (StringUtils.isNotBlank(u.getPassword())) {
			boolean isMatch = Utilities.validateEmailOrPassword(ConstantsData.PASSWORD_PATTERN, u.getPassword());
			if (!isMatch) {
				errors.rejectValue("password", "error.userPasswordIsNotMatch");
			}
		} else {
			errors.rejectValue("password", "error.userPassword.empty");
		}

		if (StringUtils.isBlank(u.getName())) {
			errors.rejectValue("name", "error.userName.empty");
		}
		
		if (StringUtils.isBlank(u.getLastName())) {
			errors.rejectValue("lastName", "error.userLastName.empty");
		}
	}

	public void validateEmailExist(User user, Errors errors) {
		if (user != null) {
			errors.rejectValue("email", "error.userEmailExist");
		}
	}
}
