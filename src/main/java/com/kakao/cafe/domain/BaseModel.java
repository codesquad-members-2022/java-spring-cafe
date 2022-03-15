package com.kakao.cafe.domain;

import java.time.LocalDateTime;

public class BaseModel {

    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }
}
