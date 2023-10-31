package com.example.OpenSource.global.error;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

import static com.example.OpenSource.global.error.ErrorCode.DUPLICATE_RESOURCE;
import static com.example.OpenSource.global.error.ErrorCode.INVALID_INPUT_VALUE;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // hibernate 관련 에러 처리
    @ExceptionHandler(value = {ConstraintViolationException.class, DataIntegrityViolationException.class})
    protected ResponseEntity<ErrorResponse> handleDataException() {
        log.error("handleDataException throw Exception: {}", DUPLICATE_RESOURCE);
        return ErrorResponse.toResponseEntity(DUPLICATE_RESOURCE);
    }

    // 직접 정의한 CustomException 사용
    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e){
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    /*
    @Valid 에서 error 가 나면 발생
    @ExceptionHandler 에 이미 MethodArgumentNotValidException 가 구현되어 있어서
    동일한 예외 처리를 하게 되면 Ambiguous(모호성) 문제가 발생
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity.status(INVALID_INPUT_VALUE.getStatus())
                .body(ErrorResponse.builder()
                        .status(INVALID_INPUT_VALUE.getStatus().value())
                        .error(INVALID_INPUT_VALUE.getStatus().name())
                        .code(INVALID_INPUT_VALUE.name())
                        .message(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage())
                        .build());
    }
}
