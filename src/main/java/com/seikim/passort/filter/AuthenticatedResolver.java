package com.seikim.passort.filter;

import com.seikim.passort.exception.PassportErrorCode;
import com.seikim.passort.exception.PassportException;
import com.seikim.passort.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthenticatedResolver implements HandlerMethodArgumentResolver {
    private static final String HEADERS_AUTHORIZATION = HttpHeaders.AUTHORIZATION;
    private static final String HEADERS_USER_AGENT = HttpHeaders.USER_AGENT;
    private static final String GRANT_TYPE = "Bearer ";
    private static final long EXPIRATION_TIME = 5L;

    private final TokenProvider tokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasParameterAnnotation = parameter.hasParameterAnnotation(Authenticated.class);
        boolean assignableFrom = parameter.getParameterType().isAssignableFrom(parameter.getParameterType());
        return hasParameterAnnotation && assignableFrom;
    }

    @Override
    public Passport resolveArgument(MethodParameter parameter,
                                    ModelAndViewContainer mavContainer,
                                    NativeWebRequest webRequest,
                                    WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String token = getToken(Objects.requireNonNull(request));
        Long memberId = tokenProvider.getPayload(token);
        return Passport.builder()
                .memberId(memberId)
                .path(request.getServletPath())
                .userAgent(request.getHeader(HEADERS_USER_AGENT))
                .expirationTime(LocalDateTime.now().plusSeconds(EXPIRATION_TIME))
                .build();
    }


    private String getToken(HttpServletRequest request) {
        Optional<String> authorization = Optional.ofNullable(request.getHeader(HEADERS_AUTHORIZATION));
        String token = authorization.orElseThrow(
                () -> new PassportException(PassportErrorCode.AUTHORIZATION_NOT_FOUNT)
        );
        if (token.startsWith(GRANT_TYPE)) {
            return token.replace(GRANT_TYPE, "");
        }
        throw new PassportException(PassportErrorCode.GRANT_TYPE_NOT_FOUND);
    }
}
