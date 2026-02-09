package com.yusufemrebilgin.boot.i18n.exception;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {

        Locale locale = LocaleContextHolder.getLocale();
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle(messageSource.getMessage("validation.failed", null, locale));
        problem.setDetail(messageSource.getMessage("validation.detail", null, locale));
        problem.setType(URI.create("https://example.com/problems/validation-error"));
        problem.setInstance(URI.create(request.getContextPath()));

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getFieldErrors().forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));
        problem.setProperty("errors", fieldErrors);

        return ResponseEntity.badRequest().body(problem);
    }
}
