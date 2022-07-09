package br.com.elo7.starlink.domains.user.validations;

import br.com.elo7.starlink.domains.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserAlreadyExists implements ConstraintValidator<UserAlreadyExistsValidation, String> {

    @Autowired
    private UserService userService;

    public boolean isValid(String email, ConstraintValidatorContext cxt) {
        var user = userService.findByEmail(email);
        return user == null;
    }
}
