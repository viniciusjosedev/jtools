package dev.vinion.jtools.common;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        if (body instanceof Map && ((Map<?, ?>) body).containsKey("success")) {
            return body;
        }

        HttpServletResponse servletResponse =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        int status = servletResponse != null ? servletResponse.getStatus() : 200;

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("statusCode", status);
        responseBody.put("success", true);

        if (body != null) responseBody.put("data", body);

        return responseBody;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidateException(MethodArgumentNotValidException ex) {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> body = new HashMap<>();


        ex.getBindingResult().getFieldErrors().forEach(error -> {
            data.put(error.getField(), error.getDefaultMessage());
        });

        body.put("statusCode", 400);
        body.put("success", false);
        body.put("data", data);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> body = new HashMap<>();

        data.put("error", ex.getReason());
        body.put("statusCode", ex.getStatusCode().value());
        body.put("success", false);
        body.put("data", data);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        Map<String, Object> body = new HashMap<>();

        body.put("statusCode", 500);
        body.put("success", false);

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
