package com.kakao.cafe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomLogger {

    private final Logger log;

    public CustomLogger(Class clazz) {
        this.log = LoggerFactory.getLogger(clazz);
    }

    public void info(String format, Object... arguments) {
        String logMessage = "------------[LOG] request to " + format;
        log.info(logMessage, arguments);
    }
}
