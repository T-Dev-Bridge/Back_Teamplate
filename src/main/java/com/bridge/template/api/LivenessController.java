package com.bridge.template.api;

import com.bridge.template.exception.IgnoreException;
import com.bridge.template.exception.RecordException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Liveness Controller", description = "Health Check 컨트롤러")
public class LivenessController {

    @GetMapping("/health-check")
    public ResponseEntity<String> livenessProbe() {
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/circuitbreaker-health-check")
    private String circuitBreakerTest(@RequestParam String param) throws InterruptedException {
        if ("a".equals(param))
            throw new RecordException("record exception");
        else if ("b".equals(param))
            throw new IgnoreException("ignore exception");
        else if ("c".equals(param)) // 3초 이상 걸리는 경우도 실패로 간주
            Thread.sleep(4000);

        return param;
    }

    @GetMapping("/activity")
    public String getRandomActivity() {
        throw new RecordException("record exception");
    }
}
