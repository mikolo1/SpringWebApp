package mikolo.springWebApp.validators;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import mikolo.springWebApp.constants.ConstantsData;
import mikolo.springWebApp.user.User;
import mikolo.springWebApp.utils.Utilities;

@Service
public class UpdateUserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz); 
	}

	@Override
	public void validate(Object target, Errors errors) {
		User u = (User) target; 
		
		if(StringUtils.isBlank(u.getName())) {
			errors.rejectValue("name", "error.userName.empty");
		}
		
		if(StringUtils.isBlank(u.getLastName())) {
			errors.rejectValue("lastName", "error.userLastName.empty");
		}
		
		if (StringUtils.isNotBlank(u.getEmail())) {
			boolean isMatch = Utilities.validateEmailOrPassword(ConstantsData.EMAIL_PATTERN, u.getEmail());
			if (!isMatch) {
				errors.rejectValue("email", "error.userEmailIsNotMatch"); 
			}
		}else {
			errors.rejectValue("email", "error.userEmail.empty");
		}

	}

	public void validateEmailExist(User user, Errors errors) {
		if (user != null) {
			errors.rejectValue("email", "error.userEmailExist");
		}
	}
}
