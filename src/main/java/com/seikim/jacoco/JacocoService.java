package com.seikim.jacoco;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JacocoService {

    public void goodMethod(int i) {
        if (i < 10) {
            log.info("[{}]는 10미만 입니다.", i);
            return;
        }
        if (i < 100) {
            log.info("[{}]는 100미만 입니다.", i);
            return;
        }
        log.info("[{}]는 100이상 입니다.", i);
    }

    public void badMethod(int i) {
        if (i < 10) {
            log.info("[{}]는 10미만 입니다.", i);
            return;
        }
        log.info("[{}]는 100이상 입니다.", i);
    }
}
