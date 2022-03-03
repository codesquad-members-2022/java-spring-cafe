package com.kakao.cafe.advice;

import com.kakao.cafe.controller.UserController;
import com.kakao.cafe.exception.user.DuplicateUserIdException;
import com.kakao.cafe.exception.user.NoSuchUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = UserController.class)
public class UserExceptionAdvice {

    @ExceptionHandler({ DuplicateUserIdException.class, NoSuchUserException.class })
    public ResponseEntity<String> except(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
