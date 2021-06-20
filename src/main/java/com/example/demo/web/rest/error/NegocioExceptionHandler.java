package com.example.demo.web.rest.error;

import com.example.demo.service.error.ErroPadrao;
import com.example.demo.service.error.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class NegocioExceptionHandler {
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(NegocioException.class)
    public final ResponseEntity<ErroPadrao> handleNegocioException(Exception exception, WebRequest request) {
        NegocioException negocioException = (NegocioException) exception;
        return handleNegocioException(negocioException, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private ResponseEntity<ErroPadrao> handleNegocioException(NegocioException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = handleNegocioExceptionMessage(ex);
        return handleExceptionInternal(ex, new ErroPadrao(errors), headers, status, request);
    }

    private List<String> handleNegocioExceptionMessage(NegocioException ex) {
        return Collections.singletonList(messageSource.getMessage(ex.getTitulo(), null, Locale.forLanguageTag("pt")) + " "
                + messageSource.getMessage(ex.getMensagem(), null, Locale.forLanguageTag("pt")));
    }

    private ResponseEntity<ErroPadrao> handleExceptionInternal(Exception ex, @Nullable ErroPadrao body, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(body, headers, status);
    }
}
