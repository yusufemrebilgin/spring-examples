package com.yusufemrebilgin.boot.filters.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // We need to use wrappers to log request. Because The HttpServletRequest's input stream
        // can only be consumed once. If our filter reads it and log to body, the actual controller
        // won't be able to read it anymore.

        // Also response body is written after filter execution when we want to log the response
        // body, it has been already written to the output stream and cannot be read again.

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            logRequest(requestWrapper);
            logResponse(responseWrapper);
            // To ensure, response body is actually sent to the client
            responseWrapper.copyBodyToResponse();
        }
    }

    private String getRequestBody(ContentCachingRequestWrapper requestWrapper) {
        return getContentAsString(requestWrapper.getContentAsByteArray());
    }

    private String getResponseBody(ContentCachingResponseWrapper responseWrapper) {
        return getContentAsString(responseWrapper.getContentAsByteArray());
    }

    private String getContentAsString(byte[] bytes) {
        return bytes != null && bytes.length > 0 ? new String(bytes, StandardCharsets.UTF_8) : "[empty]";
    }

    private void logRequest(ContentCachingRequestWrapper request) {

        String query = request.getQueryString() == null ? "" : "?" + request.getQueryString();

        String headers = Collections.list(request.getHeaderNames()).stream()
                .map(header -> header + ": " + request.getHeader(header))
                .collect(Collectors.joining("\n\t"));

        logger.info("""
                
                Request
                    {} {}
                Body
                    {}
                Headers
                    {}
                """, request.getMethod(), request.getRequestURL() + query, getRequestBody(request), headers);
    }

    private void logResponse(ContentCachingResponseWrapper response) {

        String headers = response.getHeaderNames().stream()
                .map(header -> header + ": " + response.getHeader(header))
                .collect(Collectors.joining("\n\t"));

        logger.info("""
                
                Response
                    {}
                Body
                    {}
                Headers
                    {}
                """, HttpStatusCode.valueOf(response.getStatus()), getResponseBody(response), headers);
    }

}
