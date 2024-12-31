package com.bridge.template.error;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.bridge.base.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class FeignCircuitBreakerErrorDecoder implements ErrorDecoder {

    // ErrorDecoder 란 FeignClient 요청이 실패했을 때 발생하는 오류를 Custom 오류로 변환하는 역할을 수행한다.

    Environment env;

    @Autowired
    public FeignCircuitBreakerErrorDecoder(Environment env) {
        this.env = env;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.reason() != null && !response.reason().isEmpty()) {
            return new CommonException(response.reason());
        }

        String errorMessage = null;

        try {
            if (response.body() != null) {
                errorMessage = getResponseBody(response);
            } else {
                errorMessage = "Unknown error";
            }
        } catch (IOException e) {
            errorMessage = "Failed to process response body";
        }

        switch (response.status()) {
            case 400:
            case 404:
            case 500:
                return new CommonException(errorMessage);
            default:
                return new CommonException("Generic error: " + errorMessage + " - " + response.status());
        }
    }

    private String getResponseBody(Response response) throws IOException {
        if (response.body() == null) {
            return null;
        }

        return new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }
}
