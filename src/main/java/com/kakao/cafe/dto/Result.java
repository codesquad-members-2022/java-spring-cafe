package com.kakao.cafe.dto;

public class Result {

    private Boolean valid;
    private String message;

    public Result(Boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }

    public static Result ok() {
        return new Result(true, "ok");
    }

    public static Result error(Exception e) {
        return new Result(false, e.getMessage());
    }

    public Boolean getValid() {
        return valid;
    }

    public String getMessage() {
        return message;
    }
}
