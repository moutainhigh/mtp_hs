/**
 * 版权所有 2003~2018多元世纪
 */
package com.core.exception;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Yao.shulin
 * @create Date: 2016年3月3日-上午9:40:04
 */
@ControllerAdvice(annotations = { RestController.class })
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    private static Logger LOGGER = Logger.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(value = { ServiceException.class })
    public final ResponseEntity<ErrorResult> handleServiceException(ServiceException ex, HttpServletRequest request) {
        logError(ex, request);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        ErrorResult result = new ErrorResult(ex.errorCode.getCode(), ex.getMessage());
        return new ResponseEntity<>(result, headers, HttpStatus.valueOf(ex.errorCode.getHttpStatus()));
    }

    @ExceptionHandler(value = { Exception.class })
    public final ResponseEntity<ErrorResult> handleGeneralException(Exception ex, HttpServletRequest request) {
        logError(ex, request);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        ErrorResult result = new ErrorResult(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return new ResponseEntity<>(result, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        logError(ex);
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(body, headers, status);
    }

    public void logError(Exception ex) {
        Map<String, String> map = new HashMap<>();
        map.put("message", ex.getMessage());
        LOGGER.error(map.toString(), ex);
    }

    public void logError(Exception ex, HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        map.put("message", ex.getMessage());
        map.put("from", request.getRemoteAddr());
        String queryString = request.getQueryString();
        map.put("path", queryString != null ? (request.getRequestURI() + "?" + queryString) : request.getRequestURI());

        LOGGER.error(map.toString(), ex);
    }
}
