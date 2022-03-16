package com.kakao.cafe.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    private ResponseEntity<String> except(UserException ex) {
        String response = buildResponse(ex, ex.errorCode);
        return new ResponseEntity<>(response, ex.errorCode.httpStatus);
    }

    @ExceptionHandler(ArticleException.class)
    private ResponseEntity<String> except(ArticleException ex) {
        String response = buildResponse(ex, ex.errorCode);
        return new ResponseEntity<>(response, ex.errorCode.httpStatus);
    }

    //TODO : JSON 형식의 ErrorResponse 출력?!
    private String buildResponse(Exception ex, ErrorCode errorCode) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMessage())
            .append(" (")
            .append(errorCode.httpStatus.value())
            .append(")");
        return builder.toString();
    }
}
