package com.kakao.cafe.domain;

import java.time.LocalDateTime;

public class BaseModel {

    private LocalDateTime createdTime;
    private LocalDateTime lastModifiedTime;

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setLastModifiedTime(LocalDateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public LocalDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }
}
