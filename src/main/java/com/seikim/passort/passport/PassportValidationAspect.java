package com.seikim.passort.passport;

import com.seikim.passort.exception.PassportErrorCode;
import com.seikim.passort.exception.PassportException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Component
@Aspect
public class PassportValidationAspect {
    private final UsedPassportRepository repository;

    @Pointcut("@annotation(com.seikim.passort.passport.PassportValidation)")
    private void targetAspect() {
    }

    @After("targetAspect()")
    public void validate(JoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof Passport passport) {
                validateExpirationTime(passport.getExpirationTime());
                validateAlreadyUsed(passport.getId());
                repository.save(new UsedPassport(passport.getId()));
                log.info(passport.toString());
                break;
            }
        }
    }

    private void validateExpirationTime(LocalDateTime expirationTime) {
        if (LocalDateTime.now().isAfter(expirationTime)) {
            throw new PassportException(PassportErrorCode.EXPIRED_PASSPORT);
        }
    }

    private void validateAlreadyUsed(String id) {
        if (repository.existsById(id)) {
            throw new PassportException(PassportErrorCode.ALREADY_USED_PASSPORT);
        }
    }
}
