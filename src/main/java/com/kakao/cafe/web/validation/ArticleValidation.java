package com.kakao.cafe.web.validation;

import com.kakao.cafe.web.questions.dto.ArticleDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ArticleValidation implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return ArticleDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ArticleDto article = (ArticleDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "writer", "required");
        ValidationUtils.rejectIfEmpty(errors, "title", "required");
    }
}
