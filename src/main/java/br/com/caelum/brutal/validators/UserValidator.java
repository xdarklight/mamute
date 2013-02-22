package br.com.caelum.brutal.validators;

import br.com.caelum.brutal.model.User;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.validator.I18nMessage;

@Component
public class UserValidator {

	private static final int MIN_LENGHT = 6;
	private Validator validator;
	private EmailValidator emailValidator;

	public UserValidator(Validator validator, EmailValidator emailValidator) {
		this.validator = validator;
		this.emailValidator = emailValidator;
	}

	public boolean validate(User user) {
	    validator.validate(user);
		if (user == null) {
		    validator.add(new I18nMessage("error","user.errors.wrong"));
		    return false;
		}
		
		emailValidator.validate(user.getEmail());
		
		if (user.getName().length() < MIN_LENGHT) {
			validator.add(new I18nMessage("error","user.errors.name.length"));
		}
		
		return !validator.hasErrors();
	}

	public <T> T onErrorRedirectTo(T controller){
		return validator.onErrorRedirectTo(controller);
	}
}
