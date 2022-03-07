package com.kakao.cafe.web.validation;

import com.kakao.cafe.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidation implements Validator {
    private static final String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private Pattern pattern;

    public UserValidation() {
        pattern = Pattern.compile(emailRegex);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            errors.rejectValue("email", "required");
        }else{
            Matcher matcher = pattern.matcher(user.getEmail());
            if(!matcher.matches()){
                errors.rejectValue("email", "notmatched");
            }
        }
    }
}
