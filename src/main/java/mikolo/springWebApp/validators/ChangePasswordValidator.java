package mikolo.springWebApp.validators;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import lombok.AllArgsConstructor;
import mikolo.springWebApp.constants.ConstantsData;
import mikolo.springWebApp.user.User;
import mikolo.springWebApp.utils.Utilities;

@AllArgsConstructor
@Component
public class ChangePasswordValidator implements Validator {
	
	private BCryptPasswordEncoder bcp;
	private final static Logger LOG = LoggerFactory.getLogger(ChangePasswordValidator.class);
	
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		@SuppressWarnings("unused")
		User user = (User)target;
		
//		ValidationUtils.rejectIfEmpty(errors, "newPassword", "error.userPassword.empty");
//		ValidationUtils.rejectIfEmpty(errors, "oldPassword", "error.userPassword.empty");
		
		if(StringUtils.isNotBlank(user.getNewPassword())) {
			boolean isMatch = Utilities.validateEmailOrPassword(ConstantsData.PASSWORD_PATTERN, user.getNewPassword());
			if(!isMatch) {
				errors.rejectValue("newPassword", "error.userPasswordIsNotMatch");
				LOG.info(">>>>>>>>>>>>>>> Hasło nie pasuje do wzorca, wpisane hasło: "+ user.getNewPassword());
			}
		} else {
			errors.rejectValue("newPassword", "error.userPassword.empty");
		}
		
	}
		
	public void checkOldPassword(String enteredPasword, String passwordFromDB, Errors error) {
		if(StringUtils.isNotBlank(enteredPasword)) {
			if(!bcp.matches(enteredPasword, passwordFromDB)) {
				error.rejectValue("oldPassword", "error.userOldPasswordIsNotMatch");
				LOG.info(">>>>>>>>>>>>>>> Nowe hasło nie pasuje do starego, wpisane hasło: "+ enteredPasword);
			}
		} else {
			error.rejectValue("oldPassword", "error.userPassword.empty");
		}
	}

}
