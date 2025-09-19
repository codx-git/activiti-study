package com.example.activiti.exception;

import com.example.activiti.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String message = fieldError != null ? fieldError.getDefaultMessage() : "参数校验失败";
        return ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), message);
    }

    // 处理其他所有异常
    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleAllExceptions(Exception ex) {
        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器内部错误");
    }
}
