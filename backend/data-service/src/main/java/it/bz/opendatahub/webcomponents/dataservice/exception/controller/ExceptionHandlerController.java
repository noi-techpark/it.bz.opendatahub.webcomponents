package it.bz.opendatahub.webcomponents.dataservice.exception.controller;

import it.bz.opendatahub.webcomponents.dataservice.exception.CoreException;
import it.bz.opendatahub.webcomponents.dataservice.exception.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

@ControllerAdvice
public class ExceptionHandlerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @Value("${errorHandling.verboseException:false}")
    private boolean verboseException;

    @ExceptionHandler
    ResponseEntity<ErrorMessage> handleACoreException(CoreException ex) {
        LOGGER.error("{} thrown, cause of: {}", ex.getClass().getName(), ex.getMessage());

        return createErrorResponse(ex.getHttpStatus(), ex);
    }

    @ExceptionHandler
    ResponseEntity<ErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        LOGGER.error("HttpMessageNotReadableException thrown, cause of: {}", ex.getMessage());

        return createErrorResponse(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler
    ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        LOGGER.error("MethodArgumentNotValidException thrown, cause of: {}", ex.getMessage());

        return createErrorResponse(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler
    ResponseEntity<ErrorMessage> handleTypeMismatchException(TypeMismatchException ex) {
        LOGGER.error("TypeMismatchException thrown, cause of: {}", ex.getMessage());

        return createErrorResponse(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler
    ResponseEntity<ErrorMessage> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        LOGGER.error("HttpRequestMethodNotSupportedException thrown, cause of: {}", ex.getMessage());

        return createErrorResponse(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler
    ResponseEntity<ErrorMessage> handleAnyException(Exception ex) {
        LOGGER.error("Exception thrown, cause of: {}", ex.getMessage());

        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    private ResponseEntity<ErrorMessage> createErrorResponse(HttpStatus httpStatus, Exception ex) {
        return createErrorResponse(httpStatus, httpStatus.value(), ex);
    }

    private ResponseEntity<ErrorMessage> createErrorResponse(HttpStatus httpStatus, int errorCode, Exception ex) {
        LOGGER.debug(getStackTrace(ex));

        ErrorMessage message = new ErrorMessage(ex, verboseException);
        message.setErrorCode(errorCode);

        return new ResponseEntity<>(message, httpStatus);
    }

    private static String getStackTrace(Throwable aThrowable) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        return result.toString();
    }
}
