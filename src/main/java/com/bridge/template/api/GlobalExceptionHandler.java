package com.bridge.template.api;

import lombok.extern.slf4j.Slf4j;
import org.bridge.base.api.CommonResponseDto;
import org.bridge.base.exception.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public final class GlobalExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<CommonResponseDto<Map<String, Object>>> handleBackEndExceptions(CommonException ex, WebRequest request) {
        HttpStatus status = ex.getErrorCode() != null ? HttpStatus.valueOf(ex.getErrorCode()) : HttpStatus.INTERNAL_SERVER_ERROR;
        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());
        body.put("error", ex.getCause());
        body.put("message", ex.getMessage() != null ? ex.getMessage() : ex.getReason());
        body.put("path", request.getDescription(false).substring(4)); // Strip "uri=" prefix

        CommonResponseDto<Map<String, Object>> response = new CommonResponseDto<>(false, body);
        return new ResponseEntity<>(response, status);
    }
}

